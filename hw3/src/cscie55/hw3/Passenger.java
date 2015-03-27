/** 
 * The Passenger class implements an application that 
 * simulates a passenger in a building. Passengers
 * can be waiting for elevator, in the elevator, or 
 * resident of a floor
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 1.0
 * @since October 6, 2014
 */

package cscie55.hw3;

public class Passenger {
    //fields
    private int currentFloor;
    private int destinationFloor;
    private final int id;
    
    //constructor
    public Passenger(int id) {
        this.id = id;
        this.currentFloor = 1;
        this.destinationFloor = -1;
    }
       
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Passenger)) {
            return false;
        }
        Passenger that = (Passenger) object;
        return this.id == that.id;
    }

    /**
     * @return int, the floor the passenger is at, -1 if the passenger is in the elevator
     */
    public int currentFloor() {
        return currentFloor;
    }
    
    /**
     * @return int, the floor the passenger want to go, -1 if the passenger is on his/her floor of residence
     */
    public int destinationFloor() {
        return destinationFloor;
    }
    
    /**
     * @param int, the floor number the passenger want to go
     */
    public void waitForElevator(int newDestinationFloor) {
        destinationFloor = newDestinationFloor;
    }
    
    /**
     * sets passenger currentFloor to -1
     */
    public void boardElevator() {
        currentFloor = -1;
    }
    
    /**
     * sets passenger current floor number
     * sets passenger destinationFloor to -1
     */
    public void arrive() {
        currentFloor = destinationFloor;
        destinationFloor = -1;
    }
    
    /**
     * @return String summarize the passenger's state.
     */
    public String toString() {
        if (currentFloor == -1) {
            return "The passenger " + id + " is in the elevator going to floor " + destinationFloor;
        } else if (destinationFloor == -1) {
            return "The passenger " + id + " is staying on floor " + currentFloor;
        } else {
            return "The passenger " + id + " is on floor " + currentFloor + ", waiting to go to floor " + destinationFloor;
        }
    }

}
