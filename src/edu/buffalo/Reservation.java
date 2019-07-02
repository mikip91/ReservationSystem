package edu.buffalo;

/**
 * This is a POJO class for reservation. The reservation class has the following properties student, reservationId, 
 * reservationTime and reservationStatus.
 * @author Miki Padhiary
 *
 */
public class Reservation {
	
	private Student student;
	private int reservationId;
	private String reservationTime;
	private String reservationStatus;
	
	//Constructor for reservation class.
	public Reservation(int reservationId, String reservationTime, Student student)
	{
		this.reservationId = reservationId;
		this.reservationTime = reservationTime;
		this.student = student;
	}
	protected Student getStudent() {
		return student;
	}
	protected void setStudent(Student student) {
		this.student = student;
	}
	
	protected int getReservationId() {
		return reservationId;
	}
	protected void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}	
	
	protected String getReservationTime() {
		return reservationTime;
	}
	protected void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}
	
	protected String getReservationStatus() {
		return reservationStatus;
	}
	protected void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reservationId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (reservationId != other.reservationId)
			return false;
		return true;
	}
	
}
