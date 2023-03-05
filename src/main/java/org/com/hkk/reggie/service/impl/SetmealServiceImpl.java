package org.com.hkk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.com.hkk.reggie.entity.Setmeal;
import org.com.hkk.reggie.mapper.SetmealMapper;
import org.com.hkk.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
