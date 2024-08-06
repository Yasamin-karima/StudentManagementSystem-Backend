package classes;

import dataBase.CRUD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;


public class Student  {
    private static final File COURSE_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Courses");
    private static final File STUDENT_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");


    private String name;
    private String password;
    private Long id;
    private Date birth;
    private Double average;
    private int totalUnits;


    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Todo> todos = new ArrayList<>();
//    private Map<Course, Integer> scores = new HashMap<>();
    private Map<Assignment, Integer> scores = new HashMap<>();


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
        /*double result = 0.0;
        int counter = 0;

        var temp = this.getDoneAssigns();
        for (Assignment a : temp){
            result += a.score;
            counter++;
        }
        this.average = result / counter;*/
        List<Assignment> doneAssigns = this.getDoneAssigns();
        if (doneAssigns.isEmpty()) this.average = 0.0;
        else this.average = doneAssigns.stream()
                .map(a -> a.score)
                .reduce((b, c) -> b+c).get() / doneAssigns.size();
        /*this.average = this.getDoneAssigns().stream()
                .map(a -> a.score)
                .reduce((b, c) -> b+c).get() / this.getDoneAssigns().size();*/
    }
    public void setTotalUnits() {
        this.totalUnits = this.getCourses().stream()
                .map(a -> a.getUnit())
                .reduce((a, b) -> a+b)
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
    public Map<Assignment, Integer> getScores() {
        return scores;
    }


    //limit exceptions
    public void setScoreForAssign(String score, Assignment ass){
        CRUD.createDataOnFile(STR."ASS:\{ass.getTitle()}?\{ass.getCourse().getId()}?\{score}", STR."\{STUDENT_DIR}\\\{id}.txt");
    }
    public List<Assignment> getDoneAssigns() {
        List<Assignment> result = new ArrayList<>();

        var temp = this.getAllAssigns();

        Map<String, Double> doneAssignsAndScores = new HashMap<>();

        try {
            Files.lines(Paths.get(STR."\{STUDENT_DIR}\\\{id}.txt"))
                    .filter(a -> a.startsWith("ASS:"))
                    .map(b -> b.substring(4).split("\\?"))
                    .forEach(c -> doneAssignsAndScores.put(c[0], Double.valueOf(c[1])));
        } catch (IOException e) {
            System.out.println("ERROR in getDoneAssigns");
        }

        for (Assignment a : temp){
            if (doneAssignsAndScores.containsKey(a.getTitle())) {
                result.add(new Assignment(a.course, a.title, a.description, a.deadline, doneAssignsAndScores.get(a.title)));
            }
        }
        return result;
    }
    public List<Assignment> getUndoneAssigns() {
        List<Assignment> result = new ArrayList<>();

        var all = this.getAllAssigns();
        var done = this.getDoneAssigns().stream().map(a -> a.title).toList();

        for (Assignment a : all){
            if (!done.contains(a.title)) result.add(a);
        }
        return result;
    }
    private List<Assignment> getAllAssigns(){ //STR."ASS:\{title}?\{course.getId()}?\{description}";
        try {
            List<Assignment> result = new ArrayList<>();
            List<String> coursesIds = this.getCourseIds();
            for (String s : coursesIds){
                Files.readAllLines(Paths.get(STR."\{COURSE_DIR}\\\{s}.txt")).stream()
                        .filter(a -> a.startsWith("ASS:"))
                        .map(b -> b.substring(4).split("\\?"))
                        .map(c -> new Assignment(new Course(c[1]).getWholeCourse(), c[0], c[2], c[3]))
                        .forEach(result::add);
            }
            return result;
        } catch (IOException e){
            System.out.println("ERROR in  getting course ids of the student");
            return null;
        }
    }



    public List<String> getCourseIds() {
        try {
            List<String> result = new ArrayList<>();
            var temp = Files.readAllLines(Paths.get(STR."\{STUDENT_DIR}\\\{id}.txt"));
            for (String t : temp){
                if (t.startsWith("COURSE:")){
                    result.add(t.split(":")[1]);
                }
            }
            return result;
        } catch (IOException e){
            System.out.println("ERROR in  getting course ids of the student");
            return null;
        }
    }
    public List<Course> getCourses() {
          List<Course> result = new ArrayList<>();
          for (String s : this.getCourseIds()){
              result.add(new Course(s).getWholeCourse());
          }
          return result;
    }


    public List<Todo> getTodos() {
        try {
            List<Todo> result = new ArrayList<>();
            var temp = Files.readAllLines(Paths.get(STR."\{STUDENT_DIR}\\\{id}.txt"));
            for (String t : temp){
                if (t.startsWith("TODO:")){
                    String[] split = t.substring(5).split("\\?");
                    result.add(new Todo(split[0], split[1]));
                }
            }
            return result;
        } catch (IOException e){
            System.out.println("ERROR in  getting course ids of the student");
            return null;
        }
    }
    public void createTodo(String title){
        if (this.doesTodoExists(title)) {
            System.out.println("todo exists");
            return;
        }
        Todo newOne = new Todo(title, "false");
        CRUD.createDataOnFile(newOne.toString(), STR."\{STUDENT_DIR}\\\{id}.txt");
        System.out.println("todo created");
    }
    public void setTodoDone(String title){
        CRUD.deleteLine(STR."TODO:\{title}", Path.of(STR."\{STUDENT_DIR}\\\{id}.txt"));
        Todo t = new Todo(title, "true");
        CRUD.createDataOnFile(t.toString(), STR."\{STUDENT_DIR}\\\{id}.txt");
    }
    public void setTodoUndone(String title){
        CRUD.deleteLine(STR."TODO:\{title}", Path.of(STR."\{STUDENT_DIR}\\\{id}.txt"));
        Todo t = new Todo(title, "false");
        CRUD.createDataOnFile(t.toString(), STR."\{STUDENT_DIR}\\\{id}.txt");
    }
    public void removeTodo(String title){
        CRUD.deleteLine(STR."TODO:\{title}", Path.of(STR."\{STUDENT_DIR}\\\{id}.txt"));
    }
    public boolean doesTodoExists(String title){
        return this.getTodos().stream()
                .map(Todo::getTitle)
                .toList().contains(title);
    }





    public String toStringStudent(){
        return STR."id:\{id}\npassword:\{password}\nname:\{name}";
    }
    public String toString(){
        return STR."STUDENT:\{id}";
    }
    public Student getWholeStudent() { //we have id, we want : name, average, birth, pass
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(STR."\{STUDENT_DIR}\\\{this.id}.txt"));
        } catch (IOException e){
            System.out.println("ERROR in reading student file");
        }
        for (String s : lines){
            if (s.startsWith("name:")){
                this.name = s.substring(5);
            }
            if (s.startsWith("password:")){
                this.password = s.substring(9);
            }
            if (s.startsWith("birth:")){
                try {
                    this.birth = DateFormat.getDateInstance().parse(s.substring(6));
                } catch (ParseException e) {
                    System.out.println("error in parsing date of birth");
                }
            }  else this.birth = null;
        }
        this.setAverage();
        this.setTotalUnits();
        return this;
    }
    public void changePass(String newPass) {
        try {
            Files.lines(Paths.get(STR."\{STUDENT_DIR}\\\{id}.txt"))
                    .filter(a -> a.startsWith("password:"))
                    .forEach(b -> CRUD.update(b.substring(9), newPass, STR."\{STUDENT_DIR}\\\{id}.txt"));
        } catch (IOException e) {
            System.out.println("ERROR in changing password");
        }
    }
/*
    public void deleteStudent() {
        try {
            Files.delete(Paths.get(STR."\{STUDENT_DIR}\\\{this.id}.txt"));
        } catch (IOException e) {
            System.out.println("couldn't delete student file");
        }
    }*/
}
