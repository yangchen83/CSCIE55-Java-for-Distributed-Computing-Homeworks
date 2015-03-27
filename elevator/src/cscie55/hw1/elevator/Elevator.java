/** 
 * The Elevator class implements an application that 
 * simulates an elevator. The elevator picks up passengers
 * on the first floor and send them to their destined floor.
 * The elevator starts from the first floor and goes up.
 * When it reaches the top floor, it goes down to the first floor. 
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 1.0
 * @since September 14, 2014
 */

package cscie55.hw1.elevator;

public class Elevator {

	private static final int NUMBER_OF_FLOORS = 7;
	private int passengers;
	private int currentFloor;
	
	//true for going up and false for going down
	private boolean direction;
	
	//array containing the number of passengers for each floor, by default every value is set to 0 
	private int[] numberOfPassengers = new int[NUMBER_OF_FLOORS];
	
	//array of booleans that specify if the elevator need to stop on a floor, by default every value is set to false
	private boolean[] floorToStop = new boolean[NUMBER_OF_FLOORS];
	
    /**
     * No argument constructor initiate the state of the elevator
     */
	public Elevator() {
		this.passengers = 0;
		this.currentFloor = 1;
		this.direction = true;		
	}
	
	/** 
	 * This method moves the elevator 1 floor up or down 
	 * every time the method is called
	 */
	public void move () {
		//check if the direction of the elevator need to change
		if (direction && currentFloor == NUMBER_OF_FLOORS){
			direction = false;
		} else if (!direction && currentFloor == 1) {
			direction = true;
		}
		
		//decide if the currentFloor is going to increase or decrease according to the direction and position of the elevator 
		if (direction && currentFloor !=NUMBER_OF_FLOORS){
		    currentFloor++;
		} else {
			currentFloor--;
		}
		
		//delete the number of the passengers got out of the elevator
		passengers = passengers - numberOfPassengers[currentFloor-1]; 
		numberOfPassengers [currentFloor-1] = 0;
	    floorToStop[currentFloor-1] = false;
	}
	
	/**
	 * This method board passengers for the destined floor.
	 * @param floor must be greater than 1 and no greater than the number of floors in the building. 
	 */
	public void boardPassenger (int floor) {
		//check if the floor input is valid
		if (floor > 1 && floor <= NUMBER_OF_FLOORS) {
			
			numberOfPassengers [floor -1] ++;
			passengers++;
			if (!floorToStop[floor-1]) {
			floorToStop[floor-1] = true;
		    }
		}
	}
	
	/**
	 * This method returns a string that summarize the state of the elevator.
	 * @return String for the print out
	 */
	public String toString() {
		//form the first part of the printout
		String state ="Floor " + currentFloor +": " + passengers;
		
		//check the passenger number to decide to use "passengers" or "passenger" in the printout
		if (passengers == 1) {
			return state + " passenger" ; 
		} else {
			return state + " passengers" ; 
		}
	}

}
