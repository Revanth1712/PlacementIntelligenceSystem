
package com.hcl.placement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    // =====================================================
    // READ STUDENTS
    // =====================================================

    public static List<Student> readStudents(String fileName) {
        List<Student> students = new ArrayList<>();
        ValidationService validationService =
                new ValidationService();

        Path path = Path.of(fileName);

        if (!Files.exists(path)) {
            System.out.println(
                    "CSV file not found: " + path.toAbsolutePath());
            return students;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                path, StandardCharsets.UTF_8)) {

            // Skip CSV header.
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] data = line.split(",", -1);

                    if (data.length < 9) {
                        System.out.println(
                                "Invalid CSV row. Skipping: " + line);
                        continue;
                    }

                    int studentId =
                            Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String department = data[2].trim();
                    double cgpa =
                            Double.parseDouble(data[3].trim());
                    int codingScore =
                            Integer.parseInt(data[4].trim());
                    int aptitudeScore =
                            Integer.parseInt(data[5].trim());
                    int interviewScore =
                            Integer.parseInt(data[6].trim());
                    double attendance =
                            Double.parseDouble(data[7].trim());

                    // Parse skills whether separated by | or commas.
                    List<String> skills = parseSkills(data[8]);

                    Student student = new Student(
                            studentId,
                            name,
                            department,
                            cgpa,
                            codingScore,
                            aptitudeScore,
                            interviewScore,
                            attendance,
                            skills
                    );

                    if (validationService.validateStudent(student)) {
                        students.add(student);
                    } else {
                        System.out.println(
                                "Invalid student data. Skipping ID: "
                                        + studentId);
                    }

                } catch (NumberFormatException ex) {
                    System.out.println(
                            "Invalid numeric data. Skipping row: "
                                    + line);
                }
            }

        } catch (IOException ex) {
            System.out.println(
                    "Error reading CSV file: " + ex.getMessage());
        }

        return students;
    }

    // =====================================================
    // PARSE TECHNICAL SKILLS
    // =====================================================

    private static List<String> parseSkills(String skillsText) {
        List<String> skills = new ArrayList<>();

        if (skillsText == null || skillsText.trim().isEmpty()) {
            return skills;
        }

        /*
         * Normal format: Java|Python|C
         * Also accepted: Java,Python,C
         * Also accepted: Java Python C
         *
         * The whitespace format is supported for the malformed
         * existing record shown in the user's screenshot.
         */
        String[] parts;

        if (skillsText.contains("|") || skillsText.contains(",")) {
            parts = skillsText.split("[|,]");
        } else {
            parts = skillsText.trim().split("\\s+");
        }

        for (String part : parts) {
            String skill = part.trim();

            if (!skill.isEmpty()
                    && !skills.contains(skill)) {
                skills.add(skill);
            }
        }

        return skills;
    }

    // =====================================================
    // APPEND STUDENT
    // =====================================================

    public static void appendStudent(
            String fileName,
            Student student) throws IOException {

        File file = new File(fileName);

        boolean writeHeader =
                !file.exists() || file.length() == 0;

        try (BufferedWriter writer = Files.newBufferedWriter(
                file.toPath(),
                StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND)) {

            if (writeHeader) {
                writer.write(
                        "studentId,name,department,cgpa,codingScore,"
                                + "aptitudeScore,interviewScore,"
                                + "attendance,technicalSkills");
                writer.newLine();
            }

            String skills = String.join(
                    "|", student.getTechnicalSkills());

            String record = student.getStudentId() + ","
                    + student.getName() + ","
                    + student.getDepartment() + ","
                    + student.getCgpa() + ","
                    + student.getCodingScore() + ","
                    + student.getAptitudeScore() + ","
                    + student.getInterviewScore() + ","
                    + student.getAttendance() + ","
                    + skills;

            writer.write(record);
            writer.newLine();
        }
    }

    // =====================================================
    // DELETE STUDENT
    // =====================================================

    public static boolean deleteStudent(
            String fileName,
            int studentId) throws IOException {

        Path file = Path.of(fileName);

        if (!Files.exists(file)) {
            return false;
        }

        List<String> lines = Files.readAllLines(
                file, StandardCharsets.UTF_8);

        if (lines.isEmpty()) {
            return false;
        }

        List<String> updatedLines = new ArrayList<>();
        updatedLines.add(lines.get(0));

        boolean deleted = false;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] columns = line.split(",", 2);

            if (columns.length > 0
                    && columns[0].trim().equals(
                            String.valueOf(studentId))) {
                deleted = true;
            } else {
                updatedLines.add(line);
            }
        }

        if (!deleted) {
            return false;
        }

        Path tempFile = file.resolveSibling(
                file.getFileName().toString() + ".tmp");

        Files.write(
                tempFile,
                updatedLines,
                StandardCharsets.UTF_8);

        Files.move(
                tempFile,
                file,
                StandardCopyOption.REPLACE_EXISTING);

        return true;
    }
}
