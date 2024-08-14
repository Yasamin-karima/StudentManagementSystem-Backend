package ObjectsController.Course;

import classes.Course;
import classes.Teacher;
import dataBase.SQLConnect;
import java.util.List;

public class CourseUtils {
    private static final SQLConnect sql = SQLConnect.getInstance();

    public static void OOPCreateCourse(String courseId, Teacher teacher, String courseName, int unit, String examDate, List<String> times) {
        if (!teacherExists(teacher.getName())){
            System.out.println("invalid teacher name");
            return;
        }
        if (courseExists(courseId)){
            System.out.println("Course already exists");
            return;
        }

        sql.insert("INSERT INTO courses VALUES " +
                STR."('\{courseId}', '\{courseName}', '\{teacher.getName()}', '\{unit}', '\{times}', '\{examDate}');");
    }
    public static void addStudentToCourse(Long studentId, Course course) {
        if (!studentExists(studentId.toString())){
            System.out.println("invalid studentID");
            return;
        }
        if (!courseExists(course.getName())){
            System.out.println("invalid courseId");
            return;
        }
        if (studentHasCourse(studentId.toString(), course.getId())) { // if student already has the course
            System.out.println("student already has the course");
            return;
        }

        sql.insert(STR."INSERT INTO students_courses VALUES ('\{studentId}', '\{course.getName()}');");
    }
    private static boolean teacherExists(String name) {
        return sql.query("SELECT name FROM teachers;").contains(name);
    }
    private static boolean studentExists(String id) {
        return sql.query("SELECT student_id FROM students;").contains(id);
    }
    private static boolean courseExists(String id) {
        return sql.query("SELECT course_id FROM courses;").contains(id);
    }
    private static boolean studentHasCourse(String studentId, String courseId){
        var rows = sql.query("SELECT * FROM students_courses;");
        return rows.stream().filter(a -> a.contains(studentId))
                .map(b -> b.split("@")[1])
                .toList()
                .contains(courseId);
    }

}
