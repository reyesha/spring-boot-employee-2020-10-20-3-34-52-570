package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    //Givens
    EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    Employee employeeRequest = new Employee(1, "junjun", 10, "male", 200);
    List<Employee> expectedEmployees = asList(new Employee(), new Employee());
    EmployeeService employeeService= new EmployeeService(employeeRepository);

    @Test
    void should_get_all_when_get_employees() {
        //GIVEN
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);
        //WHEN
        List<Employee> actual = employeeService.getAll();
        //THEN
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void should_create_employee_when_create_given_employee_request() {
        //GIVEN
        when(employeeRepository.save(employeeRequest)).thenReturn(employeeRequest);
        //WHEN
        Employee actual = employeeService.create(employeeRequest);
        //THEN
        Assertions.assertEquals(1, actual.getId());
    }

    @Test
    void should_delete_employee_when_delete_given_employee_request() {
        //given
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeRequest));
        // when
        employeeService.delete(1);
        //then
        verify(employeeRepository).deleteById(1);

    }

    @Test
    void should_get_updated_employeeId_when_update_given_employee_request() {
        //GIVEN
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeRequest));
        //WHEN
        employeeService.update(1, employeeRequest);
        //THEN
        verify(employeeRepository).save(employeeRequest);
    }

    @Test
    void should_get_employee_with_correct_gender_when_search_given_employee_request() {
        //GIVEN
        when(employeeRepository.findByGender("male")).thenReturn(expectedEmployees);
        //WHEN
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> actual = employeeService.getByGender("male");
        //THEN
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void should_get_correct_page_size_when_given_getPage_employee_request() {
        //GIVEN
        Page<Employee> employeePage = new PageImpl<Employee>(expectedEmployees);
        Pageable pageable = PageRequest.of(1,3);
        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);
        //WHEN
        List<Employee> employeeActual = employeeService.getByPage(1,3);
        //THEN
        Assertions.assertEquals(2, employeeActual.size());
    }

    @Test
    void should_get_employee_with_correct_id_when_search_given_employee_request() {
        //GIVEN
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeRequest));
        //WHEN
        Employee actual = employeeService.getById(1);
        //THEN
        Assertions.assertEquals(1, actual.getId());
    }
}