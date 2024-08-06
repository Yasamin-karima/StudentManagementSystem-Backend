package Server;

import ObjectsController.Course.CourseUtils;
import ObjectsController.ToDo.TodoUtils;
import UserController.Login;
import UserController.Logout;
import UserController.SignUp;
import classes.Assignment;
import classes.Course;
import classes.Student;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to SERVER!");
        ServerSocket serverSocket = new ServerSocket(50050);
        while (true){
            System.out.println("waiting for client...");
            new ClientHandler(serverSocket.accept()).start();
        }

    }
}

class ClientHandler extends Thread {
    Socket socket;
    BufferedReader brd;
    PrintWriter pwr;

    public ClientHandler(Socket socket) throws IOException {
        System.out.println("great, you're in clientHandler constructor");
        System.out.println(socket.getPort());
        this.socket = socket;
        pwr = new PrintWriter(socket.getOutputStream());
        brd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private String listener() throws IOException {
        String result;
        result = brd.readLine();
        System.out.println(STR."@@@@command is: \{result}");
        return result;
    }

    private void writer(String serverResponse){
        pwr.println(serverResponse);
        pwr.flush();
        try {
            brd.close();
            pwr.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("couldn't close resources");
        }
        System.out.println("write finished");
    }

    @Override
    public void run(){
        super.run();
        String command = null;
        try {
            command = listener();
            System.out.println(STR."cammand recieved:\{command}");
        } catch (IOException e){
            e.printStackTrace();
        }
        if (command == null) {
            System.out.println("no command found");
            return;
        }
        String[] split = command.split("\\*"); //command*id*password*name* : STUDENT &  command*name*password : TEACHER
        String response = "0"; //serverResponse
//        Object objectResponse = null; //serverResponse
        switch (split[0]){
            case "studentSignUp":
                response =
                    SignUp.studentSignUp(Long.valueOf(split[1]), split[2], split[3]);
                break;
            case "studentLogin":
                response =
                        Login.StudentLogin(Long.valueOf(split[1]), split[2]);
                break;
            case "getStudent"://getStudent*402243090
                System.out.println("in the method");
                response =
                        new Gson().toJson(new Student(Long.valueOf(split[1])).getWholeStudent());
                break;
            case "getCourse" ://getCourse*402243090
                var result =
                        new Student(Long.valueOf(split[1])).getCourses();
                response =
                        new Gson().toJson(result);
                break;
            case "createCourse" ://createCourse*402243090*courseId
                CourseUtils.addStudentToCourse(Long.valueOf(split[1]), new Course(split[2]));
                response = "201";
                break;
            case "getTodo" ://getTodo*402243090
                var res =
                        new Student(Long.valueOf(split[1])).getTodos();
                response =
                        new Gson().toJson(res);
                break;
            case "createTodo" ://createTodo*402243090*title
                TodoUtils.createTodo(Long.valueOf(split[1]), split[2]);
                response = "201";
                break;
            case "removeTodo" ://removeTodo*402243090*title
                TodoUtils.removeTodo(Long.valueOf(split[1]), split[2]);
                response = "201";
                break;
            case "setTodoState" ://setTodoState*402243090*title*newState
                if (split[3].equals("true"))  {
                    response = TodoUtils.todoDone(Long.valueOf(split[1]), split[2]);
                } else {
                    response = TodoUtils.todoUndone(Long.valueOf(split[1]), split[2]);
                }
                break;
            case "getDoneAssigns" ://getDoneAssigns*402243090*sortFormat
                List<Assignment> r = new ArrayList<>();
                if (split[2].equals("SJF")) {
                    r = new Student(Long.valueOf(split[1])).getDoneAssigns();
                    r.sort((a, b) -> LocalDate.parse(a.getDeadline()).compareTo(LocalDate.parse(b.getDeadline())));
                } else if (split[2].equals("FIFO")) {
                    r = new Student(Long.valueOf(split[1])).getDoneAssigns();
                }
                response = new Gson().toJson(r);
                break;
            case "getUndoneAssigns" ://getUndoneAssigns*402243090*sortFormat
                List<Assignment> re = new ArrayList<>();
                if (split[2].equals("SJF")) {
                    re = new Student(Long.valueOf(split[1])).getUndoneAssigns();

                    re.sort((a, b) -> LocalDate.parse(a.getDeadline()).compareTo(LocalDate.parse(b.getDeadline())));

                } else if (split[2].equals("FIFO")) {
                    re = new Student(Long.valueOf(split[1])).getUndoneAssigns();

                    var allAssignsTitles = Assignment.getAllTheAssignmentsTitles().stream().map(Assignment::getTitle).toList();
                    re.sort((a, b) -> Integer.compare(allAssignsTitles.indexOf(b.getTitle()), allAssignsTitles.indexOf(a.getTitle())));

                }
                response = new Gson().toJson(re);
                break;
            case "changePassword" :// changePassword*userId*newPass;
                new Student(Long.valueOf(split[1])).changePass(split[2]);
                response = "201";
                break;
            case "deleteAccount" :// changePassword*userId*newPass;
                Logout.logout(new Student(Long.valueOf(split[1])));
                response = "201";
                break;
            default:
                System.out.println("default");
        } 
        System.out.println(response);
        writer(response);
        }
    }
/*
    public static void main(String[] args) throws IOException {
        BufferedInputStream r = new BufferedInputStream(System.in);
        String df = Arrays.toString(r.);
        System.out.println("starting action....");
//        System.out.println(r.readAllBytes().toString());
    }*/

