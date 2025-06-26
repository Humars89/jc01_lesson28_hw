package com.edu.lesson20.educational_institution.controller.impl;

import com.edu.lesson20.educational_institution.model.*;
import com.edu.lesson20.educational_institution.service.ICourseService;
import com.edu.lesson20.educational_institution.controller.ICourseController;
import java.util.List;
import com.edu.lesson20.educational_institution.exception.CourseNotFoundException;

// Command interface
interface Command<T> {
    T execute();
}

// Concrete command classes
class CreateCourseCommand implements Command<Course> {
    private final ICourseService service;
    private final String courseName;
    private final Teacher teacher;
    private final Administrator administrator;
    public CreateCourseCommand(ICourseService service, String courseName, Teacher teacher, Administrator administrator) {
        this.service = service;
        this.courseName = courseName;
        this.teacher = teacher;
        this.administrator = administrator;
    }
    @Override
    public Course execute() {
        return service.createCourse(courseName, teacher, administrator);
    }
}

class AddStudentToCourseCommand implements Command<Boolean> {
    private final ICourseService service;
    private final Course course;
    private final Student student;
    public AddStudentToCourseCommand(ICourseService service, Course course, Student student) {
        this.service = service;
        this.course = course;
        this.student = student;
    }
    @Override
    public Boolean execute() {
        return service.addStudentToCourse(course, student);
    }
}

class GetCourseInfoCommand implements Command<String> {
    private final ICourseService service;
    private final Course course;
    public GetCourseInfoCommand(ICourseService service, Course course) {
        this.service = service;
        this.course = course;
    }
    @Override
    public String execute() {
        if (course == null) return "Курс не найден.";
        StringBuilder info = new StringBuilder();
        info.append("\n--- Информация о курсе: ").append(service.getCourseName(course)).append(" ---");
        info.append("\nПреподаватель: ").append(service.getTeacher(course));
        info.append("\nАдминистратор: ").append(service.getAdministrator(course));
        info.append("\nСтуденты (").append(service.getStudents(course).size()).append("):");
        if (service.getStudents(course).isEmpty()) {
            info.append("\n  Студенты пока не зачислены.");
        } else {
            for (Student student : service.getStudents(course)) {
                info.append("\n  - ").append(student);
            }
        }
        info.append("\n-----------------------------------");
        return info.toString();
    }
}

public class CourseControllerImpl implements ICourseController {
    private final ICourseService courseService;

    public CourseControllerImpl(ICourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public Course createCourse(String courseName, Teacher teacher, Administrator administrator) {
        return new CreateCourseCommand(courseService, courseName, teacher, administrator).execute();
    }

    @Override
    public boolean addStudentToCourse(Course course, Student student) {
        return new AddStudentToCourseCommand(courseService, course, student).execute();
    }

    @Override
    public String getCourseInfo(Course course) {
        return new GetCourseInfoCommand(courseService, course).execute();
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