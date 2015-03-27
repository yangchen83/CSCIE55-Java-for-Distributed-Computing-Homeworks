/** 
 * The Building class implements an application that 
 * simulates a building. The building has its own floors
 * and elevators.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 1.0
 * @since September 24, 2014
 */

package cscie55.hw2;

public class Building {
    //fields
    public static final int FLOORS = 7;
    private Elevator elevator;
    private Floor[] floorArray= new Floor[FLOORS];
    
    //constructor
    public Building() {
        //generate elevator for the building
        elevator = new Elevator(this);
        
        //generate floors for the building
        for (int i=0; i<FLOORS;i++){
            floorArray[i] = new Floor(this, i+1);
        }
    }
    
    /**
     * This method returns the elevator object of the building.
     * @return Elevator object in the building
     */
    public Elevator elevator() {
        return elevator;
    }
    
    /**
     * This method returns the specific floor number object
     * @param floor must be greater than 0 and not greater than the total number of floors in the building
     * @return Floor object of the indicated floor 
     */
    public Floor floor(int floorNumber) {
        return floorArray[floorNumber-1];
    }
    
    

}
