package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);

    List<ProductDto> getProductsByOrgId(Long orgId);
    List<ProductDto> getProductsByFacilityId(Long facilityId);
//     List<ProductDto> getProductsByOrgIdAndFacilityId(Long orgId, Long facilityId);
//     List<ProductDto> getProductsByOrgIdAndFacilityIdAndDepartment(Long orgId, Long facilityId, String department);
//     List<ProductDto> getProductsByOrgIdAndFacilityIdAndCategory(Long orgId, Long facilityId, String category);
//     List<ProductDto> getProductsByOrgIdAndFacilityIdAndSubCategory(Long orgId, Long facilityId, String subCategory);
//     List<ProductDto> getProductsByOrgIdAndFacilityIdAndCategoryAndSubCategory(Long orgId, Long facilityId, String category, String subCategory);
//
 }