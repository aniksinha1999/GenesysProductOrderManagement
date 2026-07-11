package genesys.code.productManagement.serviceimpl;

import genesys.code.productManagement.dto.requestDto.ProductDtoRequest;
import genesys.code.productManagement.dto.responseDto.ProductDtoResponse;
import genesys.code.productManagement.model.Product;
import genesys.code.productManagement.repository.ProductManagementRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductManagementRepo productManagementRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDtoRequest request;

    @BeforeEach
    void setUp() {

        product = new Product();
        product.setId(1L);
        product.setProductCode("PRD1001");
        product.setProductName("iPhone 16");
        product.setDescription("128 GB");
        product.setPrice(BigDecimal.valueOf(79999));
        product.setQuantity(20);
        product.setCategory("Mobile");
        product.setBrand("Apple");
        product.setSupplier("Apple India");
        product.setActive(true);

        request = new ProductDtoRequest();
        request.setProductCode("PRD1001");
        request.setProductName("iPhone 16");
        request.setDescription("128 GB");
        request.setPrice(BigDecimal.valueOf(79999));
        request.setQuantity(20);
        request.setCategory("Mobile");
        request.setBrand("Apple");
        request.setSupplier("Apple India");
        request.setActive(true);
    }

    @Test
    void createProduct() {

        when(productManagementRepo.saveAll(any()))
                .thenReturn(List.of(product));

        ProductDtoResponse response =
                productService.createProduct(List.of(request));

        assertNotNull(response);
        assertEquals("200", response.getStatusCode());
        assertEquals("Success", response.getStatus());
        assertEquals("1 Products created successfully", response.getMessage());

        verify(productManagementRepo, times(1)).saveAll(any());
    }

    @Test
    void createProduct_Exception() {

        when(productManagementRepo.saveAll(any()))
                .thenThrow(new RuntimeException("DB Error"));

        ProductDtoResponse response =
                productService.createProduct(List.of(request));

        assertEquals("500", response.getStatusCode());
        assertEquals("Failed", response.getStatus());
        assertTrue(response.getMessage().contains("DB Error"));
    }

    @Test
    void getAllProduct() {

        when(productManagementRepo.findAll())
                .thenReturn(List.of(product));

        ProductDtoResponse response =
                productService.getAllProduct();

        assertEquals("200", response.getStatusCode());
        assertEquals("Success", response.getStatus());
        assertEquals(1, response.getProducts().size());

        verify(productManagementRepo).findAll();
    }

    @Test
    void getAllProduct_Empty() {

        when(productManagementRepo.findAll())
                .thenReturn(List.of());

        ProductDtoResponse response =
                productService.getAllProduct();

        assertEquals("200", response.getStatusCode());
        assertEquals("No products found", response.getMessage());
    }

    @Test
    void getProductById() {

        when(productManagementRepo.findById(1))
                .thenReturn(Optional.of(product));

        ProductDtoResponse response =
                productService.getProductById(1);

        assertEquals("200", response.getStatusCode());
        assertEquals("Success", response.getStatus());
        assertEquals("Product found", response.getMessage());
        assertEquals(1, response.getProducts().size());
    }

    @Test
    void getProductById_NotFound() {

        when(productManagementRepo.findById(1))
                .thenReturn(Optional.empty());

        ProductDtoResponse response =
                productService.getProductById(1);

        assertEquals("500", response.getStatusCode());
        assertEquals("Failure", response.getStatus());
        assertEquals("Product Not found", response.getMessage());
    }

    @Test
    void getProductByProductCode() {

        when(productManagementRepo.getProductByCode("PRD1001"))
                .thenReturn(product);

        Product result =
                productService.getProductByProductCode("PRD1001");

        assertNotNull(result);
        assertEquals("PRD1001", result.getProductCode());

        verify(productManagementRepo).getProductByCode("PRD1001");
    }

    @Test
    void updateProduct() {

        when(productManagementRepo.getProductByCode("PRD1001"))
                .thenReturn(product);

        when(productManagementRepo.save(any(Product.class)))
                .thenReturn(product);

        productService.updateProduct("PRD1001", 5);

        assertEquals(15, product.getQuantity());

        verify(productManagementRepo).save(product);
    }

    @Test
    void updateProduct_ProductNotFound() {

        when(productManagementRepo.getProductByCode("PRD1001"))
                .thenReturn(null);

        assertThrows(NullPointerException.class,
                () -> productService.updateProduct("PRD1001", 5));
    }
}