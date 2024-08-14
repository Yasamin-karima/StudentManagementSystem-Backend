package classes;

import dataBase.SQLConnect;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Student  {
    private static final SQLConnect sql = SQLConnect.getInstance();

    private String name;
    private String password;
    private Long id;
    private Date birth;
    private Double average;
    private int totalUnits;


    public Student(String name, String password, Long id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }
    public Student(Long id) {
        this.id = id;
    }


    public void setBirth(Date birth) {
        this.birth = birth;
    }
    public void setAverage() {
        List<Assignment> doneAssigns = this.getDoneAssigns();
        if (doneAssigns.isEmpty()) this.average = 0.0;
        else this.average = doneAssigns.stream()
                .map(a -> a.score)
                .reduce(Double::sum).get() / doneAssigns.size();
    }
    public void setTotalUnits() {
        this.totalUnits = this.getCourses().stream()
                .map(Course::getUnit)
                .reduce(Integer::sum)
                .get();
    }


    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
    public Date getBirth() {
        return birth;
    }



    public void setScoreForAssign(String score, Assignment ass){
        sql.insert("INSERT INTO assignments_scores"
                + STR."VALUES ('\{this.id}', '\{ass.getTitle()}', '\{ass.getCourse().getId()}', '\{score}');");
    }
    public List<Assignment> getDoneAssigns() {

        List<Assignment> result = new ArrayList<>();
        var all = getAllAssigns();
        var done = sql.query(STR."SELECT * FROM assignments_scores WHERE student_id = '\{id}'");

        for (Assignment ass : all){
            done.stream().map(a -> a.split("@"))
                    .filter(b -> ass.getCourse().getId().equals(b[2]) && ass.getTitle().equals(b[1])) //b:402243004@HW-3@4022-Z@18.6
                    .forEach(c -> ass.setScore(Double.parseDouble(c[3])));
            if (ass.score != 0.0) result.add(ass);
        }
        return result;
    }
    public List<Assignment> getUndoneAssigns() {
        List<Assignment> result = new ArrayList<>();

        var all = getAllAssigns();
        var done = getDoneAssigns().stream().map(a -> a.title).toList();

        for (Assignment a : all){
            if (!done.contains(a.title)) result.add(a);
        }
        return result;
    }
    private List<Assignment> getAllAssigns(){ //STR."ASS:\{title}?\{course.getId()}?\{description}";
        var courseIds = sql.query(STR."SELECT course_id FROM students_courses WHERE student_id = '\{id}';");
        List<String> result = new ArrayList<>();
        for (String c : courseIds){
            result.addAll(sql.query(STR."SELECT * FROM assignments WHERE course_id = '\{c}';"));
        }
        return result.stream().map(a -> a.split("@"))
                .map(b -> new Assignment(new Course(b[0]), b[1], b[2], b[3]))
                .collect(Collectors.toList());
    }
    public List<Course> getCourses() {
        List<Course> result = new ArrayList<>();
        var courseIds = sql.query(STR."SELECT course_id FROM students_courses WHERE student_id = '\{id}';");
          for (String s : courseIds){
              result.add(new Course(s).getWholeCourse());
          }
          return result;
    }


    public List<Todo> getTodos() {
        List<Todo> result = new ArrayList<>();
        var todos = sql.query(STR."SELECT todo_title, is_done FROM students_todos WHERE student_id = '\{id}';");
        for (String t : todos){
            var split = t.split("@");
            result.add(new Todo(split[0], split[1]));
        }
        return result;
    }
    public void createTodo(String title){
        if (this.doesTodoExists(title)) {
            System.out.println("todo exists");
        } else {
            sql.insert(STR."INSERT INTO students_todos VALUES ('\{id}', '\{title}', 'false');");
        }
    }
    public void setTodoStatus(String title, String status){
        sql.update("UPDATE students_todos" +
                STR." SET is_done = '\{status}' " +
                STR."WHERE student_id = '\{id}' AND todo_title = '\{title}';");
    }
    public void removeTodo(String title){
        sql.delete(STR."DELETE FROM students_todos WHERE todo_title = '\{title}' AND student_id = '\{this.id}';");
    }
    public boolean doesTodoExists(String title){
        return this.getTodos().stream()
                .map(Todo::getTitle)
                .toList().contains(title);
    }



    public String toString(){
        return STR."student_id : \{id}" +
                STR."name : \{name}" +
                STR."pass : \{password}";
    }
    public Student getWholeStudent() { //we have id, we want : name, average, birth, pass
        var student = sql.query("SELECT name, password FROM students " +
                STR."WHERE student_id = '\{this.id}';");
        this.name = student.get(0);
        this.password = student.get(1);
        this.setAverage();
        this.setTotalUnits();
        /*for (String s : lines){
            if (s.startsWith("birth:")){
                try {
                    this.birth = DateFormat.getDateInstance().parse(s.substring(6));
                } catch (ParseException e) {
                    System.out.println("error in parsing date of birth");
                }
            }  else this.birth = null;
        }*/
        return this;
    }
    public void changePass(String newPass) {
        sql.update("UPDATE students" +
                STR."SET password = '\{newPass}' WHERE student_id = '\{id}';");
    }
    public void deleteStudent(){
        sql.delete(STR."DELETE FROM assignments_scores WHERE student_id = '\{this.id}';");
        sql.delete(STR."DELETE FROM students_courses WHERE student_id = '\{this.id}';");
        sql.delete(STR."DELETE FROM students_todos WHERE student_id = '\{this.id}';");
        sql.delete(STR."DELETE FROM students WHERE student_id = '\{this.id}';");
    }
}
