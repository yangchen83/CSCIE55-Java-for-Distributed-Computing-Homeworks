/** 
 * The Floor class implements an application that 
 * simulates a floor in a building. Passengers
 * on the floor can call the elevator to pick them up.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 2.0
 * @since September 24, 2014
 */

package cscie55.hw3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Floor {
    //fields
    private final Elevator elevator;
    private final int floorNumber;
    Set<Passenger> resident;
    List<Passenger> passengerWaitingGoUp;
    List<Passenger> passengerWaitingGoDown;
    Set<Passenger> passengersToDisembark;
    
    
    //constructor
    public Floor(Building building, int floorNumber) {
        this.resident = new HashSet<Passenger>();
        this.passengerWaitingGoUp = new ArrayList<Passenger>();
        this.passengerWaitingGoDown = new ArrayList<Passenger>();
        this.passengersToDisembark = new HashSet<Passenger>();
        this.elevator = building.elevator();
        this.floorNumber = floorNumber;   
    }
    
    /**
     * @param Passenger object
     */
    void passengerBoardsElevator(Passenger passenger) {
        if (elevator.goingUp()) {
            passengerWaitingGoUp.remove(passenger);
        } else {
            passengerWaitingGoDown.remove(passenger);
        }
        passenger.boardElevator();
    }
    
    /**
     * This method add 1 to the number of passengers who are waiting for the elevator, 
     * and let the elevator know that passengers are waiting.
     */
    public void waitForElevator(Passenger passenger, int destinationFloor) {
        passenger.waitForElevator(destinationFloor);
        
        //remove passenger from the resident set of the floor
        resident.remove(passenger);
        
        //check if the passenger is going up or going down
        if (destinationFloor > floorNumber) {
            //passenger is going up
            passengerWaitingGoUp.add(passenger);
        } else {
            //passenger is going down
            passengerWaitingGoDown.add(passenger);
        }
        
        elevator.floorToStop[floorNumber-1] = true;
    }
    
    /**
     * @param Passenger object
     * @return boolean, true the passenger is the floor resident, false the passenger is not the floor resident
     */
    public boolean isResident(Passenger passenger) {
        return resident.contains(passenger);
    }
    
    /**
     * Adds the passenger to the floor resident set
     * @param Passenger object
     */
    void enterGroundFloor(Passenger passenger) {
        resident.add(passenger);    
    }
}

