package classes;

import dataBase.SQLConnect;

public class Todo {
    private static final SQLConnect sql = SQLConnect.getInstance();


    String title;
    boolean isDone;

    public String getTitle() {
        return title;
    }
    public boolean isDone() {
        return isDone;
    }

    public Todo(String title, String done) {
        this.title = title;
        this.isDone = done.equals("true");
    }


    @Override
    public String toString() {
        return STR."TODO:\{title}?\{isDone}";
    }
}
