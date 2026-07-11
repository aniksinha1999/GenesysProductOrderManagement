package genesys.code.orderManagement.dto.requestDto;

import lombok.Data;

@Data
public class NotificationRequestDto {
    private String email;
    private  String productName;
    private  String productDescription;
    private String processingStatus;
    private String quantity;
    private String orderNumber;
    private String totalAmount;
    private String price;
    private String shippingAddress;
}
