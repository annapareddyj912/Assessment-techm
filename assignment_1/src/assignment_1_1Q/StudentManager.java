package assignment_1_1Q;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students = new ArrayList<>();

    // 1. Add a new student to the collection
    public void addStudent(Student student) {
        students.add(student);
    }

    // 2. Get a list of all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent modification
    }

    // 3. Get a student by their id
    public Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    // 4. Update the name and age of the student with the specified id
    public boolean updateStudent(int id, String name, int age) {
        Student student = getStudentById(id);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
            return true;
        }
        return false;
    }

    // 5. Remove the student with the specified id from the collection
    public boolean deleteStudent(int id) {
        Student student = getStudentById(id);
        if (student != null) {
            students.remove(student);
            return true;
        }
        return false;
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
