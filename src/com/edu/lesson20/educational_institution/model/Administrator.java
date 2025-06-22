package com.edu.lesson20.educational_institution.model;

public class Administrator extends Person {
    private String department;

    public Administrator(String name, int id, String department) {
        super(name, id);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String performRole() {
        return getName() + " (Администратор) оказывает административную поддержку для отдела " + department + ".";
    }

    @Override
    public String toString() {
        return "Administrator - " + super.toString() + ", Department: " + department;
    }
} 