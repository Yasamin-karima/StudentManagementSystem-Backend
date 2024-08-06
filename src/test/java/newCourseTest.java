import classes.Course;
import classes.Student;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;



public class newCourseTest {

    Course course1;
    Student student1, student2, student3, student4, student5;
    Student[] students;

    @BeforeEach
    void setUp() {
        course1 = new Course("course1", 111, 3);
        student1 = new Student("student1", 1000);
        student2 = new Student("student2", 2000);
        student3 = new Student("student3", 3000);
        student4 = new Student("student4", 4000);
        student5 = new Student("student5", 5000);
        Student[] students = new Student[]{student1, student2, student3, student4, student5};
    }
    @Test
    void noOfThingsTest(){
        course1.setStudents(students);
        assertEquals(5, course1.getNoOfStudents());
//        assertEquals(1, course1);
//        assertEquals(1, noOfTerms);
    }
    @Test
    void printStudents() {
        course1.printStudents();
        // first question, how to check printed format??
    }
}
