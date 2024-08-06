package UserController;

import classes.Course;
import classes.Student;
import dataBase.CRUD;

import java.io.File;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Logout {

    public static final File STUDENT_DIR = new File( "C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");
    public static final File COURSE_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Courses");
    public static final File ALL_USERS = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");

    public static void logout(Student s){
        var courses = s.getCourseIds().stream().map(Course::new).toList();
        for (Course c : courses){
            CRUD.deleteLine(STR."STUDENT:\{s.getId()}", Paths.get(STR."\{COURSE_DIR}\\\{c.getId()}.txt"));
        }
        CRUD.deleteFile(STR."\{STUDENT_DIR}\\\{s.getId()}.txt");
        CRUD.deleteLine(s.getId().toString(), ALL_USERS.toPath());
    }
}
