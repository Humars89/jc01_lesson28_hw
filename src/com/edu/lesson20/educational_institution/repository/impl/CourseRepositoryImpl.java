package com.edu.lesson20.educational_institution.repository.impl;

import com.edu.lesson20.educational_institution.model.*;
import com.edu.lesson20.educational_institution.repository.ICourseRepository;
import com.edu.lesson20.educational_institution.exception.CourseNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryImpl implements ICourseRepository {
    private static final String DEFAULT_FILENAME = "course.txt";

    @Override
    public String saveCourseToFile(Course course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_FILENAME))) {
            writer.write("CourseName:" + course.getCourseName() + "\n");
            writer.write("Teacher:" + course.getTeacher().getName() + "," + course.getTeacher().getId() + "," + course.getTeacher().getSubject() + "\n");
            writer.write("Administrator:" + course.getAdministrator().getName() + "," + course.getAdministrator().getId() + "," + course.getAdministrator().getDepartment() + "\n");
            writer.write("Students:\n");
            for (Student student : course.getStudents()) {
                writer.write(student.getName() + "," + student.getId() + "," + student.getGroup() + "," + student.getAverageGrade() + "\n");
            }
            return "Данные курса сохранены в файл: " + DEFAULT_FILENAME;
        } catch (IOException e) {
            return "Ошибка при сохранении данных курса: " + e.getMessage();
        }
    }

    @Override
    public Course loadCourseFromFile() {
        Course course = null;
        File file = new File(DEFAULT_FILENAME);
        if (!file.exists()) {
            throw new CourseNotFoundException("Файл курса не найден: " + DEFAULT_FILENAME);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String courseName = null;
            Teacher teacher = null;
            Administrator administrator = null;
            List<Student> loadedStudents = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("CourseName:")) {
                    courseName = line.substring("CourseName:".length());
                } else if (line.startsWith("Teacher:")) {
                    String[] parts = line.substring("Teacher:".length()).split(",");
                    teacher = new Teacher(parts[0], Integer.parseInt(parts[1]), parts[2]);
                } else if (line.startsWith("Administrator:")) {
                    String[] parts = line.substring("Administrator:".length()).split(",");
                    administrator = new Administrator(parts[0], Integer.parseInt(parts[1]), parts[2]);
                } else if (line.equals("Students:")) {
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        loadedStudents.add(new Student(parts[0], Integer.parseInt(parts[1]), parts[2], Double.parseDouble(parts[3])));
                    }
                }
            }
            if (courseName != null && teacher != null && administrator != null) {
                course = new Course(courseName, teacher, administrator);
                course.getStudents().addAll(loadedStudents);
            } else {
                throw new CourseNotFoundException("Неполные данные курса в файле: " + DEFAULT_FILENAME);
            }
        } catch (IOException e) {
            throw new CourseNotFoundException("Ошибка при загрузке данных курса: " + e.getMessage());
        }
        return course;
    }
} 