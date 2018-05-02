package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //以下所有的*都代表变量

    //1.查询名字是*的第一个employee
    //@Query("select e from Employee e where e.name =?1")
    Employee findFirstByName(String name);

    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    //自定义sql查询
    @Query(value = "select * from Employee t where name like %?1% and t.salary > ?2 limit 1", nativeQuery = true)
    //实现方法
    Employee findFirstByNameContainingAndSalaryGreaterThan(String name,Integer salary);

}
