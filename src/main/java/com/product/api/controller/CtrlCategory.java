package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    SvcCategory svcCategory;

    @GetMapping
    public List<Category> getCategories() {
        return svcCategory.getCategories();
    }
}