package edu.buffalo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 * This class handles all the interactions with the gui. It is responsible for displaying reservation queue and handling empty
 * queue. It also takes care of absent and present functionality.
 * 
 * @author Miki Padhiary
 *
 */
@SuppressWarnings("serial")
public class ReservationHandler {

	private Queue<Reservation> reservationQueue;
	private String currentTime;
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	// Names & email
	private Hashtable<String, String> nameTable = new Hashtable<String, String>() {
		{
			put("Miki Padhiary", "mpadh@buffalo.edu");
			put("Atrayee Nag", "anag@buffalo.edu");
			put("Bhagyashree Throat", "bthroat@buffalo.edu");
			put("Ashish Kakumanyu", "akakumanyu@buffalo.edu");
			put("Yijiyang Wang", "ywang@buffalo.edu");
		}
	};

	// Questions
	private List<String> questionList = new ArrayList<String>() {
		{
			add("Where is Miki ?");
			add("Where is Atrayee ?");
			add("Where is Bhagyashree ?");
			add("Where is Ashish ?");
			add("Where is Yijiyang ?");
		}
	};

	private List<String> finalTimeList = new ArrayList<String>() {
	};

	// CurrentTime
	@SuppressWarnings("unused")
	private List<Date> timeList = new ArrayList<Date>() {
		{
			Date date = new Date();
			currentTime = String.valueOf(dateFormat.format(date));
			getTime(5, currentTime);
			getTime(11, currentTime);
			getTime(0, currentTime);
			getTime(4, currentTime);
			getTime(10, currentTime);
		}
	};

	/**
	 * Constructor of ReservationHandler Class.
	 */
	public ReservationHandler() {
		ReservationGenerator reservationGenerator = new ReservationGenerator();
		List<String> shuffledQuestionList;
		List<String> shuffledTimeList;
		List<String> shuffledNameList;
		shuffledQuestionList = getShuffledQuestionList(questionList);
		shuffledTimeList = getShuffledTimeList(finalTimeList);
		List<String> keys = new ArrayList<String>(nameTable.keySet());
		shuffledNameList = getShuffledNameList(keys);
		reservationQueue = reservationGenerator.generateReservations(0, 5, shuffledQuestionList, shuffledTimeList,
				shuffledNameList, nameTable);
		
		if (reservationQueue.isEmpty()) {
			displayEmptyQueue();
		} else {
			for (Reservation res : reservationQueue) {
				System.out.println(res.getStudent().getStudentName());
				System.out.println(res.getStudent().getStudentEmailId());
				System.out.println(res.getStudent().getStudentQueries());
				System.out.println(res.getReservationTime());
				System.out.println(res.getReservationId());
				System.out.println(res.getStudent().getStudentName());
				System.out.println(
						"------------------------------------------------------------------------------------------");
			}
			displayResults(reservationQueue);
		}
	}

	/**
	 * This method takes in reservationQueue and populates the details based on the
	 * queue status.
	 * 
	 * @param reservationQueue
	 */
	private void displayResults(Queue<Reservation> reservationQueue) {
		if (reservationQueue.isEmpty()) {
			displayEmptyQueue();
		} else {
			JFrame mainFrame = initializeFrame("TA Portal");
			initializeLabel(mainFrame, "Appointment Portal");
			populateQueueDetails(reservationQueue, mainFrame);
			absentButton(reservationQueue, mainFrame);
			presentButton(reservationQueue, mainFrame);
		}
	}

