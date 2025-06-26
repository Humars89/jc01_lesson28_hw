package com.edu.lesson20.educational_institution.controller;

import com.edu.lesson20.educational_institution.model.*;
import java.util.List;

public interface ICourseController {
    Course createCourse(String courseName, Teacher teacher, Administrator administrator);
    boolean addStudentToCourse(Course course, Student student);
    String getCourseInfo(Course course);
    List<Student> getStudents(Course course);
    Course loadCourse();
    String conductLesson(Course course);
    String generateStudentReport(Course course);
    String saveCourse(Course course);
} 