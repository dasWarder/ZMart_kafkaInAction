package com.example.zmart.streamsnode.mapper;

import com.example.zmart.streamsnode.dto.ProductsInfo;
import com.example.zmart.streamsnode.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    ProductsInfo productToProductsInfo(Product product);
}
