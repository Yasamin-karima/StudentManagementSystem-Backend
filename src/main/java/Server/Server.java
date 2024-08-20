package Server;

import ObjectsController.Course.CourseUtils;
import UserController.Login;
import UserController.SignUp;
import classes.*;
import com.google.gson.Gson;
import dataBase.SQLConnect;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    Gson gson = new Gson();

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
                response =
                        gson.toJson(new Student(Long.valueOf(split[1])).getWholeStudent());
                break;
            case "getCourse" ://getCourse*402243090
                var result =
                        new Student(Long.valueOf(split[1])).getCourses();
                response =
                        gson.toJson(result);
                break;
            case "createCourse" ://createCourse*402243090*courseId
                CourseUtils.addStudentToCourse(Long.valueOf(split[1]), new Course(split[2]));
                response = "201";
                break;
            case "getTodo" ://getTodo*402243090
                var res =
                        new Student(Long.valueOf(split[1])).getTodos();
                response =
                        gson.toJson(res);
                break;
            case "createTodo" ://createTodo*402243090*title
                new Student(Long.valueOf(split[1])).createTodo(split[2]);
                response = "201";
                break;
            case "removeTodo" ://removeTodo*402243090*title
                new Student(Long.valueOf(split[1])).removeTodo(split[2]);
                response = "201";
                break;
            case "setTodoState" ://setTodoState*402243090*title*newState
                new Student(Long.valueOf(split[1])).setTodoStatus(split[2], split[3]);
                response = "201";
                break;
            case "getDoneAssigns" ://getDoneAssigns*402243090*sortFormat
                List<Assignment> r = new ArrayList<>();
                if (split[2].equals("SJF")) {
                    r = new Student(Long.valueOf(split[1])).getDoneAssigns();
                    r.sort((a, b) -> LocalDate.parse(a.getDeadline()).compareTo(LocalDate.parse(b.getDeadline())));
                } else if (split[2].equals("FIFO")) {
                    r = new Student(Long.valueOf(split[1])).getDoneAssigns();
                }
                response = gson.toJson(r);
                break;
            case "getUndoneAssigns" ://getUndoneAssigns*402243090*sortFormat
                List<Assignment> re = new ArrayList<>();
                if (split[2].equals("SJF")) {
                    re = new Student(Long.valueOf(split[1])).getUndoneAssigns();

                    re.sort((a, b) -> LocalDate.parse(a.getDeadline()).compareTo(LocalDate.parse(b.getDeadline())));

                } else if (split[2].equals("FIFO")) {
                    re = new Student(Long.valueOf(split[1])).getUndoneAssigns();

                    var allAssignsTitles = SQLConnect.getInstance().query("SELECT ass_title FROM assignments;");
                    re.sort((a, b) -> Integer.compare(allAssignsTitles.indexOf(b.getTitle()), allAssignsTitles.indexOf(a.getTitle())));

                }
                response = gson.toJson(re);
                break;
            case "changePassword" :// changePassword*userId*newPass;
                new Student(Long.valueOf(split[1])).changePass(split[2]);
                response = "201";
                break;
            case "deleteAccount" :// changePassword*userId*newPass;
                new Student(Long.valueOf(split[1])).deleteStudent();
                response = "201";
                break;
            case "getBestAssign" :// getBestAssign*userId
                var ass = new Student(Long.valueOf(split[1])).getBestAssign();
                response = gson.toJson(ass);
                break;
            case "getWorstAssign" :// getWorstAssign*userId
                var assign = new Student(Long.valueOf(split[1])).getWorstAssign();
                response = gson.toJson(assign);
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

