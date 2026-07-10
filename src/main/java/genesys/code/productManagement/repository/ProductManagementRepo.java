package genesys.code.productManagement.repository;

import genesys.code.productManagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductManagementRepo extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM PRODUCT_DETAILS  WHERE PRODUCT_CODE  = :productCode", nativeQuery = true)
    Product getProductByCode(@Param("productCode") String productCode);
}
