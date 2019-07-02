package edu.buffalo;

import java.text.DateFormat;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * This class generates random number of reservations using generateReservations method.
 * @author Miki Padhiary
 *
 */
public class ReservationGenerator {
	Queue<Reservation> reservationQueue;
	DateFormat dateFormat;
	

	/**
	 * This methods generates random number of reservations between 0 to 4.
	 * @param low parameter for lowest number of reservations to be generated.
	 * @param high parameter for highest number of reservations to be generated.
	 * @param shuffledQuestionList Question list shuffled.
	 * @param shuffledTimeList Time list shuffled.
	 * @param shuffledNameList Name list shuffled.
	 * @param nameTable Table having student name as key and student email id as value.
	 * @return Queue<Reservation>
	 */
	public Queue<Reservation> generateReservations(int low, int high, List<String> shuffledQuestionList,
			List<String> shuffledTimeList, List<String> shuffledNameList, Hashtable<String, String> nameTable)
	{
		reservationQueue = new LinkedList<Reservation>();
		Random randomNumber = new Random();
		int numberOfReservation = randomNumber.nextInt(high-low) + low;	
    	
		for(int i=0; i<numberOfReservation; i++)
		{
	    	String reservationTime = shuffledTimeList.get(i);
			Reservation reservation = new Reservation(i, reservationTime, new Student(shuffledNameList.get(i),
					nameTable.get(shuffledNameList.get(i)), null, shuffledQuestionList.get(i)));
			reservationQueue.add(reservation);
		}		
		return reservationQueue;
	}

	
	
}
