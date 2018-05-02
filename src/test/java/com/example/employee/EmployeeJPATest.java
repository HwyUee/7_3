package com.example.employee;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class EmployeeJPATest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        //本地启动mysql，创建employee_db数据库
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://localhost:3306/employee_db","root","123");
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void should_return_employee_when_input_employee_name() throws Exception {
        //1.查询名字是小红的employee
        //Employee expectedEmployee = new Employee("xiaohong",19,"female",7000,1, 1);

        String actualName = "xiaohong";
        Employee expectedEmployee = employeeRepository.findFirstByName(actualName);
        assertThat(actualName).isEqualTo(expectedEmployee.getName());
    }
    @Test
    public void should_return_employee_given_character_in_name_and_salary_large_than() throws Exception {
        //2.找出Employee表中第一个姓名包含`n`字符的雇员所有个人信息
        Employee expectedEmployee=employeeRepository.findFirstByNameContainingAndSalaryGreaterThan("n",5000);
        String actualName ="xiaoming";
        assertThat(actualName).isEqualTo(expectedEmployee.getName());
    }
    @Test
    public void should_return_employee_name_when_employee_salary_is_max_and_given_company_id_() throws Exception {
        //3.找出一个薪资最高且公司ID是1的雇员以及该雇员的name
        Employee expectedEmployee = employeeRepository.findhighestsalaryemployeeBycompanyId(1);
        String actualName = "xiaohong";
        assertThat(actualName).isEqualTo(expectedEmployee.getName());
    }
    @Test
    public void should_return_employee_list_when_input_page_request() throws Exception {
        //4.实现对Employee的分页查询，每页两条数据，一共三页数。
        //注意：PageRequest的构造方法已经弃用了代替的是PageRequest.of,并且最后一个参数代表按照table中的哪一个字段排序
        Page<Employee> EmployeePage = employeeRepository.findAll(PageRequest.of(3,2));
        assertThat(EmployeePage.getTotalPages()).isEqualTo(3);
    }



}
