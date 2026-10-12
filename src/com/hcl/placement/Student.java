package com.hcl.placement;

import java.util.List;

public class Student {

    private int studentId;
    private String name;
    private String department;
    private double cgpa;
    private int codingScore;
    private int aptitudeScore;
    private int interviewScore;
    private double attendance;
    private List<String> technicalSkills;

    public Student(int studentId, String name, String department,
                   double cgpa, int codingScore,
                   int aptitudeScore, int interviewScore,
                   double attendance, List<String> technicalSkills) {

        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.cgpa = cgpa;
        this.codingScore = codingScore;
        this.aptitudeScore = aptitudeScore;
        this.interviewScore = interviewScore;
        this.attendance = attendance;
        this.technicalSkills = technicalSkills;
    }

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getCgpa() {
		return cgpa;
	}

	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}

	public int getCodingScore() {
		return codingScore;
	}

	public void setCodingScore(int codingScore) {
		this.codingScore = codingScore;
	}

	public int getAptitudeScore() {
		return aptitudeScore;
	}

	public void setAptitudeScore(int aptitudeScore) {
		this.aptitudeScore = aptitudeScore;
	}

	public int getInterviewScore() {
		return interviewScore;
	}

	public void setInterviewScore(int interviewScore) {
		this.interviewScore = interviewScore;
	}

	public double getAttendance() {
		return attendance;
	}

	public void setAttendance(double attendance) {
		this.attendance = attendance;
	}

	public List<String> getTechnicalSkills() {
		return technicalSkills;
	}

	public void setTechnicalSkills(List<String> technicalSkills) {
		this.technicalSkills = technicalSkills;
	}
}