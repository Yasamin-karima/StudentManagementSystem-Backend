package classes;

import dataBase.SQLConnect;

import java.util.List;
import java.util.stream.Collectors;


public class Assignment {
    private static final SQLConnect sql = SQLConnect.getInstance();


    Course course;
    String title;
    String description;
    String deadline;
    double score;



    public Assignment(Course course, String title) {
        this.course = course;
        this.title = title;
    }
    public Assignment(String title) {
        this.title = title;
    }
    public Assignment(Course course, String title, String description, String deadline) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }


    public void setScore(double score) {
        this.score = score;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


    public String getDeadline() {
        return deadline;
    }

    public Course getCourse() {
        return course;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Assignment getWholeAssignment(){
        if (this.title == null || this.course == null) return null;

        var data = sql.query("SELECT * FROM assignments " +
                STR."WHERE course_id = '\{this.course.getId()}' AND ass_title = '\{this.title}';")
                .getFirst().split("@");
        this.course = new Course(this.course.getId()).getWholeCourse();
        this.deadline = data[3];
        this.description = data[2];
        var temp = sql.query("SELECT score FROM assignments_scores " +
                STR."WHERE course_id = '\{this.course.getId()}' And ass_title = '\{this.title}';");
        if (temp != null) this.score = Double.parseDouble(temp.getFirst());
        return this;
    }


    public String toString(){
        return STR."ASS:\{title}?\{course.getId()}?\{score}?\{description}";
    }

    public void removeAssignment(){
        if (this.getCourse() == null){
            System.out.println("we don't know the course of the assignment, so we can't delete it");
        } else {
            String[] tables = new String[] {"assignments", "assignments_scores", "courses_assignments"};
            for (String table : tables){
                sql.delete(STR."DELETE FROM \{table} " +
                        STR."WHERE ass_title = '\{this.title}' AND course_id = '\{this.getCourse().getId()}';");
            }
            /*sql.delete("DELETE FROM assignments " +
                    STR."WHERE ass_title = '\{this.title}' AND course_id = '\{this.getCourse().getId()}';");// delete from assignments table
            sql.delete("DELETE FROM assignments_scores " +
                    STR."WHERE ass_title = '\{this.title}' AND course_id = '\{this.getCourse().getId()}';");// delete from scores table
            sql.delete("DELETE FROM courses_assignments " +
                    STR."WHERE ass_title = '\{this.title}' AND course_id = '\{this.getCourse().getId()}';");// delete from courses_assignments table*/
        }
    }

}
