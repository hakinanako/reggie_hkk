package org.com.hkk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.hkk.reggie.entity.Dish;
import org.com.hkk.reggie.mapper.DishMapper;
import org.com.hkk.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {
}
