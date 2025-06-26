package com.edu.lesson20.educational_institution.service.impl;

import com.edu.lesson20.educational_institution.model.*;
import com.edu.lesson20.educational_institution.repository.ICourseRepository;
import com.edu.lesson20.educational_institution.service.ICourseService;
import java.util.List;
import com.edu.lesson20.educational_institution.exception.CourseNotFoundException;

public class CourseServiceImpl implements ICourseService {
    private final ICourseRepository courseRepository;

    public CourseServiceImpl(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse(String courseName, Teacher teacher, Administrator administrator) {
        return new Course(courseName, teacher, administrator);
    }

    @Override
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

    @Override
    public String saveCourse(Course course) {
        return courseRepository.saveCourseToFile(course);
    }

    @Override
    public Course loadCourse() throws CourseNotFoundException {
        return courseRepository.loadCourseFromFile();
    }

    @Override
    public List<Student> getStudents(Course course) {
        return course.getStudents();
    }

    @Override
    public Teacher getTeacher(Course course) {
        return course.getTeacher();
    }

    @Override
    public Administrator getAdministrator(Course course) {
        return course.getAdministrator();
    }

    @Override
    public String getCourseName(Course course) {
        return course.getCourseName();
    }
} 