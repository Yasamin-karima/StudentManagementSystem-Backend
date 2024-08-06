import ObjectsController.Assignment.AssignUtils;
import ObjectsController.Course.CourseUtils;
import UserController.Login;
import UserController.SignUp;
import classes.Assignment;
import classes.Course;
import classes.Teacher;
import com.github.eloyzone.jalalicalendar.DateConverter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class cli {

    private static Teacher teacher;
    private static Course checkingCourse;
    private static Integer State;
    static Scanner scan = new Scanner(System.in);


    public static void main(String[] args) {
        State = 0;

        loop: while (State != 27) { // 27 : exit
            switch (State){
                case 0:
                    ChooseRulePage(); break;
                case 1:
                    showAssAndStudents(); break;
                case 2:
                    teacherWelcome(); break;
                case 3:
                    removeCourseAccept(); break;
                case 4:
                    addAssignment(); break;
                case 5:
                    addStudent(); break;
                case 6:
                    teacherLogin(); break;
                case 7:
                    teacherSignup(); break;
                case 8:
                    profileInfoPage(); break;
                case 9:
                    showCoursePage(); break;
                case 10:
                    getCommand(); break;
                case 27:
                    break loop;
            }
        }
    }

    private static void removeCourseAccept() {
        System.out.println(STR."Are you sure you want to permanently remove \{checkingCourse}?");
        System.out.println("(type 'yes' to delete or 'no' to keep)");
        String answer = scan.next(); scan.nextLine();
        if (answer.equals("yes")){
            checkingCourse.removeCourse();
            State = 8;
        } else {
            State = 8;
        }
    }

    private static void getCommand() {
        System.out.println("Type one of commands with the given format:");
        System.out.println("->SETSCORE:score:assignmentTitle:studentId");
        System.out.println("->REMOVEASSIGNMENT:assignmentTitle");
        String command = scan.next(); scan.nextLine();
        var split = command.split(":");
        switch (split[0]){
            case "SETSCORE":
                AssignUtils.setScoreAssignmentStudent(split[1], new Assignment(checkingCourse, split[2]), split[3]);
                break;
            case "REMOVEASSIGNMENT":
                new Assignment(checkingCourse, split[1]).removeAssignment();
                break;
        }
        State = 10;
    }



    private static void showAssAndStudents() {
        var Ass = checkingCourse.getAssignmentTitles();
        var students = checkingCourse.getStudentIds();
        System.out.println("======ASSIGNMENTS=======");
        for (String title : Ass){
            System.out.println(title);
        }
        System.out.println("======STUDENTS=======");
        for (String id : students){
            System.out.println(id);
        }
        State = 10;
    }

    private static void addStudent() {
        System.out.println("type the ID the student:");
        String studentId = scan.next(); scan.nextLine();
        CourseUtils.addStudentToCourse(Long.valueOf(studentId), checkingCourse);
        State = 9;
    }

    private static void addAssignment() {
        System.out.println("type the title of assignment:");
        String title = scan.next(); scan.nextLine();

        System.out.println("type description of assignment in a line");
        System.out.println("and use shift+space instead of space :)");
        String description = readPersian();

        System.out.println("enter deadline with given format:");
        System.out.println("year-month-day");
        String deadline = scan.next(); scan.nextLine();

        var split = deadline.split("-");
        DateConverter converter = new DateConverter();
        LocalDate date = converter.jalaliToGregorian(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));

        AssignUtils.createAssignForCourse(title, checkingCourse, description, String.valueOf(date));
        State = 9;
    }

    private static void profileInfoPage() {
        clearScreen();
        System.out.println("These are your courses : ");
        System.out.println("-- To add another course, choose '0' ");
        System.out.println("-- To see your courses, type another digit ");
        setCursorPosition(5, 0);
        int answer = scan.nextInt();
        if (answer == 0) createCourse();
        showExistingCourses();
        System.out.println("choose any course with THIS format:");
        System.out.println("     ->courseId<-");
        String chosenCourseId = scan.next(); scan.nextLine();
        checkingCourse = new Course(chosenCourseId).getWholeCourse();
        //GetterCLI.getCourseById(chosenCourseId, teacher);
        State = 9;
    }

    private static void showCoursePage() {
        clearScreen();
        System.out.println("choose one:");
        System.out.println("1- add assignment");
        System.out.println("2- add student");
        System.out.println("3- see students and assignments");
        System.out.println("4- permanently remove course");
        int answer = scan.nextInt();
        switch (answer){
            case 1: State = 4; break;
            case 2: State = 5; break;
            case 3: State = 1; break;
            case 4: State = 3; break;
        }
    }

    private static void showExistingCourses() {
        Objects.requireNonNull(teacher.getTeacherCourses())
                .forEach(a -> System.out.println(STR."   - \{a.getId()}"));
    }

    private static void createCourse(){
        clearScreen();
        System.out.println("enter new COURSE ID : ");
        String courseId = scan.next();

        System.out.println("enter new COURSE name : ");
        String name = readPersian();

        System.out.println("enter new COURSE unit : ");
        int unit = scan.nextInt();

        System.out.println("enter new COURSE exam date : (year-month-day)");
        String examDate = scan.next(); scan.nextLine();
        var split = examDate.split("-");
        DateConverter converter = new DateConverter();
        LocalDate date = converter.jalaliToGregorian(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));

        System.out.println("enter days and times of your course, and then type 'DONE'");
        System.out.println("day-start-end");
        List <String> times = new ArrayList<>();
        String next = scan.next();
        while (!next.equals("DONE")){
            times.add(next);
            System.out.println("you typed:"+ next);
            next = scan.next(); scan.nextLine();
        }

        CourseUtils.OOPCreateCourse(courseId, teacher, name, unit, String.valueOf(date), times);
        System.out.println("COURSE CREATED !!");
    }
    private static void teacherSignup() {
        clearScreen();

        System.out.println("enter your NAME : ");
        String name = scan.next(); scan.nextLine();
        System.out.println("name: " + name);

        System.out.println("enter your PASSWORD : ");
        String pass = scan.next(); scan.nextLine();
        System.out.println("pass: " + pass);

        String res = SignUp.teacherSignUp(name, pass);
        if (res == "201") {
            System.out.println("201");
            teacher = new Teacher(name);
            State = 8;//page profile
        } else {
            System.out.println("409");
            State = 6; //login
        }
    }

    private static void teacherLogin() {
        clearScreen();

        System.out.println("Enter your NAME : ");
        String name = scan.next(); scan.nextLine();

        System.out.println("Enter your PASSWORD : ");
        String pass = scan.next(); scan.nextLine();

        String res = Login.teacherLogin(name, pass);
        // todo : handle "401" response
        if (res.equals("200")) {
            System.out.println("200");
            teacher = new Teacher(name);
            State = 8;
        } else if (res.equals("404")){
            System.out.println("404");
            State = 2; //teacher doesn't have account and must sign up
        }  else if (res.equals("401")) System.out.println("result is 401, incorrect password");
    }

    //check password in backend :|||
    private static void adminLogin() {
        clearScreen();
        setCursorPosition(2, 20);
        System.out.println("hello dear ADMIN :)");
        setCursorPosition(3, 20);
        System.out.println("enter admin's password :");
    }
    private static void teacherWelcome() {
        clearScreen();
        setCursorPosition(2, 20);
        System.out.println("hello dear TEACHER :)");
        setCursorPosition(3, 20);
        System.out.println("do you have account? : ");
        setCursorPosition(5, 20);
        System.out.println("[ Y/N : Y to login, N to signUp]");

        setCursorPosition(3, 43);
        String answer = scan.next();
        switch (answer){
            case "Y": case "y":
                State = 6; break;
            case "N": case "n":
                State = 7; break;
        }
    }
    private static void ChooseRulePage() {
        clearScreen();

        System.out.println("hello");
        System.out.println("welcome to our app");
        System.out.println();
        System.out.println("who are you? : ");
        System.out.println();
        System.out.println("2- TEACHER");
        System.out.println("3- ADMIN");

        setCursorPosition(3, 16);

        int answer = scan.nextInt();
        switch (answer){
//            case 1: State = 1;break;
            case 2: State = 2; break;
            case 3: State = 3; break;
        }
    }
    private static void setCursorPosition(int row, int col) {
        // ANSI escape code to set cursor position
        String escapeCode = STR."\u001B[\{row + 1};\{col + 1}H";
        // Print the escape code to move the cursor
        System.out.print(escapeCode);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static String readPersian(){
        byte[] bytes;
        bytes = scan.next().getBytes(StandardCharsets.UTF_8);
        scan.nextLine();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
