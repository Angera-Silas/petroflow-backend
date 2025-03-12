package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}