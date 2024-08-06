package ObjectsController.Assignment;

import classes.Assignment;
import classes.Course;
import classes.Student;
import dataBase.CRUD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class AssignUtils {
    // REMEMBER that an assignment is closely related to a course

    //"ass" & "assign" are stand for assignment


    private static final File TEACHER_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Teachers");
    private static final File STUDENT_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");
    private static final File COURSE_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Courses");
    private static final File ALL_OBJECTS = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allObjects.txt");


    // todo: maybe u need to set other properties here

    public static void createAssignForCourse(String title, Course course, String description, String deadline){
        Assignment newAss = new Assignment(course, title);
        newAss.setDescription(description);
        newAss.setDeadline(deadline);

        // updating changes in ALL_OBJECTS
        CRUD.createDataOnFile(newAss.toStringALLOBJECTS(), ALL_OBJECTS.toString());

        // updating changes in COURSE file, adding a new assign for the course
        //todo: add its deadline and score
        CRUD.createDataOnFile(newAss.toStringCourse(), STR."\{COURSE_DIR}\\\{newAss.getCourse().getId()}.txt");
    }
    public static void setScoreAssignmentStudent(String score, Assignment assign, String studentId) {
        Student st = new Student(Long.valueOf(studentId));
        st.setScoreForAssign(score, assign);
    }

}
