package classes;

public class Todo {
    String title;
    boolean isDone;

    public String getTitle() {
        return title;
    }
    public boolean isDone() {
        return isDone;
    }

    public Todo(String title) {
        this.title = title;
        isDone = false;
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
