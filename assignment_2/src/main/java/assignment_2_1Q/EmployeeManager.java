package assignment_2_1Q;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManager {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Add a new employee to the database
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (name, department) VALUES (?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartment());
    }

    // 2. Retrieve all employees
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employees";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    // 3. Get an employee by their id
    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM Employees WHERE id = ?";
        List<Employee> employees = jdbcTemplate.query(sql, new Object[]{id}, new EmployeeRowMapper());
        return employees.isEmpty() ? null : employees.get(0);
    }

    // 4. Update the details of the employee
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employees SET name = ?, department = ? WHERE id = ?";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartment(), employee.getId());
    }

    // 5. Remove an employee from the database by their id
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM Employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // RowMapper implementation to map ResultSet to Employee object
    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setDepartment(rs.getString("department"));
            return employee;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Load Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get EmployeeManager bean
        EmployeeManager manager = context.getBean("employeeManager", EmployeeManager.class);

        // Add employees
        manager.addEmployee(new Employee(0, "Alice", "HR"));
        manager.addEmployee(new Employee(0, "Bob", "IT"));
        manager.addEmployee(new Employee(0, "Charlie", "Finance"));

        // Display all employees
        System.out.println("All Employees: " + manager.getAllEmployees());

        // Get an employee by id
        System.out.println("Get Employee with ID 2: " + manager.getEmployeeById(2));

        // Update an employee's details
        Employee updatedEmployee = new Employee(2, "Robert", "IT");
        manager.updateEmployee(updatedEmployee);
        System.out.println("Updated Employee with ID 2: " + manager.getEmployeeById(2));

        // Delete an employee by id
        manager.deleteEmployee(1);
        System.out.println("All Employees after deletion: " + manager.getAllEmployees());
    }
}
