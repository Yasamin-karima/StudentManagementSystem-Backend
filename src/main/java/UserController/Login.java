package UserController;

import dataBase.SQLConnect;
import java.util.stream.Collectors;

public class Login {
    //todo: names and ids must be validated

    // student login method:
    public static String StudentLogin(Long studentId, String enteredPass){
        int passCounter = 0;
        if (hasSignedUpStudent(String.valueOf(studentId))){
            while (!studentCheckPassword(String.valueOf(studentId), enteredPass)){
                passCounter++;
                System.out.println(STR."wrong password\{Integer.toString(passCounter)}");
                if (passCounter == 3){
                    System.out.println("log in again");
                    return "401"; //wrongPassword3Times
                }
            }
            // successfully logged in
            return "200";
        } else { // hasn't signed up
            System.out.println("you don't have an account, sign up");
            return "404"; //no account with that studentId
        }
    }
    public static String teacherLogin(String teacherName, String enteredPass){
        int passCounter = 0;
        if (hasSignedUpTeacher(teacherName)){
            while (!teacherCheckPassword(teacherName, enteredPass)){
                passCounter++;
                System.out.println(STR."wrong password\{Integer.toString(passCounter)}");
                if (passCounter == 3){
                    System.out.println("log in again");
                    return "401"; //wrongPassword3Times
                }
            }
            // successfully logged in
            return "200";
        } else { // hasn't signed up
            System.out.println("you don't have an account, sign up");
            return "404"; //no account with that studentId
        }
    }



    //@ SOME HELPING METHODS @:

    //this one checks if entered password is correct
    private static boolean studentCheckPassword(String nameOrId, String enteredPass){
        String actualPass = studentFindPassword(nameOrId);
        return enteredPass.equals(actualPass);
    }
    private static boolean teacherCheckPassword(String name, String enteredPass){
        String actualPass = teacherFindPassword(name);
        return enteredPass.equals(actualPass);
    }


    //this method finds real password for that id
    private static String studentFindPassword(String id){
        SQLConnect sql = SQLConnect.getInstance();
        var names = sql.query("SELECT student_id, password FROM students;");
        return names.stream()
                .filter(a -> a.contains(id))
                .map(b -> b.split("@")[1])
                .collect(Collectors.joining());
    }
    private static String teacherFindPassword(String name){
        SQLConnect sql = SQLConnect.getInstance();
        var names = sql.query("SELECT name, password FROM teachers;");
        return names.stream()
                .filter(a -> a.contains(name))
                .map(b -> b.split("@")[1])
                .collect(Collectors.joining());
    }


    // these check the user has signed up or not
    private static boolean hasSignedUpTeacher(String name) {// works for both teacher and student
        SQLConnect sql = SQLConnect.getInstance();
        var names = sql.query("SELECT name FROM teachers;");
        return names.contains(name);
    }
    private static boolean hasSignedUpStudent(String id) {// works for both teacher and student
        SQLConnect sql = SQLConnect.getInstance();
        var ids = sql.query("SELECT student_id FROM students;");
        System.out.println(ids);
        return ids.contains(id);
    }
}
