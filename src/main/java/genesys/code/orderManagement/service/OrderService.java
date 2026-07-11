package genesys.code.orderManagement.service;

import genesys.code.orderManagement.dto.requestDto.OrderRequestDto;
import genesys.code.orderManagement.dto.responseDto.OrderResponseDto;
import genesys.code.orderManagement.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    List<Order> getAllOrders();
    Order getOrder(int  orderId);

}
