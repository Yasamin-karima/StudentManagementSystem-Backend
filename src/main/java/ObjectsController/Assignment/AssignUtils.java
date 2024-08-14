/*
package ObjectsController.Assignment;

import classes.Assignment;
import classes.Course;
import classes.Student;
import dataBase.SQLConnect;

public class AssignUtils {
    // REMEMBER that an assignment is closely related to a course
    //"ass" & "assign" are stand for assignment
    //todo second method
    //todo move to ass class?
    private static final SQLConnect sql = SQLConnect.getInstance();

    */
/*public static void createAssignForCourse(String title, Course course, String description, String deadline){
        Assignment newAss = new Assignment(course, title);
        newAss.setDescription(description);
        newAss.setDeadline(deadline);

        // inserting assignment into assignments table.
        sql.insert(STR."INSERT INTO assignments VALUES ('\{course.getId()}', '\{newAss.getTitle()}', '\{newAss.getDescription()}', '\{newAss.getDeadline()}');");

        // inserting assignment into  courses_assignments table.
        sql.insert(STR."INSERT INTO courses_assignments VALUES ('\{course.getId()}', '\{newAss.getTitle()}');");
    }*//*

    */
/*public static void setScoreAssignmentStudent(String score, Assignment assign, String studentId) {
        Student st = new Student(Long.valueOf(studentId));
        st.setScoreForAssign(score, assign);
    }*//*

}
*/
