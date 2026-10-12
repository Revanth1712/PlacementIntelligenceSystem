package com.hcl.placement;

public class PlacementEvaluator {

    public String evaluate(Student student) {

        double cgpa = student.getCgpa();
        int codingScore = student.getCodingScore();
        int aptitudeScore = student.getAptitudeScore();
        int interviewScore = student.getInterviewScore();
        double attendance = student.getAttendance();
        int skillCount = student.getTechnicalSkills().size();

        // Special policy for CGPA between 7.0 and 7.5
        if (cgpa >= 7.0 && cgpa < 7.5
                && codingScore >= 80
                && skillCount >= 3) {

            return "PLACEMENT READY";
        }

        // Normal placement-ready criteria
        if (cgpa >= 7.5
                && codingScore >= 70
                && aptitudeScore >= 60
                && interviewScore >= 60
                && attendance >= 75
                && skillCount >= 2) {

            return "PLACEMENT READY";
        }

        // Students with good CGPA but needing improvement
        if (cgpa >= 7.5) {
            return "NEEDS IMPROVEMENT";
        }

        // Remaining students
        return "NOT ELIGIBLE";
    }
}