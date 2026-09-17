package com.product.api.repository;

import com.product.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {


    @Query("SELECT c FROM Category c")
    List<Category> getCategories();
}