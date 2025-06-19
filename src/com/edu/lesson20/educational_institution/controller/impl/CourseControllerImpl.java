package com.edu.lesson20.educational_institution.controller.impl;

import com.edu.lesson20.educational_institution.model.*;
import com.edu.lesson20.educational_institution.service.ICourseService;
import com.edu.lesson20.educational_institution.controller.ICourseController;
import java.util.List;
import com.edu.lesson20.educational_institution.exception.CourseNotFoundException;

public class CourseControllerImpl implements ICourseController {
    private final ICourseService courseService;

    public CourseControllerImpl(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public Course createCourse(String courseName, Teacher teacher, Administrator administrator) {
        return courseService.createCourse(courseName, teacher, administrator);
    }

    @Override
    public boolean addStudentToCourse(Course course, Student student) {
        return courseService.addStudentToCourse(course, student);
    }

    @Override
    public String getCourseInfo(Course course) {
        if (course == null) return "Курс не найден.";
        StringBuilder info = new StringBuilder();
        info.append("\n--- Информация о курсе: ").append(courseService.getCourseName(course)).append(" ---");
        info.append("\nПреподаватель: ").append(courseService.getTeacher(course));
        info.append("\nАдминистратор: ").append(courseService.getAdministrator(course));
        info.append("\nСтуденты (").append(courseService.getStudents(course).size()).append("):");
        if (courseService.getStudents(course).isEmpty()) {
            info.append("\n  Студенты пока не зачислены.");
        } else {
            for (Student student : courseService.getStudents(course)) {
                info.append("\n  - ").append(student);
            }
        }
        info.append("\n-----------------------------------");
        return info.toString();
    }

    @Override
    public List<Student> getStudents(Course course) {
        return courseService.getStudents(course);
    }

    @Override
    public Course loadCourse() throws CourseNotFoundException {
        return courseService.loadCourse();
    }

    @Override
    public String conductLesson(Course course) {
        if (course == null) {
            return "Курс не найден.";
        }
        StringBuilder report = new StringBuilder();
        report.append("\n--- Проведение занятия для: ").append(courseService.getCourseName(course)).append(" ---");
        report.append("\n").append(courseService.getTeacher(course).performRole());
        report.append("\n").append(courseService.getAdministrator(course).performRole());
        for (Student student : courseService.getStudents(course)) {
            report.append("\n").append(student.performRole());
        }
        report.append("\nЗанятие завершено.");
        return report.toString();
    }

    @Override
    public String generateStudentReport(Course course) {
        if (course == null) {
            return "Курс не найден.";
        }
        List<Student> students = courseService.getStudents(course);
        if (students.isEmpty()) {
            return "Нет студентов для отчета.";
        }
        StringBuilder report = new StringBuilder();
        report.append("\n--- Отчет по студентам для: ").append(courseService.getCourseName(course)).append(" ---");
        report.append("\nСтуденты:");
        students.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        double totalGrades = 0;
        for (Student student : students) {
            report.append(String.format("\n  - Имя: %s, Группа: %s, Средний балл: %.2f",
                    student.getName(), student.getGroup(), student.getAverageGrade()));
            totalGrades += student.getAverageGrade();
        }
        double averageCourseGrade = totalGrades / students.size();
        report.append(String.format("\n\nСредний балл по курсу: %.2f", averageCourseGrade));
        report.append("\n-----------------------------------");
        return report.toString();
    }

    @Override
    public String saveCourse(Course course) {
        return courseService.saveCourse(course);
    }
} 