package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.ProductDto;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.mapper.ProductMapper;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import com.angerasilas.petroflow_backend.service.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductCategory(productDto.getProductCategory());
        product.setProductSubCategory(productDto.getProductSubCategory());
        product.setDepartment(productDto.getDepartment());
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}