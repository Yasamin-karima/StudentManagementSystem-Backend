package classes;

import java.util.ArrayList;
import java.util.LinkedList;

public class Term {

    private String name;
    private long id;
    private boolean availability;
    private LinkedList<Course> courses = new LinkedList<>();
    private ArrayList<Student> students = new ArrayList<>();

    public Term(String name, long id) {
        this.name = name;
        this.id = id;
        availability = true;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public LinkedList<Course> getCourses() {
        return courses;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getName() {
        return name;
    }


    /*
    this method adds the course to specified term
    and also specified term will be in list of term for that course
     */
    public void addCourse(Course course) {
        courses.add(course);
    }
    //exception
    public void removeCourse(Course course){
        courses.remove(course);
    }
    public void setStudent(Student student) {
//        try {
            students.add(student);
//        } catch (NullPointerException e) {
            return;
//        }
    }
}