	/**
	 * This method handles the present functionality. It is responsible for
	 * displaying the queue status once a student is marked present.
	 * 
	 * @param reservationQueue
	 * @param mainFrame
	 */
	public void presentButton(Queue<Reservation> reservationQueue, JFrame mainFrame) {
		JButton presentButton = new JButton("Present");
		presentButton.setFont(new Font("Verdana", Font.BOLD, 30));
		presentButton.setForeground(Color.black);
		presentButton.setBounds(750, 630, 200, 100);
		mainFrame.getContentPane().add(presentButton);
		presentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				JFrame statusFrame = initializeFrame("Reservation Queue");
				JTextPane reservationQueueDetails = new JTextPane();
				reservationQueueDetails.setBounds(12, 13, 600, 1200);
				reservationQueueDetails.setFont(new Font("Verdana", Font.BOLD, 15));
				reservationQueueDetails.setEditable(false);
				statusFrame.getContentPane().add(reservationQueueDetails);
				reservationQueue.poll();
				String queueDetails = checkReservationQueue(reservationQueue);
				if (!queueDetails.isEmpty()) {
					reservationQueueDetails.setText(queueDetails);
					statusFrame.setVisible(true);
				} else {
					displayEmptyQueue();
				}
			}
		});
	}

	/**
	 * This method handles the absent functionality. It is responsible for assigning
	 * proper reservation status based on the status of the student.
	 * 
	 * @param reservationQueue
	 * @param mainFrame
	 */
	public void absentButton(Queue<Reservation> reservationQueue, JFrame mainFrame) {
		JButton absentButton = new JButton("Absent");
		absentButton.setFont(new Font("Verdana", Font.BOLD, 30));
		absentButton.setForeground(Color.black);
		absentButton.setBounds(250, 630, 200, 100);
		mainFrame.getContentPane().add(absentButton);
		absentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				JFrame statusFrame = initializeFrame("Reservation Queue");
				JTextPane reservationQueueDetails = new JTextPane();
				reservationQueueDetails.setBounds(12, 13, 600, 1200);
				reservationQueueDetails.setFont(new Font("Verdana", Font.BOLD, 15));
				reservationQueueDetails.setEditable(false);
				statusFrame.getContentPane().add(reservationQueueDetails);
				Reservation currentReservation = reservationQueue.peek();
				boolean isBanned = checkReservationPassedTime(currentReservation.getReservationTime());
				Queue<Reservation> updatedQueue = changeReservationStatus(currentReservation, isBanned,
						reservationQueue);
				String queueDetails = checkReservationQueue(updatedQueue);
				if (!queueDetails.isEmpty()) {
					reservationQueueDetails.setText(queueDetails);
					statusFrame.setVisible(true);
				} else {
					displayEmptyQueue();
				}
			}

		});
	}

	/**
	 * This methods returns the updated reservation queue after checking the banned status of the student.
	 * 
	 * @param currentReservation
	 * @param isBanned
	 * @param reservationQueue
	 * @return Queue<Reservation>
	 */
	public Queue<Reservation> changeReservationStatus(Reservation currentReservation, boolean isBanned,
			Queue<Reservation> reservationQueue) {
		if (isBanned) {
			try {
				currentReservation.getStudent().setBannedDate(dateFormat.parse(currentTime));
				currentReservation.setReservationStatus("Student Banned on " + currentTime);
				reservationQueue.poll();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		} else {
			currentReservation.setReservationStatus("Moved to End");
			reservationQueue.poll();
			reservationQueue.add(currentReservation);
		}
		return reservationQueue;
	}

	/**
	 * This method checks whether a reservation queue is empty or not. If yes
	 * returns an empty string otherwise not.
	 * 
	 * @param reservationQueue
	 * @return String
	 */
	public String checkReservationQueue(Queue<Reservation> reservationQueue) {
		String queueDetails = "";
		for (Reservation res : reservationQueue) {
			if (res.getReservationStatus() == null || res.getReservationStatus().length() == 0) {
				res.setReservationStatus("Status Not Set");
			}
			queueDetails += " \nReservation Id : " + res.getReservationId() + " \nStudent Name : "
					+ res.getStudent().getStudentName() + " \nEmailId : " + res.getStudent().getStudentEmailId()
					+ " \nQueries : " + res.getStudent().getStudentQueries() + " \nReservation Scheduled Time : "
					+ res.getReservationTime() + " \nStatus : " + res.getReservationStatus()
					+ " ------------------------------------------------------------------------------------" + " \n";
		}

		return queueDetails;
	}

	/**
	 * This methods handles populating gui details when initialized for first time.
	 * 
	 * @param reservationQueue
	 * @param mainFrame
	 */
	private void populateQueueDetails(Queue<Reservation> reservationQueue, JFrame mainFrame) {
		JLabel studentLabel = new JLabel("Student Details:");
		studentLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		studentLabel.setForeground(Color.white);
		studentLabel.setBounds(500, 80, 500, 100);
		mainFrame.getContentPane().add(studentLabel);

		JLabel studentNameLabel = new JLabel("Student Name:");
		studentNameLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		studentNameLabel.setForeground(Color.white);
		studentNameLabel.setBounds(250, 140, 500, 100);
		mainFrame.getContentPane().add(studentNameLabel);

		JLabel studentEmailLabel = new JLabel("Student Email:");
		studentEmailLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		studentEmailLabel.setForeground(Color.white);
		studentEmailLabel.setBounds(250, 210, 500, 100);
		mainFrame.getContentPane().add(studentEmailLabel);

		JLabel studentQuestionLabel = new JLabel("Student Queries:");
		studentQuestionLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		studentQuestionLabel.setForeground(Color.white);
		studentQuestionLabel.setBounds(250, 280, 500, 100);
		mainFrame.getContentPane().add(studentQuestionLabel);

		JLabel studentScheduledTimeLabel = new JLabel("Scheduled Time:");
		studentScheduledTimeLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		studentScheduledTimeLabel.setForeground(Color.white);
		studentScheduledTimeLabel.setBounds(250, 340, 500, 100);
		mainFrame.getContentPane().add(studentScheduledTimeLabel);

		JLabel currentTimeLabel = new JLabel("Current Time:");
		currentTimeLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		currentTimeLabel.setForeground(Color.white);
		currentTimeLabel.setBounds(250, 410, 500, 100);
		mainFrame.getContentPane().add(currentTimeLabel);

		JLabel studentNameLabelValue = new JLabel(reservationQueue.peek().getStudent().getStudentName());
		studentNameLabelValue.setFont(new Font("Verdana", Font.BOLD, 30));
		studentNameLabelValue.setForeground(Color.white);
		studentNameLabelValue.setBounds(650, 140, 500, 100);
		mainFrame.getContentPane().add(studentNameLabelValue);

		JLabel studentEmailLabelValue = new JLabel(reservationQueue.peek().getStudent().getStudentEmailId());
		studentEmailLabelValue.setFont(new Font("Verdana", Font.BOLD, 30));
		studentEmailLabelValue.setForeground(Color.white);
		studentEmailLabelValue.setBounds(650, 210, 500, 100);
		mainFrame.getContentPane().add(studentEmailLabelValue);

		JLabel studentQuestionLabelValue = new JLabel(reservationQueue.peek().getStudent().getStudentQueries());
		studentQuestionLabelValue.setFont(new Font("Verdana", Font.BOLD, 30));
		studentQuestionLabelValue.setForeground(Color.white);
		studentQuestionLabelValue.setBounds(650, 280, 500, 100);
		mainFrame.getContentPane().add(studentQuestionLabelValue);

		JLabel studentScheduledTimeLabelValue = new JLabel(reservationQueue.peek().getReservationTime());
		studentScheduledTimeLabelValue.setFont(new Font("Verdana", Font.BOLD, 30));
		studentScheduledTimeLabelValue.setForeground(Color.white);
		studentScheduledTimeLabelValue.setBounds(650, 340, 500, 100);
		mainFrame.getContentPane().add(studentScheduledTimeLabelValue);

		JLabel currentTimeLabelValue = new JLabel("");
		currentTimeLabelValue.setText(currentTime);
		currentTimeLabelValue.setFont(new Font("Verdana", Font.BOLD, 30));
		currentTimeLabelValue.setForeground(Color.white);
		currentTimeLabelValue.setBounds(650, 410, 500, 100);
		mainFrame.getContentPane().add(currentTimeLabelValue);
		mainFrame.setVisible(true);
	}

	/**
	 * This method checks the difference between current time and scheduled
	 * reservation time. If the difference is more than or equal to 10 minutes it
	 * returns true otherwise false.
	 * 
	 * @param scheduledReservationTime
	 * @return boolean
	 */
	public boolean checkReservationPassedTime(String scheduledReservationTime) {
		final long ONE_MINUTE_IN_MILLIS = 60000;
		Calendar cal = Calendar.getInstance();
		Calendar newCal = Calendar.getInstance();
		try {
			cal.setTime(dateFormat.parse(currentTime));
			newCal.setTime(dateFormat.parse(scheduledReservationTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long currTime = cal.getTimeInMillis();
		long reservationTime = newCal.getTimeInMillis();
		if (currTime - reservationTime >= 10 * ONE_MINUTE_IN_MILLIS) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method displays the empty reservation window.
	 * 
	 * @return boolean
	 */
	private boolean displayEmptyQueue() {
		JFrame mainFrame = initializeFrame("TA Portal");
		JLabel noReservationLabel = new JLabel("No Reservations Found. Please click on exit.");
		noReservationLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		noReservationLabel.setForeground(Color.white);
		noReservationLabel.setBounds(400, 240, 1400, 100);
		mainFrame.getContentPane().add(noReservationLabel);

		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Verdana", Font.BOLD, 30));
		exitButton.setForeground(Color.black);
		exitButton.setBounds(500, 630, 200, 100);
		mainFrame.getContentPane().add(exitButton);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
			}
		});
		mainFrame.setVisible(true);
		return true;
	}

	/**
	 * This method initializes a new JFrame with the name provided.
	 * 
	 * @param frameName
	 * @return JFrame
	 */
	public JFrame initializeFrame(String frameName) {
		JFrame mainFrame = new JFrame(frameName);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize(screenSize.width, screenSize.height);
		mainFrame.setBounds(0, 0, screenSize.width, screenSize.height);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.getContentPane().setBackground(Color.gray);
		return mainFrame;
	}

	/**
	 * This method initializes a label inside a frame.
	 * 
	 * @param mainFrame  Original frame created.
	 * @param portalName String used for displaying the gui window name.
	 */
	public boolean initializeLabel(JFrame mainFrame, String portalName) {
		JLabel taLabel = new JLabel(portalName);
		taLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		taLabel.setForeground(Color.white);
		taLabel.setBounds(500, 20, 500, 100);
		mainFrame.getContentPane().add(taLabel);
		return true;
	}

	/**
	 * This method shuffles the question list array.
	 * 
	 * @param questionList ArrayList of strings
	 * @return Array List of strings
	 */
	private static List<String> getShuffledQuestionList(List<String> questionList) {
		Collections.shuffle(questionList);
		return questionList;
	}

	/**
	 * This method shuffles the time list array.
	 * 
	 * @param timeList ArrayList of strings
	 * @return List<String>
	 */
	private static List<String> getShuffledTimeList(List<String> timeList) {
		Collections.shuffle(timeList);
		return timeList;
	}

	/**
	 * This method shuffles the name list array.
	 * 
	 * @param keys Array List of strings
	 * @return List<String>
	 */
	private static List<String> getShuffledNameList(List<String> keys) {
		Collections.shuffle(keys);
		return keys;
	}

	/**
	 * This method returns the old time after subtracting the minutes passed from
	 * the current time.
	 * 
	 * @param minutes number of minutes.
	 * @return String
	 */
	public String getTime(int minutes, String timeNow) {
		final long ONE_MINUTE_IN_MILLIS = 60000;
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateFormat.parse(timeNow));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long currTime = cal.getTimeInMillis();
		Date newTime = new Date(currTime - (minutes * ONE_MINUTE_IN_MILLIS));
		String dateFormatted = String.valueOf(dateFormat.format(newTime));
		finalTimeList.add(dateFormatted);
		return dateFormatted;
	}

}
