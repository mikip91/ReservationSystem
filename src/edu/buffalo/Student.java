package edu.buffalo;

import java.util.Date;

/**
 * This is a POJO class for student. The student has the following properties studentName, studentEmailId, bannedDate, student
 * Queries.
 * @author Miki Padhiary
 *
 */

public class Student {
	
	private String studentName;
	private String studentEmailId;
	private Date bannedDate;
	private String studentQueries;
	
	//Constructor for student class
	public Student(String studentName, String studentEmailId, Date bannedDate, String studentQueries) {			
			this.studentName = studentName;
			this.studentEmailId = studentEmailId;
			this.bannedDate = bannedDate;
			this.studentQueries = studentQueries;
		}

	
	protected String getStudentName() {
		return studentName;
	}
	protected void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	protected String getStudentEmailId() {
		return studentEmailId;
	}
	protected void setStudentEmailId(String studentEmailId) {
		this.studentEmailId = studentEmailId;
	}
	protected Date getBannedDate() {
		return bannedDate;
	}
	protected void setBannedDate(Date bannedDate) {
		this.bannedDate = bannedDate;
	}
	protected String getStudentQueries() {
		return studentQueries;
	}
	protected void setStudentQueries(String studentQueries) {
		this.studentQueries = studentQueries;
	}

	
	
}
