package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import java.util.List;

public interface SvcCategory {
    List<Category> findAll();
    List<Category> findActive();
    List<Category> findChilds(Integer id);
    void create(DtoCategoryIn in);
    void update(DtoCategoryIn in, Integer id);
    void enable(Integer id);
    void disable(Integer id);
}