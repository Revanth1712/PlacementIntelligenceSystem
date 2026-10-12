package com.hcl.placement;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class AnalyticsService {

    private final RankingService rankingService =
            new RankingService();

    // 1. Get Top 5 Students
    public List<Student> getTop5Students(List<Student> students) {

        return students.stream()
                .sorted(Comparator.comparingDouble(
                        rankingService::calculateScore).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    // 2. Get Best-Performing Department
    public Map.Entry<String, Double> getBestDepartment(
            List<Student> students) {

        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::getDepartment,
                        Collectors.averagingDouble(
                                rankingService::calculateScore)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }

    // 3. Get Average Coding Score by Department
    public Map<String, Double> getAverageCodingScoreByDepartment(
            List<Student> students) {

        Map<String, Double> result = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getDepartment,
                        Collectors.averagingInt(
                                Student::getCodingScore)));

        return result.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (first, second) -> first,
                        LinkedHashMap::new));
    }

    // 4. Get Most Common Technical Skills
    public Map<String, Long> getMostCommonSkills(
            List<Student> students) {

        return students.stream()
                .flatMap(student ->
                        student.getTechnicalSkills().stream())
                .map(String::trim)
                .filter(skill -> !skill.isEmpty())
                .collect(Collectors.groupingBy(
                        String::toLowerCase,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (first, second) -> first,
                        LinkedHashMap::new));
    }

    // 5. Get Students with the Most Technical Skills
    public List<Student> getStudentsWithMostSkills(
            List<Student> students) {

        return students.stream()
                .sorted(Comparator.comparingInt(
                        (Student student) ->
                                student.getTechnicalSkills().size())
                        .reversed()
                        .thenComparing(Student::getName))
                .collect(Collectors.toList());
    }

    // 6. Get Students Requiring Improvement
    // Includes both NEEDS IMPROVEMENT and NOT ELIGIBLE.
    public List<Student> getStudentsRequiringImprovement(
            List<Student> students,
            PlacementEvaluator evaluator) {

        return students.stream()
                .filter(student -> {
                    String status = evaluator.evaluate(student);

                    return "NEEDS IMPROVEMENT".equals(status)
                            || "NOT ELIGIBLE".equals(status);
                })
                .collect(Collectors.toList());
    }

    // Keep the original console-report methods available.

    public void displayTop5Students(List<Student> students) {
        System.out.println("\n===== TOP 5 STUDENTS =====");

        getTop5Students(students).forEach(student ->
                System.out.println(
                        student.getStudentId() + " - "
                                + student.getName() + " - "
                                + String.format("%.2f",
                                rankingService.calculateScore(student))));
    }

    public void displayBestDepartment(List<Student> students) {
        System.out.println("\n===== BEST-PERFORMING DEPARTMENT =====");

        Map.Entry<String, Double> best =
                getBestDepartment(students);

        if (best != null) {
            System.out.println("Department: " + best.getKey());
            System.out.println("Average Score: "
                    + String.format("%.2f", best.getValue()));
        } else {
            System.out.println("No student data available.");
        }
    }

    public void displayAverageCodingScoreByDepartment(
            List<Student> students) {

        System.out.println(
                "\n===== AVERAGE CODING SCORE BY DEPARTMENT =====");

        getAverageCodingScoreByDepartment(students)
                .forEach((department, average) ->
                        System.out.println(department + " - "
                                + String.format("%.2f", average)));
    }

    public void displayMostCommonSkills(List<Student> students) {
        System.out.println("\n===== MOST COMMON TECHNICAL SKILLS =====");

        getMostCommonSkills(students)
                .forEach((skill, count) ->
                        System.out.println(skill + " - "
                                + count + " students"));
    }

    public void displayStudentsWithMostSkills(
            List<Student> students) {

        System.out.println("\n===== STUDENTS WITH MOST SKILLS =====");

        getStudentsWithMostSkills(students)
                .forEach(student ->
                        System.out.println(student.getName() + " - "
                                + student.getTechnicalSkills().size()
                                + " skills"));
    }

    public void displayStudentsRequiringImprovement(
            List<Student> students,
            PlacementEvaluator evaluator) {

        System.out.println(
                "\n===== STUDENTS REQUIRING IMPROVEMENT =====");

        getStudentsRequiringImprovement(students, evaluator)
                .forEach(student ->
                        System.out.println(
                                student.getStudentId() + " - "
                                        + student.getName() + " - "
                                        + evaluator.evaluate(student)));
    }
}