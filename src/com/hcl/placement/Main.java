package com.hcl.placement;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        PlacementService service = new PlacementService();
        RankingService rankingService = new RankingService();
        PlacementEvaluator evaluator = new PlacementEvaluator();
        AnalyticsService analyticsService = new AnalyticsService();

        // Load students from CSV
        List<Student> students =
                CSVReader.readStudents("students.csv");

        for (Student student : students) {
            service.addStudent(student);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== PLACEMENT INTELLIGENCE SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Find Student");
            System.out.println("4. Display Students");
            System.out.println("5. Evaluate Placement Readiness");
            System.out.println("6. Display Ranking");
            System.out.println("7. Analytics & Reports");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    // Add student from console

                	System.out.print("Enter Student ID: ");

                	int id;

                	while (!scanner.hasNextInt()) {
                	    System.out.println("Invalid input. Please enter a whole number.");
                	    scanner.next();
                	}

                	id = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();

                    System.out.print("Enter CGPA: ");

                    double cgpa;

                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }

                    cgpa = scanner.nextDouble();

                    System.out.print("Enter Coding Score: ");

                    int codingScore;

                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }

                    codingScore = scanner.nextInt();

                    System.out.print("Enter Aptitude Score: ");

                    int aptitudeScore;

                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }

                    aptitudeScore = scanner.nextInt();

                    System.out.print("Enter Interview Score: ");

                    int interviewScore;

                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }

                    interviewScore = scanner.nextInt();

                    System.out.print("Enter Attendance: ");

                    double attendance;

                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }

                    attendance = scanner.nextDouble();
                    
                    scanner.nextLine();

                    System.out.print(
                            "Enter Technical Skills (separated by space): ");
                    String skillInput = scanner.nextLine();

                    List<String> skills =
                            List.of(skillInput.split(" "));

                    Student student = new Student(
                            id,
                            name,
                            department,
                            cgpa,
                            codingScore,
                            aptitudeScore,
                            interviewScore,
                            attendance,
                            skills
                    );

                    service.addStudent(student);
                    break;

                case 2:
                    // Delete student

                    System.out.print(
                            "Enter Student ID to delete: ");
                    int deleteId = scanner.nextInt();

                    service.removeStudent(deleteId);
                    break;

                case 3:
                    // Find student

                    System.out.print(
                            "Enter Student ID to find: ");
                    int findId = scanner.nextInt();

                    service.searchStudent(findId);
                    break;

                case 4:
                    // Display all students

                    service.displayStudents();
                    break;

                case 5:
                    // Evaluate placement readiness

                    System.out.print(
                            "Enter Student ID to evaluate: ");
                    int evaluationId = scanner.nextInt();

                    Student studentToEvaluate =
                            service.getStudent(evaluationId);

                    if (studentToEvaluate != null) {

                        String result =
                                evaluator.evaluate(studentToEvaluate);

                        System.out.println(
                                "Placement Status: " + result);

                    } else {

                        System.out.println(
                                "Student not found!");
                    }

                    break;

                case 6:

                    System.out.println("\n===== STUDENT RANKING =====");

                    rankingService.displayRanking(service.getAllStudents());

                    break;
                
                    
                case 7:
                    System.out.println("\n===== ANALYTICS & REPORTS =====");
                    System.out.println("1. Top 5 Students");
                    System.out.println("2. Best-Performing Department");
                    System.out.println("3. Average Coding Score by Department");
                    System.out.println("4. Most Common Technical Skills");
                    System.out.println("5. Students with Most Skills");
                    System.out.println("6. Students Requiring Improvement");
                    System.out.print("Enter your choice: ");

                    int analyticsChoice = scanner.nextInt();

                    switch (analyticsChoice) {

                        case 1:
                            analyticsService.displayTop5Students(
                                    service.getAllStudents());
                            break;

                        case 2:
                            analyticsService.displayBestDepartment(
                                    service.getAllStudents());
                            break;

                        case 3:
                            analyticsService.displayAverageCodingScoreByDepartment(
                                    service.getAllStudents());
                            break;

                        case 4:
                            analyticsService.displayMostCommonSkills(
                                    service.getAllStudents());
                            break;

                        case 5:
                            analyticsService.displayStudentsWithMostSkills(
                                    service.getAllStudents());
                            break;

                        case 6:
                            analyticsService.displayStudentsRequiringImprovement(
                                    service.getAllStudents(),
                                    evaluator);
                            break;

                        default:
                            System.out.println("Invalid analytics choice!");
                    }

                    break; 
                    
                case 8:
                    // Exit

                    System.out.println("Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}