package com.hcl.placement;

public class ValidationService {

    public boolean validateStudent(Student student) {

        if (student.getStudentId() <= 0) {
            System.out.println("Error: Student ID must be positive.");
            return false;
        }

        if (student.getName() == null
                || student.getName().isBlank()) {
            System.out.println("Error: Student name is required.");
            return false;
        }

        if (student.getDepartment() == null
                || student.getDepartment().isBlank()) {
            System.out.println("Error: Department is required.");
            return false;
        }

        if (student.getCgpa() < 0
                || student.getCgpa() > 10) {
            System.out.println(
                    "Error: CGPA must be between 0 and 10.");
            return false;
        }

        if (student.getCodingScore() < 0
                || student.getCodingScore() > 100) {
            System.out.println(
                    "Error: Coding score must be between 0 and 100.");
            return false;
        }

        if (student.getAptitudeScore() < 0
                || student.getAptitudeScore() > 100) {
            System.out.println(
                    "Error: Aptitude score must be between 0 and 100.");
            return false;
        }

        if (student.getInterviewScore() < 0
                || student.getInterviewScore() > 100) {
            System.out.println(
                    "Error: Interview score must be between 0 and 100.");
            return false;
        }

        if (student.getAttendance() < 0
                || student.getAttendance() > 100) {
            System.out.println(
                    "Error: Attendance must be between 0 and 100.");
            return false;
        }

        if (student.getTechnicalSkills() == null
                || student.getTechnicalSkills().isEmpty()) {
            System.out.println(
                    "Error: At least one technical skill is required.");
            return false;
        }

        return true;
    }
}