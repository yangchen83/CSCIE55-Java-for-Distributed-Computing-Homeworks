/**
 * The ElevatorTest class is a testing for the elevator class.
 * Board passengers on the first floor and the elevator goes up and comes down.
 * Every floor the elevator moves, it output the status.
 * 
 * @author <a href="mailto:chenyang83@gmail.com">Yang Chen</a>
 * @version 1.0
 * @since September 14, 2014
 */

package cscie55.hw1.elevatortest;

import cscie55.hw1.elevator.Elevator;

public class ElevatorTest {

	public static void main(String[] args) {
		//Initiate a new elevator object
		Elevator e = new Elevator();
		
		//Board passengers, 2 for floor 3 and 1 for floor 5
		e.boardPassenger(3);
		e.boardPassenger(3);
		e.boardPassenger(5);
		
		//Print out the state of the elevator on floor 1 before moving
		System.out.println(e.toString());
		
		//Move the elevator and print out the state of the elevator.
		//Change the number i < to change the times the elevator move
		for (int i=0; i<12; i++) {
			e.move();
			System.out.println(e.toString());
		}
	}

}
