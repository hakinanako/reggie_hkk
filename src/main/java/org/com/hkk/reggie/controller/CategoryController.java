package org.com.hkk.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.com.hkk.reggie.common.R;
import org.com.hkk.reggie.entity.Category;
import org.com.hkk.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
