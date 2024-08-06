package ObjectsController.Course;

import classes.Course;
import classes.Teacher;
import dataBase.CRUD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class CourseUtils {
    private static final File TEACHER_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Teachers");
    private static final File STUDENT_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");
    private static final File COURSE_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Courses");
    private static final File ALL_OBJECTS = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allObjects.txt");
    private static final File ALL_USERS = new File( "C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");

    // #CREATE# methods to create a new course.
    // course properties should be set later

    public static void OOPCreateCourse(String courseId, Teacher teacher, String courseName, int unit, String examDate, List<String> times) {
        if (!CRUD.read(ALL_USERS.toString()).contains(teacher.getName())){
            System.out.println("invalid teacher name");
            return;
        }
        if (CRUD.read(ALL_OBJECTS.toString()).contains(courseId)){
            System.out.println("Course already exists");
            return;
        }
        Course newCourse = new Course(courseId, teacher);
        newCourse.setName(courseName);
        newCourse.setUnit(unit);
        newCourse.setExamDate(examDate);
        newCourse.setCourseTimes(times);

        // updating changes in ALL_OBJECTS
        CRUD.createDataOnFile(newCourse.toString(), ALL_OBJECTS.toString());

        // updating changes in COURSE_DIR, creating a new file for new course
        CRUD.createFile(COURSE_DIR.toString(), newCourse.getId(), newCourse.toStringInCourses());

        //updating changes in TEACHER_DIR, adding this course to its teacher
        CRUD.createDataOnFile(newCourse.toString(), STR."\{TEACHER_DIR}\\\{newCourse.getTeacherName()}.txt");
    }

/*
    // #REMOVE# methods to remove a course
    // todo:assignments must be removed
    public static void adminRemoveCourse(String courseId) {

        //updating changes in ALL_OBJECTS
        //reminder : there is only 1 id of each course in ALL_OBJECT
        // todo : \n doesn't work
        CRUD.deletePartOfFile(STR."COURSE:\{courseId}" + "\n", ALL_OBJECTS.toString());

        // removing course from TEACHER's file
        CRUD.deletePartOfFile(STR."COURSE:\{courseId}", STR."\{TEACHER_DIR}\\\{getTeacherOfCourse(courseId)}.txt");

        // updating changes in COURSE_DIR, removing course's file
        CRUD.deleteFile(STR."\{COURSE_DIR}\\\{courseId}.txt");

        // removing course from each STUDENT file
        // this method seems to work correct, but im not sure
        try {
            Files.list(STUDENT_DIR.toPath())
                    .filter(b -> CRUD.read(String.valueOf(b)).contains(STR."COURSE:\{courseId}"))
                    .forEach(c -> CRUD.deletePartOfFile(STR."COURSE:\{courseId}", String.valueOf(c)));
        } catch (IOException e) {
            System.out.println("error in removing course from STUDENT files");
        }
    }
    public static void teacherRemoveCourse(String courseId) {
        adminRemoveCourse(courseId);
    }*/


    // #OTHER OPERATIONS# about a course:
    // todo : all these operations may have exceptions, like 'student/course' doesn't exist. -> HANDLE THEM

    public static void addStudentToCourse(Long studentId, Course course) {
        if (!CRUD.read(ALL_USERS.toString()).contains(studentId.toString())){
            System.out.println("invalid studentID");
            return;
        }
        if (!CRUD.read(ALL_OBJECTS.toString()).contains(course.getId())){
            System.out.println("invalid courseId");
            return;
        }
        if (CRUD.read(STR."\{COURSE_DIR}\\\{course.getId()}.txt").contains(studentId.toString())) { // if student already has the course
            System.out.println("student already has the course");
            return;
        }

        //adding studentID to this course's FILE
        CRUD.createDataOnFile(STR."STUDENT:\{studentId}", STR."\{COURSE_DIR}\\\{course.getId()}.txt");

        //adding courseID to this student's FILE
        CRUD.createDataOnFile(course.toString(), STR."\{STUDENT_DIR}\\\{studentId.toString()}.txt");
        System.out.println("Adding Student To Course completed");
    }

    //warning : the entered code by student should be valid...
    public static void studentTakeCourse(Long studentId, Course course) {
        addStudentToCourse(studentId, course);
    }


    // ==> "removing a student from a course"
    // todo: assignment must be removed too
    public static void adminRemoveStudentFromCourse(Long studentId, String courseId) {
        //removing studentID from this course's FILE
        CRUD.deletePartOfFile(STR."STUDENT:\{studentId}", STR."\{COURSE_DIR}\\\{courseId}.txt");

        //removing courseID from this student's FILE
        CRUD.deletePartOfFile(STR."COURSE:\{courseId}", STR."\{STUDENT_DIR}\\\{studentId.toString()}.txt");
    }
    public static void teacherRemoveStudentFromCourse(Long studentId, String courseId) {
        adminRemoveStudentFromCourse(studentId, courseId);
    }

    //a necessory method
    private static String getTeacherOfCourse(String courseId) {
        try{
            return Files.list(TEACHER_DIR.toPath())
                    .map(a -> CRUD.read(a.toString()))
                    .filter(b -> b.contains(courseId))
                    .findAny()
                    .get()
                    .split("\\?")[1];
        }catch (IOException e){
            System.out.println("error in finding course teacher");
        }
        return null;
    }



    public static void main(String[] args) {
//        teacherCreateCourse("4022EC", "Y.Shekofte");
//        teacherCreateCourse("4022DS", "Ebrahimi");
//        adminAddStudentToCourse(402243090L, "4022EC");
//        studentTakeCourse(4023032L, "4022EC");
        adminRemoveStudentFromCourse(402243090L, "4022AP");
    }
}
