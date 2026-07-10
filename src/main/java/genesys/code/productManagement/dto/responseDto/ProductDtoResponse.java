package genesys.code.productManagement.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import genesys.code.productManagement.model.Product;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDtoResponse {
    private String status;
    private String message;
    private String statusCode;
    private List<Product> products;

}
