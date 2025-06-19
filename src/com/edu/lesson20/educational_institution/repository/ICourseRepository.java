package com.edu.lesson20.educational_institution.repository;

import com.edu.lesson20.educational_institution.model.Course;

public interface ICourseRepository {
    String saveCourseToFile(Course course);
    Course loadCourseFromFile();
} 