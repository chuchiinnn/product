package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SvcCategoryImpl implements SvcCategory {

    @Autowired
    RepoCategory repoCategory;

    @Override
    public List<Category> getCategories() {
        return repoCategory.getCategories();
    }
}