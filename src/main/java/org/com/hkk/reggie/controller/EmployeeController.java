package org.com.hkk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.com.hkk.reggie.common.R;
import org.com.hkk.reggie.entity.Employee;
import org.com.hkk.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest,@RequestBody Employee employee) {

        log.info("进行登陆");
        String password = employee.getPassword();
        String username = employee.getUsername();

//      password进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        查询数据库 lambda条件封装
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername, username);

        Employee result = employeeService.getOne(lambdaQueryWrapper);

        if (result == null) {
            return R.error("账户不存在");
        }
        if (!result.getPassword().equals(password)){
            return R.error("账户密码错误");
        }
        if (result.getStatus() == 0) {
            return R.error("当前账户正在封禁");
        }
            log.info("登陆成功");
            httpServletRequest.getSession().setAttribute("employee",result.getId());
         return R.success(result);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest){
        try {
            //登出 查看employee是否存在，清空session
            httpServletRequest.getSession().removeAttribute("employee");
        }catch (Exception e){
            return R.error("登出失败");
        }
        return R.success("退出成功");
        }

        @PostMapping
        public R<String> save(@RequestBody Employee employee,HttpServletRequest request){
        log.info("开始新增员工 {}",employee.toString());

            Object createUser = request.getSession().getAttribute("employee");
            //设置密码
            String password = DigestUtils.md5DigestAsHex("123456".getBytes());
            employee.setPassword(password);

//            数据设置
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());
            employee.setCreateUser((Long) createUser);
            employee.setUpdateUser((Long) createUser);

            employeeService.save(employee);

            return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name){

//        创建page对象
            Page<Employee> pageInfo = new Page<>(page, pageSize);
//            查询 -> name order by
            LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper
                .like(!(StringUtils.isEmpty(name)), Employee::getName,name)
                .orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }

//    更新方法
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info("更新Employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return R.error("无数据");
        }
        return R.success(employee);
    }
}
