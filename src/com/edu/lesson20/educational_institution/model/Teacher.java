package com.edu.lesson20.educational_institution.model;

public class Teacher extends Person {
    private String subject;

    public Teacher(String name, int id, String subject) {
        super(name, id);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String performRole() {
        return getName() + " (Преподаватель) преподает урок по " + subject + ".";
    }

    @Override
    public String toString() {
        return "Teacher - " + super.toString() + ", Subject: " + subject;
    }
} 