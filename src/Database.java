import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Database extends JFrame {
    private JTextField regNumberField, nameField;
    private JButton addButton, updateButton, deleteButton, displayButton;
    private JTextArea outputArea;

    private Connection connection;

    public Database() {
        setTitle("Student Database App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel regNumberLabel = new JLabel("Registration Number:");
        inputPanel.add(regNumberLabel);
        regNumberField = new JTextField();
        inputPanel.add(regNumberField);
        JLabel nameLabel = new JLabel("Name:");
        inputPanel.add(nameLabel);
        nameField = new JTextField();
        inputPanel.add(nameField);

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        inputPanel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });
        inputPanel.add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });
        inputPanel.add(deleteButton);

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayButton = new JButton("Display Students");
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayStudents();
            }
        });
        displayPanel.add(displayButton, BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            // Update the connection URL, username, and password as per your database configuration
            String url = "jdbc:mysql://localhost:3306/studentreg";
            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(url, username, password);
            DatabaseMetaData metadata = connection.getMetaData();
            String databaseProductName = metadata.getDatabaseProductName();
            String databaseProductVersion = metadata.getDatabaseProductVersion();
            String driverName = metadata.getDriverName();
            String driverVersion = metadata.getDriverVersion();

            outputArea.append("Connected to: " + databaseProductName + " " + databaseProductVersion + "\n");
            outputArea.append("Driver: " + driverName + " " + driverVersion + "\n");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addStudent() {
        try {
            String regNumber = regNumberField.getText();
            String name = nameField.getText();

            String sql = "INSERT INTO students (reg_number, name) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, regNumber);
            statement.setString(2, name);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Student added: " + name + " (" + regNumber + ")\n");
            } else {
                outputArea.append("Failed to add student\n");
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateStudent() {
        try {
            String regNumber = regNumberField.getText();
            String name = nameField.getText();

            String sql = "UPDATE students SET name = ? WHERE reg_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, regNumber);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Student updated: " + name + " (" + regNumber + ")\n");
            } else {
                outputArea.append("Failed to update student\n");
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteStudent() {
        try {
            String regNumber = regNumberField.getText();

            String sql = "DELETE FROM students WHERE reg_number = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, regNumber);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                outputArea.append("Student deleted: " + regNumber + "\n");
            } else {
                outputArea.append("Failed to delete student\n");
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayStudents() {
        try {
            outputArea.setText("");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                String regNumber = resultSet.getString("reg_number");
                String name = resultSet.getString("name");

                outputArea.append("Student: " + name + " (" + regNumber + ")\n");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Database();
            }
        });
    }
}
