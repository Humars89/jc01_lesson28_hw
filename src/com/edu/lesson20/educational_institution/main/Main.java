package com.edu.lesson20.educational_institution.main;

import com.edu.lesson20.educational_institution.controller.ICourseController;
import com.edu.lesson20.educational_institution.controller.impl.CourseControllerImpl;
import com.edu.lesson20.educational_institution.repository.ICourseRepository;
import com.edu.lesson20.educational_institution.repository.impl.CourseRepositoryImpl;
import com.edu.lesson20.educational_institution.service.ICourseService;
import com.edu.lesson20.educational_institution.service.impl.CourseServiceImpl;
import com.edu.lesson20.educational_institution.ui.CourseConsoleUi;

public class Main {
    public static void main(String[] args) {
        ICourseRepository courseRepository = new CourseRepositoryImpl();
        ICourseService courseService = new CourseServiceImpl(courseRepository);
        ICourseController courseController = new CourseControllerImpl(courseService);
        CourseConsoleUi ui = new CourseConsoleUi(courseController);
        ui.start();
    }
}
