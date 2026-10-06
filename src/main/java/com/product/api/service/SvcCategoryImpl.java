package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.exception.ApiException;
import com.product.api.repository.RepoCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SvcCategoryImpl implements SvcCategory {

    @Autowired
    RepoCategory repoCategory;

    @Override
    public List<Category> findAll() { return repoCategory.findAll(); }

    @Override
    public List<Category> findActive() { return repoCategory.findByStatus(1); }

    @Override
    public List<Category> findChilds(Integer id) { return repoCategory.findByParentCategoryId(id); }

    @Override
    public void create(DtoCategoryIn in) {
        validateParentCategory(in.getParentCategoryId(), null);
        Category category = new Category();
        category.setCategory(in.getCategory());
        category.setTag(in.getTag());
        category.setParentCategoryld(in.getParentCategoryId());
        category.setStatus(1);
        try {
            repoCategory.save(category);
        } catch (DataIntegrityViolationException e) {
            handleConstraintViolation(e);
        }
    }

    @Override
    public void update(DtoCategoryIn in, Integer id) {
        Category category = repoCategory.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "La categoría no existe"));
        validateParentCategory(in.getParentCategoryId(), id);
        
        category.setCategory(in.getCategory());
        category.setTag(in.getTag());
        category.setParentCategoryld(in.getParentCategoryId());
        try {
            repoCategory.save(category);
        } catch (DataIntegrityViolationException e) {
            handleConstraintViolation(e);
        }
    }

    @Override
    public void enable(Integer id) {
        if (!repoCategory.existsById(id)) throw new ApiException(HttpStatus.NOT_FOUND, "La categoría no existe");
        repoCategory.updateStatus(id, 1);
    }

    @Override
    public void disable(Integer id) {
        if (!repoCategory.existsById(id)) throw new ApiException(HttpStatus.NOT_FOUND, "La categoría no existe");
        if (!repoCategory.findByParentCategoryId(id).isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No se puede desactivar la categoría porque tiene categorías hijas");
        }
        repoCategory.updateStatus(id, 0);
    }

    private void validateParentCategory(Integer parentId, Integer currentId) {
        if (parentId != null) {
            if (parentId.equals(currentId)) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
            }
            Category parent = repoCategory.findById(parentId)
                    .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no existe"));
            if (parent.getStatus() != 1) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría padre no está activa");
            }
        }
    }

    private void handleConstraintViolation(DataIntegrityViolationException e) {
        String msg = e.getMostSpecificCause().getMessage();
        if (msg.contains("category")) throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
        if (msg.contains("tag")) throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
        throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar en la base de datos");
    }
}