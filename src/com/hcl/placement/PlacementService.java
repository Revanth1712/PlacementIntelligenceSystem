package com.hcl.placement;

import java.util.ArrayList;
import java.util.List;

public class PlacementService {

    private List<Student> students = new ArrayList<>();
    private ValidationService validationService = new ValidationService();
    
    public void addStudent(Student student) {

        if (!validationService.validateStudent(student)) {
            return;
        }

        boolean exists = students.stream()
                .anyMatch(s -> s.getStudentId() == student.getStudentId());

        if (exists) {
            System.out.println("Error: Student ID already exists!");
            return;
        }

        students.add(student);
        System.out.println("Student added successfully!");
    }
        public void searchStudent(int studentId) {

            students.stream()
                    .filter(s -> s.getStudentId() == studentId)
                    .findFirst()
                    .ifPresentOrElse(
                        student -> System.out.println(
                            "Student Found: " + student.getName()),
                        () -> System.out.println(
                            "Student not found!")
                    );
        }
        public void removeStudent(int studentId) {

            boolean removed = students.removeIf(
                    s -> s.getStudentId() == studentId
            );

            if (removed) {
                System.out.println("Student removed successfully!");
            } else {
                System.out.println("Student not found!");
            }
        }
        
        public void displayStudents() {

            if (students.isEmpty()) {
                System.out.println("No students available.");
                return;
            }

            for (Student student : students) {
                System.out.println("----------------------------");
                System.out.println("Student ID: " + student.getStudentId());
                System.out.println("Name: " + student.getName());
                System.out.println("Department: " + student.getDepartment());
                System.out.println("CGPA: " + student.getCgpa());
                System.out.println("Coding Score: " + student.getCodingScore());
                System.out.println("Aptitude Score: " + student.getAptitudeScore());
                System.out.println("Interview Score: " + student.getInterviewScore());
                System.out.println("Attendance: " + student.getAttendance());
                System.out.println("Technical Skills: " + student.getTechnicalSkills());
            }
            
            
        }
        
        public Student getStudent(int studentId) {
            return students.stream()
                    .filter(s -> s.getStudentId() == studentId)
                    .findFirst()
                    .orElse(null);
        }
        
        public List<Student> getAllStudents() {
            return students;
        }
        

}