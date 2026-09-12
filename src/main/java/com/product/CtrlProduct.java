package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CtrlProduct {

    @GetMapping("/category")
    public List<Category> getCategoriesEndpoint() {
        List<Category> list = new ArrayList<>();

        Category cat1 = new Category();
        cat1.setCategory_id(1);
        cat1.setCategory("Ropa");
        cat1.setTag("RP");
        cat1.setParentCategoryld(null); 
        cat1.setStatus(1);

        Category cat2 = new Category();
        cat2.setCategory_id(2);
        cat2.setCategory("Calzado");
        cat2.setTag("CLZD");
        cat2.setParentCategoryld(1);
        cat2.setStatus(1);

        list.add(cat1);
        list.add(cat2);

        return list;
    }


    
}

