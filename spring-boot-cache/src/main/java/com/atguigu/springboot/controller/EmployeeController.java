package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description：
 * @author： wangkang
 * @date： 2019/7/13 21:54
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){
        Employee emp = employeeService.getEmp(id);
        return emp;
    }

    @GetMapping("emp")
    public Employee updateEmployee(Employee employee){
        Employee emp = employeeService.updateEmp(employee);
        return emp;
    }

    @GetMapping("delEmp")
    public String deleteEmployee(Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("emp/lastname/{lastName}")
    public Employee getEmployeeByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }
}
