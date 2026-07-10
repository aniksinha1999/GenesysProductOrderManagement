package genesys.code.productManagement.service;

import genesys.code.productManagement.dto.requestDto.OrderProductRequestDto;
import genesys.code.productManagement.dto.requestDto.ProductDtoRequest;
import genesys.code.productManagement.dto.responseDto.ProductDtoResponse;
import genesys.code.productManagement.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ProductDtoResponse createProduct(List<ProductDtoRequest> request);
    ProductDtoResponse getAllProduct();
    ProductDtoResponse getProductById(int  id);
    Product getProductByProductCode(String productCode);

    void updateProduct(String productCode, Integer quantity);


}
