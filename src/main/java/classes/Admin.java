package classes;

import java.util.LinkedList;

public class Admin {
    LinkedList<Term> terms = new LinkedList<>();
    LinkedList<Teacher> teachers = new LinkedList<>();
    public void addTerm(Term newTerm){
        terms.add(newTerm);
    }
    public void removeTerm(Term term){
        terms.remove(term);
    }
    public void addCourseToTerm(Course newCourse, Term term){
        term.addCourse(newCourse);
    }
    public void removeCourseFromTerm(Course Course, Term term){
        term.addCourse(Course);
    }
    public void addTeacher(String firstName, String lastName, long id){
        teachers.add(new Teacher(firstName)) ;
    }


}
