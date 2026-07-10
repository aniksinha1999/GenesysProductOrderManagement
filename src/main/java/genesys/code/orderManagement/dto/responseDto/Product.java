package genesys.code.orderManagement.dto.responseDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {


    private String productCode;


    private String productName;


    private String description;


    private BigDecimal price;


    private Integer quantity;


    private String category;


    private String brand;


    private String supplier;


    private Boolean active = true;


    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
