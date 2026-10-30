package com.product.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class DtoCategoryIn {
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @JsonProperty("category")
    private String category;

    @NotBlank(message = "El tag de la categoría es obligatorio")
    @JsonProperty("tag")
    private String tag;

    @JsonProperty("parent_category_id")
    private Integer parentCategoryId;

    // Genera aquí tus Getters y Setters para estos 3 atributos
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public Integer getParentCategoryId() { return parentCategoryId; }
    public void setParentCategoryId(Integer parentCategoryId) { this.parentCategoryId = parentCategoryId; }
}