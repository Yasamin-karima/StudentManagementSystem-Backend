import classes.Course;
import classes.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TermTest {

    Term term1; Term term2; Term term3;
    Term emptyTerm;

    Course course1; Course course2; Course course3;
    Course course4; Course course5; Course course6;
    Course course7; Course course8; Course course9;
    Course nullCourse;


    @BeforeEach
    void setUp() {
        course1 = new Course("course1", 1000L, 1);
        course2 = new Course("course2", 2000L, 1);
        course3 = new Course("course3", 3000L, 1);
        course4 = new Course("course4", 4000L, 1);
        course5 = new Course("course5", 5000L, 1);
        course6 = new Course("course6", 6000L, 1);
        course7 = new Course("course7", 7000L, 1);
        course8 = new Course("course8", 8000L, 1);
        course9 = new Course("course9", 9000L, 1);

        term1 = new Term("term1", 4021L);
        term2 = new Term("term2", 4022L);
        term3 = new Term("term3", 3000L);
        emptyTerm = new Term("empty", 4000L);

    }

    @Test
    void addCourseTest() {
        term1.addCourse(course1);
        term1.addCourse(course2);
        term1.addCourse(course3);
        term1.addCourse(course4);
        term1.addCourse(course5);
//        term3.addCourse(nullCourse);
    // todo: null pass
    //todo : array out of bound
        assertEquals(5, term1.getCourses().size());
        assertEquals("course3", term1.getCourses().get(2).getName());
        assertEquals(0, emptyTerm.getCourses().size());


    }
}