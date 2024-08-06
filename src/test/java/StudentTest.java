import Exceptions.CourseIsNotAvailableInRegisteredTerms;
import classes.Course;
import classes.Student;
import classes.Teacher;
import classes.Term;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StudentTest {

    Student student1;Student student2;Student student3;
    Student student4;Student student5;Student student6;
    Student nullStudent;
    Term term1;Term term2;Term term3;
    Term nullTerm; Term emptyTerm;

    Course course1; Course course2; Course course3;
    Course course4; Course course5; Course course6;
    Course course7; Course course8; Course course9;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        student1 = new Student("student1", 402243001);
        student2 = new Student("student2", 402243002);
        student3 = new Student("student3", 402243003);
        student4 = new Student("student4", 402243004);
        student5 = new Student("student5", 402243005);
        student6 = new Student("student6", 402243006);

        term1 = new Term("term1", 4021L);
        term2 = new Term("term2", 4022L);
        term3 = new Term("term3", 3000L);
        emptyTerm = new Term("emptyTerm", 4000L);

        course1 = new Course("course1", 1000L, 1);
        course2 = new Course("course2", 2000L, 1);
        course3 = new Course("course3", 3000L, 1);
        course4 = new Course("course4", 4000L, 1);
        course5 = new Course("course5", 5000L, 1);
        course6 = new Course("course6", 6000L, 1);
        course7 = new Course("course7", 7000L, 1);
        course8 = new Course("course8", 8000L, 1);
        course9 = new Course("course9", 9000L, 1);

    }

    @Test
    void addTermTest() throws Exception {
        //adding terms to each
        student1.addTerm(term1);
        student2.addTerm(term1);
        student3.addTerm(term1);
        student4.addTerm(term2);
        student5.addTerm(term2);
        student6.addTerm(term3);
        // todo : what happens if a null student adds to a term 11

        //assertThrows(ArrayIndexOutOfBoundsException.class, (Executable) term1.getStudents().get(3)); todo:

        //todo : what about when passing nullTerm to a student?222

        //checking size of student lists in terms:
        assertEquals(3, term1.getStudents().size());
        assertEquals(2, term2.getStudents().size());
        assertEquals(1, term3.getStudents().size());
        assertEquals(0, emptyTerm.getStudents().size());

        //check null term :
        assertNull(nullTerm);

        //check assigned properly? :
        assertEquals("student3", term1.getStudents().get(2).getName());
        assertEquals("student6", term3.getStudents().getFirst().getName());

        //check added to student properly
        assertEquals("term1", student1.getTerms().getFirst().getName());
    }
    @Test
    void addMultipleTerms() throws Exception {
        student1.addTerm(term1);
        student1.addTerm(term2);
        student1.addTerm(term3);

        //check size of terms list in student
        assertEquals(3, student1.getTerms().size());

        //check assigned properly
        assertNotNull(student1.getTerms().getFirst());
        assertNotNull(student1.getTerms().get(1));
        assertNotNull(student1.getTerms().get(2));

        assertEquals("term3", student1.getTerms().getLast().getName());

        assertEquals(1, term2.getStudents().size());
    }


    @Test
    void addCoursesTest () throws Exception {
        term1.addCourse(course1);
        term1.addCourse(course2);
        term1.addCourse(course3);
        term2.addCourse(course9);
        term3.addCourse(course4);

        student1.addTerm(term1);
        student1.addTerm(term3);

        assertThrows(CourseIsNotAvailableInRegisteredTerms.class, () -> {
            student1.addCourse(course5);
        });
        assertThrows(CourseIsNotAvailableInRegisteredTerms.class, () -> {
            student1.addCourse(course9);
        });
        assertThrows(CourseIsNotAvailableInRegisteredTerms.class, () -> {
            student2.addCourse(course1);
        });
        assertThrows(CourseIsNotAvailableInRegisteredTerms.class, () -> {
            student2.addCourse(course7);
        });

        assertDoesNotThrow( () -> {
            student1.addCourse(course4);
            student1.addCourse(course1);
        });

    }

    // FOR COURSE TEST!!!!!
    @Test
    void removeStudentTest() throws Exception {
        term1.addCourse(course1);
        student1.addTerm(term1);
        student2.addTerm(term1);
        student3.addTerm(term1);
        student4.addTerm(term1);
        student1.addCourse(course1);
        student2.addCourse(course1);
        student3.addCourse(course1);
        student4.addCourse(course1);

        assertEquals(4, course1.getStudents().size());
        assertEquals(4, term1.getStudents().size());
        course1.removeStudent(student1);
        assertEquals(3, course1.getStudents().size());
        assertEquals(student2, course1.getStudents().getFirst());
    }

    //FOR TEACHER TEST!!!!!
   /* @Test
    void removeStudentFromTermByTecher() throws Exception {
        Teacher teacher = new Teacher();
        student1.addTerm(term1);
        student1.addTerm(term2);
        teacher.addStudentToTerm(student1, term3);
        teacher.removeStudentFromTerm(student1, term1);
        assertEquals(2, student1.getTerms().size());
    }*/

    //FOR COURSE TEST!!!???
//    void checkMapWorksProperly()
    /*
    @Disabled
    @Test
    void addNullSituations () throws Exception {
        student1.addTerm(term1);
        student2.addTerm(term1);
        student3.addTerm(term1);
        student4.addTerm(term2);
        student5.addTerm(term2);
        student6.addTerm(term3);

        assertThrows(NullAssignment.class, () -> {
            student1.addTerm(nullTerm);
        });
        // todo : nullStudent.addTerm(nullTerm);
    }*/

}