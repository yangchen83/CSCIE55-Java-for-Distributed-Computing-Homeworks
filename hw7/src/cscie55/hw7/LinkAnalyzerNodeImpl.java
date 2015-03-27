package cscie55.hw7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LinkAnalyzerNodeImpl extends UnicastRemoteObject implements LinkAnalyzerNode {    
    // fields
    private final List<Link> links;
    
    // constructor
    public LinkAnalyzerNodeImpl(File folder) throws RemoteException {
        this.links = parseFile(folder);       
    }

    public static void main(String[] args) throws RemoteException {
        // create a new LinkAnalyzerNode
        LinkAnalyzerNode linkAnalyzerNode = new LinkAnalyzerNodeImpl(new File(args[0]));
        System.out.println("A new LinkAnalyzerNode is initiated");
        
        // locates the LinkAnalyzer from the RMI registry
        System.out.println("Looking for LinkAnalyzer");
        LinkAnalyzer linkAnalyzer = null;
        try {
            linkAnalyzer = (LinkAnalyzer) Naming.lookup(LinkAnalyzer.URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        System.out.println("LinkAnalyzer found");
        
        //register with the LinkAnalyzer
        linkAnalyzer.registerNode(linkAnalyzerNode);
        System.out.println("Registered with the LinkAnalyzer");
    }
    
    @Override
    public Set<Link> linksByTime(long startTime, long endTime)
            throws RemoteException {
        Set<Link> linksByTime = new HashSet<Link>();
        for (Link link : links) {
            long ts = link.timestamp();
            if (ts > startTime && ts < endTime) {
                linksByTime.add(link);
            }
        }
        return linksByTime;
    }

    @Override
    public Set<Link> linksByURL(String url) throws RemoteException {
        Set<Link> linksByURL = new HashSet<Link>();
        for (Link link : links) {
            String u = link.url();
            if (u.equals(url)) {
                linksByURL.add(link);
            }
        }
        return linksByURL;
    }

    @Override
    public Set<Link> linksByTag(String... tags) throws RemoteException {
        Set<Link> linksByTag = new HashSet<Link>();
        List<String> tagList = Arrays.asList(tags);
        for (Link link : links) {
            List<String> t = link.tags();
            if (t.containsAll(tagList)) {
                linksByTag.add(link);
            }
        }
        return linksByTag;
    }
    
    /**
     * Takes a folder and return the list of link objects parsed by the files in the folder
     * @param the folder that contain the files to be analyzed
     * @return a list of link objects
     */
    private List<Link> parseFile(File folder) {
        List<Link> links = new ArrayList<Link>();
        for (File file : folder.listFiles()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    try {
                        Link link = Link.parse(line);
                        links.add(link); 
                    } catch (Exception e){
                        // do nothing, keep on parsing next line
                    }    
                }
                br.close();
            } catch (Exception e) {
                // do nothing, keep processing next file
            }
        }
        return links;
    }
}