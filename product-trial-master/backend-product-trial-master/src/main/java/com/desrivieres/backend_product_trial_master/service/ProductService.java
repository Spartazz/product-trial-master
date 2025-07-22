package com.desrivieres.backend_product_trial_master.service;

import com.desrivieres.backend_product_trial_master.model.Product;
import com.desrivieres.backend_product_trial_master.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Produit avec l'ID " + id + " non trouvé");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateProduct(Product product) {
        try {
            return productRepository.findById(product.getId())
                    .map(existingProduct -> updateProductFields(existingProduct, product))
                    .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé"));
        } catch (OptimisticLockingFailureException e) {
            log.warn("Conflit de concurrence détecté pour le produit {} (version: {})",
                    product.getId(),
                    product.getVersion());
            throw e;
        }
    }

    private Product updateProductFields(Product existingProduct, Product newProduct) {
        existingProduct.setId(newProduct.getId());
        existingProduct.setCode(newProduct.getCode());
        existingProduct.setName(newProduct.getName());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setImage(newProduct.getImage());
        existingProduct.setCategory(newProduct.getCategory());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setQuantity(newProduct.getQuantity());
        existingProduct.setInternalReference(newProduct.getInternalReference());
        existingProduct.setShellId(newProduct.getShellId());
        existingProduct.setInventoryStatus(newProduct.getInventoryStatus());
        existingProduct.setRating(newProduct.getRating());
        existingProduct.setCreateDate(newProduct.getCreateDate());
        existingProduct.setUpdateDate(newProduct.getUpdateDate());
        return productRepository.save(existingProduct);
    }

}
