
# Student Management System

**An almost comprehensive backend solution for managing schools and educational centers.**

This Student Management System is a backend and server-side solution designed to streamline school administration for teachers and student data management. It offers functionalities for managing courses, assignments, grades, and more.

**Please note:** This is the backend and server-side component of the project. There's a separate [frontend repository](https://github.com/Yasamin-karima/StudentManagementSystem-Frontend) that provides user interfaces for students. User interface for teachers is the file `cli.java`.

## How to Install and Use

1. **Install Java:** <br/> Make sure you have JDK 21 (or a later version) installed on your PC. You can find download instructions [here](https://www.geeksforgeeks.org/how-to-run-java-program/).
2. **Initialize Database:** <br/> Then you must initial database. database is in sqlite and its schema is available in dataBase directory, So please do the initialization yourself. (I will put a ready file to run, If I had time :D )
3. **Run the Server:** <br/> Start the server by running `Server.java`. This is required for the Android application.
4. **Use the System:**
    * **Teachers:** Run `cli.java` to access a command-line interface where you can manage courses, assignments, grades, and other aspects of student data.
    * **Students (Frontend):** Use the separate frontend application (not included here) to manage courses, submit assignments, and view grades.

## For Developers

This project is open-source and welcomes contributions. Here's some information about the codebase:

* **OOP principles:** I tried to follow Object-Oriented Programming principles with methods organized by class.
* **Database schema:** The `dataBase` directory contains the SQLite database schema, including a visual representation for clarity.
* **Not organized classes:**
    * `CourseUtils`: Handles methods specifically related to courses.
    * `UserController`: Contains methods for user login and signup functionalities.
* **Unit tests:** Unfortunately my application doesn't have tests.

## License

This project is licensed under the MIT License.