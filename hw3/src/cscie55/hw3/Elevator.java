/** 
 * The Elevator class implements an application that 
 * simulates an elevator. The elevator picks up passengers
 * and send them to their destined floor.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 3.0
 * @since September 14, 2014
 */

package cscie55.hw3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elevator {
    //fields
    public static final int CAPACITY = 10;
    private final int numberOfFloors;
    private Set<Passenger> passengerOnBoard;
    private int currentFloorNumber;
    //direction of the elevator, true for going up, false for going down
    private boolean directionGoUp;
    //array containing the number of passengers to disembark for each floor, by default every value is set to 0
    private int[] passengersToDisembark;
    //array of booleans that specify if the elevator need to stop on a floor, by default every value is set to false
    boolean[] floorToStop;
    //the building the elevator is in
    private final Building building;
    
    //constructor
    public Elevator (Building building) {
        this.numberOfFloors = Building.FLOORS;
        this.building = building;
        this.passengerOnBoard = new HashSet<Passenger>();
        this.currentFloorNumber = 1;
        this.directionGoUp = true;
        this.passengersToDisembark = new int[numberOfFloors];
        this.floorToStop = new boolean[numberOfFloors];
    }
    
    /**
     * This method check if the elevator is going up
     * @return boolean, true for going up, false for going down
     */
    public boolean goingUp() {
        if (directionGoUp) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * This method check if the elevator is going down
     * @return boolean, true for going down, false for going up
     */
    public boolean goingDown() {
        return !goingUp();
    }
    
    /** 
     * This method moves the elevator 1 floor up or down 
     * every time the method is called
     */
    public void move() {
        if (directionGoUp) {
            currentFloorNumber++;
            if (currentFloorNumber == numberOfFloors) {
                directionGoUp = false;
            }
        } else {
            currentFloorNumber--;
            if (currentFloorNumber == 1) {
                directionGoUp = true;
            }
        }
        
        // remove all the disembarked passengers from the on board passenger set
        Floor floor = building.floor(currentFloorNumber);
        passengerOnBoard.removeAll(floor.passengersToDisembark);
        // add the disembarked passengers to the floor resident set
        floor.resident.addAll(floor.passengersToDisembark);
        for (Passenger p : floor.passengersToDisembark) {
            p.arrive();
        }
        floor.passengersToDisembark.clear();
        floorToStop[currentFloorNumber-1] = false;
      
        //if elevator is going up, it picks up the passengers waiting to go up
        if (directionGoUp) {
            //make a copy of the passengerWaitingGoUp from the floor and iterate through it to board passenger
            List<Passenger> passengerWaitingGoUp = new ArrayList<Passenger>(floor.passengerWaitingGoUp);
            if ( passengerWaitingGoUp.size() > 0 && passengerOnBoard.size() < CAPACITY) {
                for (Passenger p : passengerWaitingGoUp) {
                    try {
                        boardPassenger(p, p.destinationFloor());       
                    } catch (ElevatorFullException e) {
                        break; //stop boarding more passengers
                    }
                }
            } 
        } else {
            // if elevator is going down, it picks up the passengers waiting to go down
            List<Passenger> passengerWaitingGoDown = new ArrayList<Passenger>(floor.passengerWaitingGoDown);
            if ( passengerWaitingGoDown.size() > 0 && passengerOnBoard.size() != CAPACITY) {
                for (Passenger p : passengerWaitingGoDown) {
                    try {
                        boardPassenger(p, p.destinationFloor());
                        //building.floor(currentFloorNumber).setNumberOfPassengersWaiting(passengersWaitingToBoard-1-i);
                        
                    } catch (ElevatorFullException e) {
                        //System.out.println(e); //print out "Elevator Full"
                        break; //stop boarding more passengers
                    }
                
                }
            }
        }
      
    }
    
    /** 
     * This method returns the floor number the elevator is at
     * @return int, the floor number the elevator is at
     */
    public int currentFloor() {
        return currentFloorNumber;
    }
    
    /** 
     * This method returns a set of passengers in the elevator
     * @return Set<Passenger>
     */
    public Set<Passenger> passengers() {
        return passengerOnBoard;
    }
    
    /** 
     * This method board 1 passenger to the elevator when the elevator is not full
     * If the elevator is full, no more passengers will be boarded, an ElevatorFullException will be thrown
     * @param Passenger object
     * @param destinationFloorNumber, should be one of the floor numbers in the building and not the same at the floor boarding.
     */
    public void boardPassenger(Passenger passenger, int destinationFloorNumber) throws ElevatorFullException {
        if (passengerOnBoard.size()==CAPACITY) {
            throw new ElevatorFullException();
        } else {
            if (destinationFloorNumber >=1 && destinationFloorNumber <= numberOfFloors && destinationFloorNumber != currentFloorNumber){
                passengersToDisembark[destinationFloorNumber-1]++;
                building.floor(destinationFloorNumber).passengersToDisembark.add(passenger);
                passengerOnBoard.add(passenger);
                floorToStop[destinationFloorNumber-1] = true;
                building.floor(currentFloorNumber).passengerBoardsElevator(passenger);
                passenger.boardElevator();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
    
    /**
     * This method returns a string that summarize the state of the elevator.
     * @return String for the print out
     */
    public String toString() {
        //form the first part of the printout
        String state ="Floor " + currentFloorNumber +": " + passengerOnBoard.size();
        
        //check the passenger number to decide to use "passengers" or "passenger" in the printout
        if (passengerOnBoard.size() == 1) {
            return state + " passenger" ; 
        } else {
            return state + " passengers" ; 
        }    
    }
    
    
}

