package edu.buffalo;


import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class ReservationGeneratorTest {

	ReservationGenerator revGen = new ReservationGenerator();
	List<String> shuffledQuestionList = new ArrayList<String>();
	List<String> shuffledTimeList = new ArrayList<String>();
	List<String> shuffledNameList = new ArrayList<String>();
	Hashtable<String, String> nameTable = new Hashtable<String, String>();
	Queue<Reservation> reservationQueue = new LinkedList<Reservation>();
	
	
	/* Check if reservation queue is not empty when student name and question are provided to generate reservation. */
	@Test
	public void testReservationWhenQueueIsNotEmpty() {
		int low = 1;
		int high = 2;
		
		shuffledQuestionList.add("Where is Miki ?");
		shuffledTimeList.add("05/06/2019 14:12:58");
		shuffledNameList.add("Miki Padhiary");
		nameTable.put("Miki Padhiary", "mpadh@buffalo.edu");
		
		Queue<Reservation> result = revGen.generateReservations(low, high, shuffledQuestionList, shuffledTimeList, shuffledNameList, nameTable);
		assertEquals(result.size(), 1);	
	}
	
	/* Check if reservation queue is empty when no details are provided to create the reservation. */
	@Test
	public void testReservationWhenQueueIsEmpty() {
		int low = 0;
		int high = 1;
		Queue<Reservation> result = revGen.generateReservations(low, high, shuffledQuestionList, shuffledTimeList, shuffledNameList, nameTable);
		assertEquals(result.size(), 0);
	}

}
