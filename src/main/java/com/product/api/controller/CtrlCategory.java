package com.product.api.controller;

import com.product.api.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    SvcCategory svcCategory;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(svcCategory.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> findActive() {
        return ResponseEntity.ok(svcCategory.findActive());
    }

    @GetMapping("/{id}/childs")
    public ResponseEntity<List<Category>> findChilds(@PathVariable Integer id) {
        return ResponseEntity.ok(svcCategory.findChilds(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody DtoCategoryIn in) {
        svcCategory.create(in);
        return ResponseEntity.ok(new ApiResponse("La categoría ha sido registrada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @Valid @RequestBody DtoCategoryIn in) {
        svcCategory.update(in, id);
        return ResponseEntity.ok(new ApiResponse("La categoría ha sido actualizada"));
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<ApiResponse> enable(@PathVariable Integer id) {
        svcCategory.enable(id);
        return ResponseEntity.ok(new ApiResponse("La categoría ha sido activada"));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse> disable(@PathVariable Integer id) {
        svcCategory.disable(id);
        return ResponseEntity.ok(new ApiResponse("La categoría ha sido desactivada"));
    }
}