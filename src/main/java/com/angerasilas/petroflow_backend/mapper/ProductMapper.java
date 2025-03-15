package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.ProductDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.entity.Product;

@Component
public class ProductMapper {

    public static ProductDto mapToProductDto(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDto(
            product.getId(),
            product.getDateAdded(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductCategory(),
            product.getProductSubCategory(),
            product.getOrganization() != null ? product.getOrganization().getId() : null,
            product.getFacility() != null ? product.getFacility().getId() : null,
            product.getDepartment()
        );
    }

    public static Product mapToProduct(ProductDto productDto, Organization organization, Facility facility) {
        if (productDto == null) {
            return null;
        }

        if (organization == null) {
            throw new IllegalArgumentException("Organization cannot be null");
        }

        if (facility == null) {
            throw new IllegalArgumentException("Facility cannot be null");
        }

        return new Product(
            productDto.getId(),
            productDto.getDateAdded(),
            productDto.getProductName(),
            productDto.getProductDescription(),
            productDto.getProductCategory(),
            productDto.getProductSubCategory(),
            organization,
            facility,
            productDto.getDepartment(),
            null,
            null
        );
    }
}