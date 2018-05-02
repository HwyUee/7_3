package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名
    @Query(value = "select * from Employee t inner join Company c on t.companyId=c.id where t.companyId=?1 order by t.salary desc limit 1 ", nativeQuery = true)
    Employee findhighestsalaryemployeeBycompanyId(int companyId);
    //4.实现对Employee的分页查询，每页两个数据
    Page<Employee> findAll(Pageable pageable);
    //5.查找**的所在的公司的公司名称
    @Query(value = "select c.CompanyName from Employee t inner join Company c on t.companyId=c.id where t.name=?1",nativeQuery = true)
    String findCompanyNameByEmployeeName(String name);

}
