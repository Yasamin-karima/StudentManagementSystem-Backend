package UserController;

import classes.Student;
import dataBase.CRUD;

import java.io.File;

public class SignUp {

    private static final File STUDENTS_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");
    private static final File TEACHER_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Teachers");
    private static final File ALL_USERS = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");
    private static final File ALL_USER = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");


    public static String teacherSignUp(String name, String password){
        if (UserExists(name)) {
            System.out.println("User already exist");
            return "409";
        }
        // creating a formatted string of teacher data
        String userData = createTeacherData(name, password);

        // adding teacher to ALL_USERS
        CRUD.createDataOnFile(name, ALL_USERS.toString());

        // creating teacher FILE and writing its data on it
        CRUD.createFile(TEACHER_DIR.toString(), name, userData);
        return "201";
    }
    public static String studentSignUp(Long studentId, String password, String name){
        Student newSt = new Student(name, password, studentId);
//        newSt.setDateOfBirth();
        if (UserExists(newSt.getId().toString())) {
            System.out.println("User already exist");
            return "409";//conflict, user already exists
        }

        // adding student id to ALL_USERS
        CRUD.createDataOnFile(newSt.getId().toString(), ALL_USER.toString());

        // creating student FILE and writing its data on it
        CRUD.createFile(STUDENTS_DIR.toString(), newSt.getId().toString(), newSt.toStringStudent());
        return "201";//created
    }
    private static String createTeacherData(String name, String password){
        return STR."T?\{name}?\{password}?";
    }
    private static boolean UserExists(String userData) {
        return CRUD.read(ALL_USERS.toString()).contains(userData);
    }

}
