package UserController;

import dataBase.SQLConnect;

public class SignUp {
    private static final SQLConnect sql = SQLConnect.getInstance();
    public static String teacherSignUp(String name, String password){
        if (userExists(name)) {
            System.out.println("User already exist");
            return "409";
        }
        // inserting the teacher to teachers table
        sql.insert(STR."INSERT INTO teachers VALUES ('\{name}', '\{password}');");
        return "201";
    }
    public static String studentSignUp(Long studentId, String password, String name){
//        Student newSt = new Student(name, password, studentId);
//        newSt.setDateOfBirth();
        if (userExists(studentId.toString())) {
            System.out.println("User already exist");
            return "409";//conflict, user already exists
        }
        // inserting the student to students table
        sql.insert(STR."INSERT INTO students VALUES ('\{studentId}', '\{name}', '\{password}');");
        return "201";//created
    }
    private static boolean userExists(String userData) {
        SQLConnect sql = SQLConnect.getInstance();
        var users = sql.query("SELECT name FROM teachers;");
        users.addAll(sql.query("SELECT student_id FROM students;"));
        return users.contains(userData);
    }

}
