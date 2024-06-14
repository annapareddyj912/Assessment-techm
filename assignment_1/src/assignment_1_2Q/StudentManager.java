package assignment_1_2Q;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe"; // Adjust as per your Oracle DB settings
    private final String jdbcUsername = "yourUsername";
    private final String jdbcPassword = "yourPassword";

    public StudentManager() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 1. Add a new student to the database
    public void addStudent(Student student) {
        String sql = "INSERT INTO Students (id, name, age) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Retrieve all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                students.add(new Student(id, name, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // 3. Get a student by their id
    public Student getStudentById(int id) {
        Student student = null;
        String sql = "SELECT * FROM Students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    student = new Student(id, name, age);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    // 4. Update the details of a student by their id
    public boolean updateStudent(int id, String name, int age) {
        String sql = "UPDATE Students SET name = ?, age = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Remove a student from the database by their id
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM Students WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        // Adding students
        manager.addStudent(new Student(1, "Alice", 20));
        manager.addStudent(new Student(2, "Bob", 22));
        manager.addStudent(new Student(3, "Charlie", 23));

        // Display all students
        System.out.println("All Students: " + manager.getAllStudents());

        // Get a student by id
        System.out.println("Get Student with ID 2: " + manager.getStudentById(2));

        // Update a student's details
        manager.updateStudent(2, "Robert", 23);
        System.out.println("Updated Student with ID 2: " + manager.getStudentById(2));

        // Delete a student by id
        manager.deleteStudent(1);
        System.out.println("All Students after deletion: " + manager.getAllStudents());
    }
}
