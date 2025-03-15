package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.ProductDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.mapper.ProductMapper;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.repository.OrganizationRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import com.angerasilas.petroflow_backend.service.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrganizationRepository organizationRepository;
    private final FacilityRepository facilityRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Organization organization = organizationRepository.findById(productDto.getOrgId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        Facility facility = facilityRepository.findById(productDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        Product product = ProductMapper.mapToProduct(productDto, organization, facility);
        product = productRepository.save(product);
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        Organization organization = organizationRepository.findById(productDto.getOrgId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        Facility facility = facilityRepository.findById(productDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));

        Product updatedProduct = ProductMapper.mapToProduct(productDto, organization, facility);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct = productRepository.save(updatedProduct);
        return ProductMapper.mapToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}