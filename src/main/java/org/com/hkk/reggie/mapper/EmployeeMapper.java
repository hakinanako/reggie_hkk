package org.com.hkk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.com.hkk.reggie.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
