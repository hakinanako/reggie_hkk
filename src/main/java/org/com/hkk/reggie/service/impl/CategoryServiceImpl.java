package org.com.hkk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.com.hkk.reggie.entity.Category;
import org.com.hkk.reggie.mapper.CategoryMapper;
import org.com.hkk.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
