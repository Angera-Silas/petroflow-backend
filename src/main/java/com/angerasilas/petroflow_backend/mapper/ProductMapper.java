package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;
import com.angerasilas.petroflow_backend.dto.ProductDto;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.repository.OrganizationRepository;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final OrganizationRepository organizationRepository;
    private final FacilityRepository facilityRepository;


    public ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setDateAdded(product.getDateAdded());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductCategory(product.getProductCategory());
        dto.setProductSubCategory(product.getProductSubCategory());
        dto.setDepartment(product.getDepartment());
        return dto;
    }

    public Product toEntity(ProductDto productDto) {
        Product entity = new Product();
        entity.setId(productDto.getId());
        entity.setDateAdded(productDto.getDateAdded());
        entity.setProductName(productDto.getProductName());
        entity.setProductDescription(productDto.getProductDescription());
        entity.setProductCategory(productDto.getProductCategory());
        entity.setProductSubCategory(productDto.getProductSubCategory());
        entity.setDepartment(productDto.getDepartment());
        entity.setOrganization(organizationRepository.findById(productDto.getOrgId()).orElse(null));
        entity.setFacility(facilityRepository.findById(productDto.getFacilityId()).orElse(null));
        return entity;
    }
}