package cscie55.hw7;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinkAnalyzerImpl extends UnicastRemoteObject implements LinkAnalyzer {
    // fields
    private final List<LinkAnalyzerNode> linkAnalyzerNodes;
    
    // constructor
    protected LinkAnalyzerImpl() throws RemoteException {
        this.linkAnalyzerNodes = new ArrayList<LinkAnalyzerNode>();
    }

    public static void main(String[] args) throws RemoteException {
        // create a new LinkAnalyzer
        LinkAnalyzer linkAnalyzer = new LinkAnalyzerImpl();
        System.out.println("New LinkAnalyzer Initiated");
        
        // register with RMI
        try {
            Naming.rebind(LinkAnalyzer.URL, linkAnalyzer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("LinkAnalyzer registered with RMI");
    }

    @Override
    public Set<Link> linksByTime(long startTime, long endTime)
            throws RemoteException {
        Set<Link> linksByTime = new HashSet<Link>();
        // pass the task to every node and combine results from each node
        for (LinkAnalyzerNode node : linkAnalyzerNodes) {
            linksByTime.addAll(node.linksByTime(startTime, endTime));
        }
        return linksByTime;
    }

    @Override
    public Set<Link> linksByURL(String url) throws RemoteException {
        Set<Link> linksByURL = new HashSet<Link>();
        // pass the task to every node and combine results from each node
        for (LinkAnalyzerNode node : linkAnalyzerNodes) {
            linksByURL.addAll(node.linksByURL(url));
        }
        return linksByURL;
    }

    @Override
    public Set<Link> linksByTag(String... tags) throws RemoteException {
        Set<Link> linksByTag = new HashSet<Link>();
        // pass the task to every node and combine results from each node
        for (LinkAnalyzerNode node : linkAnalyzerNodes) {
            linksByTag.addAll(node.linksByTag(tags));
        }
        return linksByTag;
    }

    @Override
    public void registerNode(LinkAnalyzerNode node) throws RemoteException {
        linkAnalyzerNodes.add(node);
        System.out.println("One new node is connected");
    }
}