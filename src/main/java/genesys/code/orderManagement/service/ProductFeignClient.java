package genesys.code.orderManagement.service;

import genesys.code.orderManagement.dto.responseDto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductFeignClient {


    @GetMapping("/api/productService/getProductByProductCode")
    Product getProductByProductCode(
            @RequestParam("productCode") String productCode);

    @PutMapping("/api/productService/updateProduct")
    void updateProduct(
            @RequestParam("productCode") String productCode,
            @RequestParam("quantity") Integer quantity);
}