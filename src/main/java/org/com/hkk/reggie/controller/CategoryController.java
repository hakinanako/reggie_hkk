package org.com.hkk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.com.hkk.reggie.common.R;
import org.com.hkk.reggie.entity.Category;
import org.com.hkk.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        log.info("分类新增");
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize){
        //分页构造器
        Page<Category> categoryPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
        categoryQueryWrapper.orderByAsc(Category::getSort);
        categoryService.page(categoryPage,categoryQueryWrapper);
        return R.success(categoryPage);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
//        判断分类下是否有关联菜品
        log.info("进入删除");
//        categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }
}
