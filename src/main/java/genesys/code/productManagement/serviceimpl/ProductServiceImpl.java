package genesys.code.productManagement.serviceimpl;

import genesys.code.productManagement.dto.requestDto.OrderProductRequestDto;
import genesys.code.productManagement.dto.requestDto.ProductDtoRequest;
import genesys.code.productManagement.dto.responseDto.ProductDtoResponse;
import genesys.code.productManagement.model.Product;
import genesys.code.productManagement.repository.ProductManagementRepo;
import genesys.code.productManagement.service.ProductService;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductManagementRepo productManagementRepo;

    public ProductServiceImpl(ProductManagementRepo productManagementRepo) {
        this.productManagementRepo = productManagementRepo;
    }

    @Override
    public ProductDtoResponse createProduct(List<ProductDtoRequest> requests) {

        ProductDtoResponse response = new ProductDtoResponse();

        try {

            List<Product> products = new ArrayList<>();

            for (ProductDtoRequest request : requests) {

                Product product = new Product();

                product.setProductCode(request.getProductCode());
                product.setProductName(request.getProductName());
                product.setBrand(request.getBrand());
                product.setPrice(request.getPrice());
                product.setQuantity(request.getQuantity());
                product.setCategory(request.getCategory());
                product.setDescription(request.getDescription());
                product.setSupplier(request.getSupplier());
                product.setActive(request.getActive());
                product.setCreatedAt(LocalDateTime.now());
                product.setUpdatedAt(LocalDateTime.now());

                products.add(product);
            }

            List<Product> savedProducts = productManagementRepo.saveAll(products);

            response.setStatusCode("200");
            response.setStatus("Success");
            response.setMessage(savedProducts.size() + " Products created successfully");
            response.setProducts(savedProducts);

        } catch (Exception e) {

            response.setStatusCode("500");
            response.setStatus("Failed");
            response.setMessage("Failed to create products : " + e.getMessage());
        }

        return response;
    }

    @Override
    public ProductDtoResponse getAllProduct() {
        List<Product> products = productManagementRepo.findAll();
        ProductDtoResponse response = new ProductDtoResponse();
        if(products.isEmpty()){

            response.setStatusCode("200");
            response.setStatus("Success");
            response.setMessage("No products found");
        }
        else
        {

            response.setStatusCode("200");
            response.setStatus("Success");
            response.setMessage(products.size()  + " products found");
            response.setProducts(products);
        }
        return response;
    }

    @Override
    public ProductDtoResponse getProductById(int id) {
        Optional<Product> product=productManagementRepo.findById(id);
        ProductDtoResponse response = new ProductDtoResponse();
        if(product.isPresent()){

            response.setStatus("Success");
            response.setMessage("Product found");
            response.setStatusCode("200");
            response.setProducts(product.stream().toList());
        }
        else
        {

            response.setStatus("Failure");
            response.setMessage("Product Not found");
            response.setStatusCode("500");
            response.setProducts(product.stream().toList());
        }
        return response;
    }

    @Override
    public Product getProductByProductCode(String productCode) {

        return productManagementRepo.getProductByCode(productCode);


    }

    @Override
    @Transactional
    public void  updateProduct(String productCode, Integer quantity) {
        Product product = productManagementRepo.getProductByCode(productCode);
        product.setQuantity(product.getQuantity() - quantity);
        product.setUpdatedAt(LocalDateTime.now());
        productManagementRepo.save(product);

    }
}
