```
# Student Database Application

This is a Java application that connects to a database using JDBC and provides a graphical user interface (GUI) for performing basic CRUD operations on a student database.

## Prerequisites

- Java Development Kit (JDK) installed
- MySQL database server installed
- MySQL Connector/J library added to the classpath

## Getting Started

1. Clone the repository or download the source code files.

2. Update the database connection details:
   - Open the `StudentDatabaseApp.java` file.
   - Modify the `DB_URL`, `DB_USER`, and `DB_PASSWORD` constants with the appropriate values for your MySQL database.

3. Compile the Java source files:
   ```
   javac StudentDatabaseApp.java
   ```

4. Run the application:
   ```
   java StudentDatabaseApp
   ```

## Usage

- The application GUI provides the following functionality:
  - Add: Enter the name and registration number of a student and click the "Add" button to insert the student into the database.
  - Update: Enter the updated name and registration number of a student and click the "Update" button to modify an existing student's information.
  - Delete: Enter the registration number of a student and click the "Delete" button to remove a student from the database.
  - Display: Click the "Display" button to retrieve and display all the students' information from the database.

- The output of the database operations will be displayed in the text area provided in the GUI.

- To exit the application, simply close the GUI window.
