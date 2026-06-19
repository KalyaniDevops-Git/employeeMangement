package employee_management_system.service;

import employee_management_system.entity.Employee;
import employee_management_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }


    public Employee save(Employee employee) {
        return repository.save(employee);
    }


    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }


    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));
    }


    public Employee updateEmployee(Long id, Employee employee) {

        Employee existing = getEmployeeById(id);
        existing.setName(employee.getName());
        existing.setSalary(employee.getSalary());

        return repository.save(existing);
    }


    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }


}
