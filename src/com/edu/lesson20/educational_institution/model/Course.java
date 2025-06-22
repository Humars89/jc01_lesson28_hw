package com.edu.lesson20.educational_institution.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private Teacher teacher;
    private Administrator administrator;
    private List<Student> students;

    public Course(String courseName, Teacher teacher, Administrator administrator) {
        this.courseName = courseName;
        this.teacher = teacher;
        this.administrator = administrator;
        this.students = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public List<Student> getStudents() {
        return students;
    }
} 