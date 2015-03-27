/** 
 * The Building class implements an application that 
 * simulates a building. The building has its own floors
 * and elevators.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 2.0
 * @since September 24, 2014
 */

package cscie55.hw3;

public class Building {
    //fields
    public static final int FLOORS = 7;
    private final Elevator elevator;
    private final Floor[] floors= new Floor[FLOORS];
    
    //constructor
    public Building() {
        //generate elevator for the building
        this.elevator = new Elevator(this);
        //generate floors for the building
        for (int i = 0; i < FLOORS; i++) {
            floors[i] = new Floor(this, i + 1);
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
        return floors[floorNumber - 1];
    }
    
    /**
     * Adds the passenger into ground floor resident set
     * @param Passenger object
     */
    public void enter(Passenger passenger) {
       floors[0].enterGroundFloor(passenger); 
    }
    
    

}
