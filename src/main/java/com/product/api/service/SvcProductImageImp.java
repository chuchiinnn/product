package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.exception.ApiException;
import com.product.api.exception.DBAccessException;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;

@Service
public class SvcProductImageImp implements SvcProductImage {

    @Autowired
    RepoProductImage repo;

    @Autowired
    RepoProduct repoProduct;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.images}")
    private String uploadImages;

    @Override
    public ResponseEntity<List<ProductImage>> getProductImages(Integer id) {
        try {
            if (repoProduct.findById(id).isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
            }
            List<ProductImage> images = repo.findByProductId(id);
            for (ProductImage productImage : images) {
                String img = readProductImageFile(productImage.getImage());
                productImage.setImage(img);
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<String> createProductImage(DtoProductImageIn in) {
        try {
            if (repoProduct.findById(in.getProductId()).isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
            }

            // Eliminar el prefijo "data:image/png;base64," si existe (C11)
            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                if (commaIndex != -1) {
                    in.setImage(in.getImage().substring(commaIndex + 1));
                }
            }

            // Decodifica la cadena Base64 a bytes (C11)
            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());

            // Genera un nombre único para la imagen (C11)
            String fileName = UUID.randomUUID().toString() + ".png";

            // Construye la ruta completa donde se guardará la imagen (C11)
            Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);

            // Asegurarse de que el directorio exista y escribir el archivo (C11)
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageBytes);

            // Crear la entidad ProductImage y guardar la URL en la base de datos (C11)
            ProductImage productImage = new ProductImage();
            productImage.setProductId(in.getProductId());
            productImage.setImage("/product/" + fileName);
            productImage.setStatus(1);
            repo.save(productImage);

            return new ResponseEntity<>("La imagen ha sido registrada", HttpStatus.CREATED);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        }
    }

    @Override
    public ResponseEntity<String> deleteProductImage(Integer id, Integer productImageId) {
        try {
            if (repoProduct.findById(id).isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
            }
            if (repo.findById(productImageId).isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe");
            }
            repo.deleteById(productImageId);
            return new ResponseEntity<>("La imagen ha sido eliminada", HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Método de lectura a Base64 tomado de C12
    private String readProductImageFile(String imageUrl) {
        try {
            if (imageUrl.startsWith("/")) {
                imageUrl = imageUrl.substring(1);
            }
            Path imagePath = Paths.get(uploadDir, uploadImages, imageUrl);
            if (!Files.exists(imagePath)) {
                return "";
            }
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }
}