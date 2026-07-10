package genesys.code.productManagement.controller;

import genesys.code.productManagement.dto.requestDto.OrderProductRequestDto;
import genesys.code.productManagement.dto.requestDto.ProductDtoRequest;
import genesys.code.productManagement.dto.responseDto.ProductDtoResponse;
import genesys.code.productManagement.model.Product;
import genesys.code.productManagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productService")
public class ProductManagementController {
    private final ProductService productService;

    public ProductManagementController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ProductDtoResponse createProduct(@Valid  @RequestBody List<ProductDtoRequest> product) {
        return productService.createProduct(product);
    }
    @GetMapping("/product")
    public ProductDtoResponse getAllProduct() {
        return productService.getAllProduct();
    }
    @GetMapping("/product/{id}")
    public ProductDtoResponse getProductDetail(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getProductByProductCode")
    public Product getProductByProductCode(@RequestParam String productCode) {
        return productService.getProductByProductCode(productCode);
    }
    @PutMapping("/updateProduct")
    public void updateProduct(@RequestParam String productCode,@RequestParam int quantity) {
      productService.updateProduct(productCode,quantity);
    }
}
