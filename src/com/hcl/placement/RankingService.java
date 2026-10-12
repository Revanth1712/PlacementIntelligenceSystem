package com.hcl.placement;

import java.util.Comparator;
import java.util.List;

public class RankingService {

    public double calculateScore(Student student) {

        double cgpaScore = student.getCgpa() * 10;

        return (cgpaScore * 0.20)
                + (student.getCodingScore() * 0.30)
                + (student.getAptitudeScore() * 0.20)
                + (student.getInterviewScore() * 0.20)
                + (student.getAttendance() * 0.10);
    }

    public void displayRanking(List<Student> students) {

        students.stream()
                .sorted(Comparator.comparingDouble(this::calculateScore)
                        .reversed())
                .forEach(student -> {

                    double score = calculateScore(student);

                    System.out.println(
                            student.getStudentId()
                            + " - "
                            + student.getName()
                            + " - Score: "
                            + String.format("%.2f", score));
                });
    }
}