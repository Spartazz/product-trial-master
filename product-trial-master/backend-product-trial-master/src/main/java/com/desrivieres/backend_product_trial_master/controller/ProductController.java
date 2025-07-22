package com.desrivieres.backend_product_trial_master.controller;

import com.desrivieres.backend_product_trial_master.model.Product;
import com.desrivieres.backend_product_trial_master.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            Iterable<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des produits", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de la récupération des produits");
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(newProduct);
        } catch (Exception e) {
            log.error("Erreur lors de l'ajout du produit: {}", product.getCode(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de l'ajout du produit");
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (OptimisticLockingFailureException e) {
            // Gestion de l'erreur de concurrence
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Le produit a été modifié par un autre utilisateur. Veuillez rafraîchir et réessayer.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Le produit n'a pas été trouvé");
        } catch (Exception e) {
            log.error("Erreur lors de la suppression du produit avec l'ID: {}", id, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de la suppression du produit");
        }
    }
}
