package com.edu.lesson20.educational_institution.ui;

import com.edu.lesson20.educational_institution.model.Administrator;
import com.edu.lesson20.educational_institution.model.Course;
import com.edu.lesson20.educational_institution.model.Student;
import com.edu.lesson20.educational_institution.model.Teacher;
import com.edu.lesson20.educational_institution.controller.ICourseController;
import com.edu.lesson20.educational_institution.exception.CourseNotFoundException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CourseConsoleUi {
    private ICourseController courseController;
    private Scanner scanner;
    private Course currentCourse;

    public CourseConsoleUi(ICourseController courseController) {
        this.courseController = courseController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Добро пожаловать в систему 'Образовательное учреждение'!");
        mainMenu();
    }

    private void mainMenu() {
        int choice;
        do {
            System.out.println("\n--- Главное меню ---");
            System.out.println("1. Создать новый курс");
            System.out.println("2. Загрузить курс из файла");
            System.out.println("3. Управление текущим курсом");
            System.out.println("0. Выход");
            System.out.print("Введите ваш выбор: ");
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    createNewCourse();
                    break;
                case 2:
                    loadCourse();
                    break;
                case 3:
                    if (currentCourse != null) {
                        manageCourseMenu();
                    } else {
                        System.out.println("Курс не загружен. Сначала создайте или загрузите курс.");
                    }
                    break;
                case 0:
                    System.out.println("Выход из системы. До свидания!");
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        } while (choice != 0);
    }

    private void createNewCourse() {
        System.out.println("\n--- Создать новый курс ---");
        System.out.print("Введите название курса: ");
        scanner.nextLine();
        String courseName = scanner.nextLine();

        System.out.print("Введите имя преподавателя: ");
        String teacherName = scanner.nextLine();
        System.out.print("Введите ID преподавателя: ");
        int teacherId = getValidIntInput();
        System.out.print("Введите предмет преподавателя: ");
        scanner.nextLine();
        String teacherSubject = scanner.nextLine();
        Teacher teacher = new Teacher(teacherName, teacherId, teacherSubject);

        System.out.print("Введите имя администратора: ");
        String adminName = scanner.nextLine();
        System.out.print("Введите ID администратора: ");
        int adminId = getValidIntInput();
        System.out.print("Введите отдел администратора: ");
        scanner.nextLine();
        String adminDepartment = scanner.nextLine();
        Administrator administrator = new Administrator(adminName, adminId, adminDepartment);

        currentCourse = courseController.createCourse(courseName, teacher, administrator);
        System.out.println("Курс '" + courseName + "' успешно создан!");
        System.out.print("Хотите сразу сохранить курс в файл? (y/n): ");
        String answer = scanner.nextLine();
        if (answer.trim().equalsIgnoreCase("y")) {
            String result = courseController.saveCourse(currentCourse);
            System.out.println(result);
        }
    }

    private void loadCourse() {
        System.out.println("\n--- Загрузить курс из файла ---");
        try {
            currentCourse = courseController.loadCourse();
            System.out.println("Курс успешно загружен.");
        } catch (CourseNotFoundException e) {
            System.out.println("Ошибка загрузки курса: " + e.getMessage());
            currentCourse = null;
        }
    }

    private void manageCourseMenu() {
        int choice;
        do {
            System.out.println("\n--- Управление курсом: " + currentCourse.getCourseName() + " ---");
            System.out.println("1. Показать информацию о курсе");
            System.out.println("2. Добавить студента");
            System.out.println("3. Провести занятие");
            System.out.println("4. Сгенерировать отчет по студентам");
            System.out.println("5. Сохранить курс в файл");
            System.out.println("0. Назад в главное меню");
            System.out.print("Введите ваш выбор: ");
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println(courseController.getCourseInfo(currentCourse));
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    System.out.println(courseController.conductLesson(currentCourse));
                    break;
                case 4:
                    System.out.println(courseController.generateStudentReport(currentCourse));
                    break;
                case 5:
                    saveCourse();
                    break;
                case 0:
                    System.out.println("Возвращаюсь в главное меню.");
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        } while (choice != 0);
    }

    private void addStudent() {
        System.out.println("\n--- Добавить студента ---");
        System.out.print("Введите имя студента: ");
        scanner.nextLine();
        String studentName = scanner.nextLine();
        System.out.print("Введите ID студента: ");
        int studentId = getValidIntInput();
        System.out.print("Введите группу студента: ");
        scanner.nextLine();
        String studentGroup = scanner.nextLine();
        System.out.print("Введите средний балл студента: ");
        double averageGrade = getValidDoubleInput();

        Student student = new Student(studentName, studentId, studentGroup, averageGrade);
        boolean added = courseController.addStudentToCourse(currentCourse, student);
        if (added) {
            System.out.println("Студент " + student.getName() + " успешно добавлен на курс.");
        } else {
            System.out.println("Не удалось добавить студента " + student.getName() + " на курс. Возможно, он уже существует или введены неверные данные.");
        }
    }

    private void saveCourse() {
        System.out.println("\n--- Сохранить курс в файл ---");
        String result = courseController.saveCourse(currentCourse);
        System.out.println(result);
    }

    private int getUserChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
        return choice;
    }

    private int getValidIntInput() {
        int value = -1;
        boolean valid = false;
        while (!valid) {
            try {
                value = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("Ошибка ввода. Введите целое число: ");
                scanner.nextLine();
            }
        }
        return value;
    }

    private double getValidDoubleInput() {
        double value = -1;
        boolean valid = false;
        while (!valid) {
            try {
                value = scanner.nextDouble();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.print("Ошибка ввода. Введите число: ");
                scanner.nextLine();
            }
        }
        return value;
    }
} 