package genesys.code.orderManagement.dto.responseDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponseDto {
    private String orderId;
    private String orderStatus;
    private String statusCode;
    private String message;
    private BigDecimal totalAmount;


    public OrderResponseDto() {
    }
}
