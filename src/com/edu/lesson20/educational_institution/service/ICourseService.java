package com.edu.lesson20.educational_institution.service;

import com.edu.lesson20.educational_institution.model.*;
import java.util.List;

public interface ICourseService {
    Course createCourse(String courseName, Teacher teacher, Administrator administrator);
    boolean addStudentToCourse(Course course, Student student);
    String saveCourse(Course course);
    Course loadCourse();
    List<Student> getStudents(Course course);
    Teacher getTeacher(Course course);
    Administrator getAdministrator(Course course);
    String getCourseName(Course course);
} 