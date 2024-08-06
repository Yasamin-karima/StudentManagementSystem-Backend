package UserController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Login {

    private static final File ALL_USERS = new File( "C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");
    private static final File STUDENT_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");
    public static final File TEACHER_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Teachers");


    // student login method:
    // I wish its behavior doesn't change after movement
    //I copied Login method here, because directory calls are specific
    public static String StudentLogin(Long studentId, String enteredPass){
        int passCounter = 0;
        if (hasSignedUp(String.valueOf(studentId))){
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


    // teacher login method:
    public static String teacherLogin(String teacherName, String enteredPass){
        int passCounter = 0;
        if (hasSignedUp(teacherName)){
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
    private static String studentFindPassword(String nameOrId){
        try (Stream<String> lines = Files.lines(Path.of(STR."\{STUDENT_DIR}\\\{nameOrId}.txt"))){
            return lines.filter(a -> a.startsWith("password:"))
                    .map(b -> b.substring(9))
                    .collect(Collectors.joining());
        }catch (Exception e) {
            System.out.println("ERROR IN <studentFindPassword>");
            e.printStackTrace();
            return null;
        }
    }
    private static String teacherFindPassword(String name){
        try (Stream<String> lines = Files.lines(Path.of(STR."\{TEACHER_DIR}\\\{name}.txt"))){
            return lines.findFirst().get().split("\\?")[2];
        }catch (Exception e) {
            System.out.println("ERROR IN <teacherFindPassword>");
            e.printStackTrace();
            return null;
        }
    }


    // this one checks the user has signed up or not
    private static Boolean hasSignedUp(String nameOrId) {// works for both teacher and student
        try (Stream<String> lines = Files.lines(ALL_USERS.toPath())){
            return lines.anyMatch(a -> a.contains(nameOrId));
        }catch (Exception e) {
            System.out.println("ERROR IN <hasSignedUp>");
            e.printStackTrace();
            return false;
        }
    }
}
