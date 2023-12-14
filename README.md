# ToDo List Application

Welcome to the ToDo List Application repository! 🚀 This is my first project in the Java programming language! 🎉 This project is a simple task management application developed using Java technologies. The application utilizes JDBC for database connectivity, JavaFX for the user interface, and PostgreSQL as the backend database.

## Application
<p align="center">
  <img src="https://i.imgur.com/GdIT8G2.png">
</p>

## Features
- **Task Management:** Create, update, and delete tasks effortlessly. ✅
- **User-Friendly Interface:** The JavaFX interface provides a seamless and intuitive user experience. 🌈

## Technologies Used
- **JavaFX:** JavaFX is used for building the graphical user interface, providing a rich and interactive user experience. 🎨
- **JDBC (Java Database Connectivity):** JDBC is employed for connecting the application to the PostgreSQL database, enabling seamless data transactions. 🔄
- **PostgreSQL:** The PostgreSQL database is used to store and manage the tasks and their related information. 🐘

## Getting Started

### Prerequisites
1. JDK (Java Development Kit) installed ☕
2. PostgreSQL database server installed and running 🚀

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/vovgoo/ToDoList.git
    cd todo-list
    ```

2. Compile and run the application:
    ```bash
    javac -cp ".:path/to/javafx-sdk/lib/*:path/to/postgresql-driver.jar" src/main/java/com/example/todo/Main.java
    java -cp ".:path/to/javafx-sdk/lib/*:path/to/postgresql-driver.jar" src/main/java/com/example/todo/Main
    ```

Make sure to replace `path/to/javafx-sdk` and `path/to/postgresql-driver.jar` with the actual paths on your system. 🛠️

### Database Configuration
1. Set up a PostgreSQL database with the following details:
   - Database Name: `javafx_todo`
   - Username: `your_username`
   - Password: `your_password`

2. Update the database connection details in `src/main/java/com/example/controllers/HelloController.java` with your PostgreSQL credentials.

```java
Connection conn = DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USER"), System.getenv("DB_PASSWORD"));
```

# Tags
- **Java☕**
- **JavaFX🎨**
- **JDBC🔄**
- **PostgreSQL🐘**
- **ToDoList📋**
- **TaskManagement✅**
- **TaskTracker🚀**
- **ProjectManagement🌐**
- **Database🛢️**
- **UserInterface🌈**
