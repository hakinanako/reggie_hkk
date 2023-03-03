package org.com.hkk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.hkk.reggie.entity.Employee;
import org.com.hkk.reggie.mapper.EmployeeMapper;
import org.com.hkk.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}

