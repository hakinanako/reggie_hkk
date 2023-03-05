package org.com.hkk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.hkk.reggie.common.CustomException;
import org.com.hkk.reggie.entity.Category;
import org.com.hkk.reggie.entity.Dish;
import org.com.hkk.reggie.entity.Setmeal;
import org.com.hkk.reggie.mapper.CategoryMapper;
import org.com.hkk.reggie.service.CategoryService;
import org.com.hkk.reggie.service.DishService;
import org.com.hkk.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(Long id) {
        //根据id删除 同时判断是否套餐有关联的菜品
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,id);

        //关联菜品
        int dishCount = dishService.count(lambdaQueryWrapper);
        if (dishCount>0){
            throw new CustomException("菜品关联");
        }
        //关联菜单
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);

        int count = setmealService.count(setmealQueryWrapper);
        if(count>0){
        //抛出
            throw new CustomException("套餐关联");
        }
        //正常情况
        super.removeById(id);

    }
}
