package com.edu.lesson20.educational_institution.service;

import com.edu.lesson20.educational_institution.model.Administrator;
import com.edu.lesson20.educational_institution.model.Course;
import com.edu.lesson20.educational_institution.model.Student;
import com.edu.lesson20.educational_institution.model.Teacher;
import com.edu.lesson20.educational_institution.repository.CourseRepository;
import com.edu.lesson20.educational_institution.exception.CourseNotFoundException;

import java.util.List;

public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(String courseName, Teacher teacher, Administrator administrator) {
        return new Course(courseName, teacher, administrator);
    }

    public boolean addStudentToCourse(Course course, Student student) {
        if (course == null || student == null) {
            return false;
        }
        if (course.getStudents().contains(student)) {
            return false;
        }
        course.getStudents().add(student);
        return true;
    }

    public String saveCourse(Course course) {
        return courseRepository.saveCourseToFile(course);
    }

    public Course loadCourse() throws CourseNotFoundException {
        return courseRepository.loadCourseFromFile();
    }

    public List<Student> getStudents(Course course) {
        return course.getStudents();
    }

    public Teacher getTeacher(Course course) {
        return course.getTeacher();
    }

    public Administrator getAdministrator(Course course) {
        return course.getAdministrator();
    }

    public String getCourseName(Course course) {
        return course.getCourseName();
    }
} 