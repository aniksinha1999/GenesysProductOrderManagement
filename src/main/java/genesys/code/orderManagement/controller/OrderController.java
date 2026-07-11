package genesys.code.orderManagement.controller;

import genesys.code.orderManagement.dto.requestDto.OrderRequestDto;
import genesys.code.orderManagement.dto.responseDto.OrderResponseDto;
import genesys.code.orderManagement.model.Order;
import genesys.code.orderManagement.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderService")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return  orderService.createOrder(orderRequestDto);
    }
    @GetMapping("/order")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderService.getOrder(Integer.parseInt(id));
    }
}
