package com.desrivieres.backend_product_trial_master.repository;

import com.desrivieres.backend_product_trial_master.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {
}
