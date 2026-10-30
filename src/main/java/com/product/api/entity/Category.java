package com.product.api.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")

public class Category {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@JsonProperty("category_id")
@Column(name = "category_id")
private Integer category_id;

@JsonProperty("category")
@Column(name = "category")
private String category;

@JsonProperty("tag")
@Column(name = "tag")
private String tag;

@JsonProperty("parent_category_id")
@Column(name = "parent_category_id")
private Integer parentCategoryId;

@JsonProperty("status")
@Column(name = "status")
private Integer status;


    public Category(){
    }

    //Getters y Setters
    public Integer getCategory_id(){
        return this.category_id;
    }

    public void setCategory_id(Integer category_id){
        this.category_id = category_id;
    }

    public String getCategory(){
        return this.category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getParentCategoryId() {
        return this.parentCategoryId;
    }

    public void setParentCategoryld(Integer parentCategoryld) {
        this.parentCategoryId = parentCategoryld;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    //Creamos el formato para imprimir en consola
    @Override
    public String toString(){
        return "{" + category_id + ", " + category + ", " + tag + ", " + parentCategoryId + ", " + status + "} ";
    }
}


    


