package edu.buffalo;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;


import org.junit.Test;

public class ReservationHandlerTest {
	
	ReservationHandler handler = new ReservationHandler();
	Queue<Reservation> reservationQueue = new LinkedList<Reservation>();	
	Student stud1 = new Student("Atrayee", "atrayeen@buffalo.edu", null, "Where is Atrayee");
	Student stud2 = new Student("Miki", "miki@buffalo.edu", null, "Where is Miki");
	Student stud3 = new Student("Asish", "asish@buffalo.edu", null, "Where is Asish");
	Student stud4 = new Student("Bhagyashree", "bthorat@buffalo.edu", null, "Where is Bhagyashree");
	Student stud5 = new Student("Yijiang", "yijiang@buffalo.edu", null, "Where is Yijiang");
	
	Reservation res1 = new Reservation(1, "05/06/2019 14:20:58", stud1);
	Reservation res2 = new Reservation(2, "05/06/2019 14:25:58", stud2);
	Reservation res3 = new Reservation(3, "05/06/2019 14:40:58", stud3);
	Reservation res4 = new Reservation(4, "05/06/2019 14:50:58", stud4);
	Reservation res5 = new Reservation(5, "05/06/2019 15:10:58", stud5);
	
	
	/* Check if correct time is returned before 10 minutes of the given time. */
	@Test
	public void testTimeReturnedBeforeTenMin() throws Exception {	
		
		String oldTime = "05/06/2019 15:20:58";
		
		String actualTime = handler.getTime(10,oldTime);
		
		assertEquals(actualTime, "05/06/2019 15:10:58");		
	}
	
	
	/* Check if correct time is returned before 5 minutes of the given time. */
	@Test
	public void testTimeReturnedBeforeFiveMin() throws Exception {	
		
		String oldTime = "05/06/2019 15:20:58";
		
		String actualTime = handler.getTime(5,oldTime);
		
		assertEquals(actualTime, "05/06/2019 15:15:58");		
	}
	
	
	/*Check if student is banned if exactly 10 minutes has passed. */
	@Test
	public void testCheckIfStudentNotBannedInExactlyTenMin() throws Exception {	
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		String currentTime = String.valueOf(dateFormat.format(date));
		
		String scheduledReservationTime = handler.getTime(6, currentTime);
		
		boolean isBanned = handler.checkReservationPassedTime(scheduledReservationTime);
		
		assertFalse(isBanned);		
	}
	
	
	/*Check if student is banned if more than 10 minutes has passed. */
	@Test	
	public void testCheckIfStudentIsBannedAfterTenMin() throws Exception {	
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		String currentTime = String.valueOf(dateFormat.format(date));
		
		String scheduledReservationTime = handler.getTime(11, currentTime);
		
		boolean isBanned = handler.checkReservationPassedTime(scheduledReservationTime);
		
		assertTrue(isBanned);		
	}
	
	/*Check if student is not banned if less than 10 minutes has passed. */
	@Test	
	public void testCheckIfStudentIsNotBanned() throws Exception {	
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		String currentTime = String.valueOf(dateFormat.format(date));
		
		String scheduledReservationTime = handler.getTime(9, currentTime);
		
		boolean isBanned = handler.checkReservationPassedTime(scheduledReservationTime);
		
		assertFalse(isBanned);		
	}
	
	
	/* Check if queue-details is empty when queue is empty. */
	@Test
	public void testCheckQueueDetailsWhenQueueIsEmpty() throws Exception{
		
		String queueDetails = handler.checkReservationQueue(reservationQueue);
		
		assertTrue(queueDetails.isEmpty());		
	}
	
	
	/* Check if queue-details is empty when queue has elements. */
	@Test
	public void testCheckQueueDetailsWhenQueueIsNotEmpty() throws Exception{
		
		reservationQueue.add(res1);
		
		String result = handler.checkReservationQueue(reservationQueue);
		
		assertFalse(result.isEmpty());		
	}
	
	
	/* Check if reservation is removed from queue when student is banned. */
	@Test
	public void testRemoveReservationWhenStudentIsBanned() throws Exception{
		
		reservationQueue.add(res1);
		reservationQueue.add(res2);
		reservationQueue.add(res3);
		reservationQueue.add(res4);
		reservationQueue.add(res5);		
		
		Queue<Reservation> updatedQueue = handler.changeReservationStatus(res1, true, reservationQueue);
		
		assertFalse(updatedQueue.contains(res1));
	}
	
	/* Check if reservation is moved to end of queue when student is absent. */
	@Test
	public void testMoveToEndOfQueueWhenAbsent() throws Exception{	
		
		reservationQueue.add(res1);
		reservationQueue.add(res2);
		reservationQueue.add(res3);
		reservationQueue.add(res4);
		reservationQueue.add(res5);		
		
		Queue<Reservation> updatedQueue = handler.changeReservationStatus(res1, false, reservationQueue);
		
		assertEquals(updatedQueue.peek(), res2);
	}

}
