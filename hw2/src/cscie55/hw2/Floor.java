/** 
 * The Floor class implements an application that 
 * simulates a floor in a building. Passengers
 * on the floor can call the elevator to pick them up.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 1.0
 * @since September 24, 2014
 */

package cscie55.hw2;

public class Floor {
    //fields
    private int numberOfPassengersWaiting;
    private Elevator elevator;
    private int floorNumber;
    
    //constructor
    public Floor(Building building, int floorNumber) {
        numberOfPassengersWaiting = 0;
        elevator = building.elevator();
        this.floorNumber = floorNumber;
    }
    
    /**
     * This method returns the number of passengers who are waiting for the elevator.
     * @return int the number of passengers who are waiting 
     */
    public int passengersWaiting() {
        return numberOfPassengersWaiting;
    }
    
    /**
     * This method sets the number of passengers who are waiting for the elevator.
     * @param number must be greater or equal to 0. 
     */
    public void setNumberOfPassengersWaiting(int number) {
        numberOfPassengersWaiting = number;
    }
    
    /**
     * This method add 1 to the number of passengers who are waiting for the elevator, 
     * and let the elevator know that passengers are waiting.
     */
    public void waitForElevator() {
        numberOfPassengersWaiting++;
        elevator.floorToStop[floorNumber-1] = true;
    }
    

}
