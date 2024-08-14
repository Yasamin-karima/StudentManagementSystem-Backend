package classes;

import dataBase.SQLConnect;

import java.util.List;
import java.util.ArrayList;

public class Course {
    final private static SQLConnect sql = SQLConnect.getInstance();

    private String name; //مدار الکتریکی
    private String id; //4022EC
    private int unit; //3
    private String examDate;
    private Teacher teacher;
    private List<String> courseTimes = new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();



    public Course(String id) {
        this.id = id;
    }
    public Course(String id, Teacher teacher) {
        this.teacher = teacher;
        this.id = id;
    }


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getUnit() {
        return unit;
    }
    public String getExamDate() {
        return examDate.toString();
    }

    public Course getWholeCourse() {
        //we only have id, we want name, unit, teacher, examDate, times
        // (course_id ,course_name ,teacher_name ,unit ,class_times ,exam_date );

        var course = sql.query("SELECT * FROM courses " +
                STR."WHERE course_id = '\{this.id}';").getFirst().split("@");

        setName(course[1]);
        setTeacher(new Teacher(course[2]));
        setUnit(Integer.parseInt(course[3]));
        setCourseTimes(List.of(course[4].substring(1, course[4].length() - 1).trim().split(",")));
        // setCourseTimes(List.of(s.substring(7, s.length() - 2).trim().split(",")));
        setExamDate(course[5]);

        return this;
    }

    public String toString(){
        return STR."COURSE:\{id}";
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setUnit(int unit) {
        this.unit = unit;
    }
    public void setAssignments(Assignment assignment) {
        assignments.add(assignment);
    }
    public void setStudent(Student student) {
        students.add(student);
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setCourseTimes(List<String> courseTimes) {
        this.courseTimes = courseTimes;
    }
    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }


    public List<String> getAssignmentTitles() {
        return sql.query("SELECT ass_title FROM courses_assignments" +
                STR." WHERE course_id = '\{this.id}';");
    }
    public List<String> getStudentIds() {
        return sql.query("SELECT student_id FROM students_courses" +
                STR." WHERE course_id = '\{this.id}';");
    }
    public String getCourseNameById(){
        var list = sql.query("SELECT course_name FROM courses " +
                STR."WHERE course_id = '\{this.id}';");

        if (list.isEmpty()) return null;
        else return list.getFirst();
    }
    public Integer getCourseUnitById(){
        var list = sql.query("SELECT unit FROM courses " +
                STR."WHERE course_id = '\{this.id}';");

        if (list.isEmpty()) return null;
        else return Integer.valueOf(list.getFirst());
    }
    public String getTeacherName(){
        var list = sql.query("SELECT teacher_name FROM courses " +
                STR."WHERE course_id = '\{this.id}';");

        if (list.isEmpty()) return null;
        else return list.getFirst();
    }


    public void createAssignment(String title, String description, String deadline){
        Assignment newAss = new Assignment(this, title);
        newAss.setDescription(description);
        newAss.setDeadline(deadline);

        // inserting assignment into assignments table.
        sql.insert(STR."INSERT INTO assignments VALUES ('\{this.getId()}', '\{newAss.getTitle()}', '\{newAss.getDescription()}', '\{newAss.getDeadline()}');");

        // inserting assignment into  courses_assignments table.
        sql.insert(STR."INSERT INTO courses_assignments VALUES ('\{this.getId()}', '\{newAss.getTitle()}');");
    }
    public void removeCourse(){
        sql.delete(STR."DELETE FROM courses WHERE course_id = '\{id}';");
        sql.delete(STR."DELETE FROM teachers_courses WHERE course_id = '\{id}';");
        sql.delete(STR."DELETE FROM students_courses WHERE course_id = '\{id}';");

        sql.delete("DELETE FROM assignments_scores " +
                STR."WHERE course_id = '\{this.id}';");
        sql.delete("DELETE FROM courses_assignments " +
                STR."WHERE course_id = '\{this.id}';");
    }
}
