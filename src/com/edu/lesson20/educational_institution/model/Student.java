package com.edu.lesson20.educational_institution.model;

public class Student extends Person {
    private String group;
    private double averageGrade;

    public Student(String name, int id, String group, double averageGrade) {
        super(name, id);
        this.group = group;
        this.averageGrade = averageGrade;
    }

    public String getGroup() {
        return group;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getObfuscatedName() {
        return getName().replaceAll("[aeiouAEIOU]", "*");
    }

    @Override
    public String performRole() {
        return getName() + " (Студент) участвует в занятии.";
    }

    @Override
    public String toString() {
        return "Student - " + super.toString() + ", Group: " + group + ", Average Grade: " + averageGrade;
    }
} 