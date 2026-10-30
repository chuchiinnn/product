package com.product.api.repository;

import com.product.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

    List<Category> findByStatus(Integer status);

    @Query(value = "SELECT * FROM category WHERE parent_category_id = :id", nativeQuery = true)
    List<Category> findByParentCategoryId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = :status WHERE category_id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}