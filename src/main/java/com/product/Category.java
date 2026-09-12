package com.product;
import java.util.ArrayList;
import java.util.List;

public class Category {
    //Atributos
    private Integer category_id;
    private String category;
    private String tag;
    private Integer parentCategoryId;
    private Integer status;

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



    //Metódos
    private static List<Category> listCategories = new ArrayList<>();

    public static void getCategories(){
        //Revisamos si la lista es vacía
        if (listCategories.isEmpty()) {
            System.out.println("No existen categorías registradas");
            return;
        }
        //Guardamos las categorias activas
        List<String> categoriasActivas = new ArrayList<>();

    // Recorrer la lista y filtrar
    for (Category cat : listCategories) {
        if (cat.getStatus() != null && cat.getStatus() == 1) {
            categoriasActivas.add(cat.toString());
        }
    }

    // Lo mandamos a imprimir con el formato de corchetes
    System.out.println("[" + String.join(", ", categoriasActivas) + "]");
}



 public static void getChildCategories(Integer category_id){
        //Guardamos los textos de las categorías hijas
        List<String> categoriasHijas = new ArrayList<>();
        //recorremos nuestra lista principal que tienw las categorias
        for (Category cat : listCategories) {
            Integer idPadreActual = cat.getParentCategoryId();
            //Verificamos que el id no sea nullo e igual al que buscamos
            if (idPadreActual != null && idPadreActual.equals(category_id)) {
                categoriasHijas.add(cat.toString());
            }
        }
        //Imprimimos el resultado final 
        System.out.println("[" + String.join(", ", categoriasHijas) + "]");
    }

    public static void createCategory(Category nuevaCategoria){
        
        for (Category cat : listCategories) {
            //Revisamos si hay categorías repetidas y si ya se uso el tag
            if (cat.getCategory().equalsIgnoreCase(nuevaCategoria.getCategory())) { 
                System.out.println("Nombre de categoría repetida");
                return;
            }
            if (cat.getTag().equalsIgnoreCase(nuevaCategoria.getTag())) {
            System.out.println("El tag ya está en uso");
            return;
        }
        }
        Integer idPadre = nuevaCategoria.getParentCategoryId();
        if (idPadre != null) {
        boolean padreValido = false;
        
        for (Category cat : listCategories) {
            // Buscamos si existe el ID y si su estatus es 1
            if (cat.getCategory_id().equals(idPadre) && cat.getStatus() == 1) {
                padreValido = true;
                break; // Rompemos este ciclo porque ya encontramos al padre
            }
        }
        
        if (!padreValido) {
            System.out.println("El ID del padre no existe o su estatus no es 1.");
            return; 
        }
    }
    Integer nuevoId = listCategories.size() + 1;
    
    // Usamos el setter para inyectarle el nuevo ID autogenerado
    nuevaCategoria.setCategory_id(nuevoId);

    //Asignar el estatus y guardar en la lista
    nuevaCategoria.setStatus(1);
    listCategories.add(nuevaCategoria);
    
    System.out.println("Categoría registrada correctamente");

    }

    public static void deleteCategory(Integer id) {
    
    for (Category cat : listCategories) {
        Integer idPadreActual = cat.getParentCategoryId();
        
        // Verificamos que no sea nulo Y comparamos si es igual al id que queremos borrar
        if (idPadreActual != null && idPadreActual.equals(id)) {
            System.out.println("Error: La categoría tiene hijas y no puede eliminarse");
            return; 
        }
    }
    
    for (Category cat : listCategories) {
        // Usamos el getter para obtener el ID de la categoría actual
        if (cat.getCategory_id().equals(id)) {
            cat.setStatus(0);
            System.out.println("Categoría eliminada correctamente");
            break; 
        }
    }
}
}


    


