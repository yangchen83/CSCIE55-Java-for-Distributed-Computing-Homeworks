/** 
 * The Elevator class implements an application that 
 * simulates an elevator. The elevator picks up passengers
 * and send them to their destined floor.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 2.0
 * @since September 14, 2014
 */

package cscie55.hw2;

public class Elevator {
    //fields
    public static final int CAPACITY = 10;
    private int numberOfFloors;
    private int passengerNumber;
    private int currentFloorNumber;
    
    //direction of the elevator, true for going up, false for going down
    private boolean directionGoUp;
    
    //array containing the number of passengers to disembark for each floor, by default every value is set to 0
    private int[] passengersToDisembark;
    
    //array of booleans that specify if the elevator need to stop on a floor, by default every value is set to false
    protected boolean[] floorToStop;
    
    //the building the elevator is in
    private Building building;
    
    //constructor
    public Elevator (Building building) {
        this.numberOfFloors = Building.FLOORS;
        this.building = building;
        this.passengerNumber = 0;
        this.currentFloorNumber = 1;
        this.directionGoUp = true;
        this.passengersToDisembark = new int[numberOfFloors];
        this.floorToStop = new boolean[numberOfFloors];
    }
    
    /** 
     * This method moves the elevator 1 floor up or down 
     * every time the method is called
     */
    public void move() {
        if (directionGoUp && currentFloorNumber == numberOfFloors) {
            directionGoUp = false;
        } else if (!directionGoUp && currentFloorNumber == 1) {
            directionGoUp = true;
        }
        
        if (directionGoUp) {
            currentFloorNumber++;
        } else {
            currentFloorNumber--;
        }
        
        passengerNumber = passengerNumber - passengersToDisembark[currentFloorNumber-1];
        passengersToDisembark[currentFloorNumber-1] = 0;
        floorToStop[currentFloorNumber-1] = false;
        
        //if there are passengers waiting on the floor and the elevator is not full yet, 
        //the elevator will stop and board the passengers
        int passengersWaitingToBoard = building.floor(currentFloorNumber).passengersWaiting();
        if ( passengersWaitingToBoard > 0 && passengerNumber != CAPACITY) { 
            for (int i = 0; i< passengersWaitingToBoard; i++) {
                try {
                    boardPassenger(1);
                    building.floor(currentFloorNumber).setNumberOfPassengersWaiting(passengersWaitingToBoard-1-i);
                } catch (ElevatorFullException e) {
                    System.out.println(e); //print out "Elevator Full"
                    break; //stop boarding more passengers
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
     * This method returns the number of passengers in the elevator
     * @return int, the number of passengers in the elevator
     */
    public int passengers() {
        return passengerNumber;
    }
    
    /** 
     * This method board 1 passenger to the elevator when the elevator is not full
     * If the elevator is full, no more passengers will be boarded, an ElevatorFullException will be thrown
     * @param destinationFloorNumber should be one of the floor numbers in the building and not the same at the floor boarding.
     */
    public void boardPassenger(int destinationFloorNumber) throws ElevatorFullException {
        if (passengerNumber==CAPACITY) {
            throw new ElevatorFullException();
        } else {
            if (destinationFloorNumber >=1 && destinationFloorNumber <= numberOfFloors && destinationFloorNumber != currentFloorNumber){
                passengersToDisembark[destinationFloorNumber-1]++;
                passengerNumber++;
                floorToStop[destinationFloorNumber-1] = true;
            }
        }
    }
    
    /**
     * This method returns a string that summarize the state of the elevator.
     * @return String for the print out
     */
    public String toString() {
        //form the first part of the printout
        String state ="Floor " + currentFloorNumber +": " + passengerNumber;
        
        //check the passenger number to decide to use "passengers" or "passenger" in the printout
        if (passengerNumber == 1) {
            return state + " passenger" ; 
        } else {
            return state + " passengers" ; 
        }    
    }
    
    
}
