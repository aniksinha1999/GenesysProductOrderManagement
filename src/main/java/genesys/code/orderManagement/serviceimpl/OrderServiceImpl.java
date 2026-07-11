package genesys.code.orderManagement.serviceimpl;

import genesys.code.orderManagement.dto.requestDto.OrderProductRequestDto;
import genesys.code.orderManagement.dto.requestDto.OrderRequestDto;
import genesys.code.orderManagement.dto.responseDto.OrderResponseDto;
import genesys.code.orderManagement.dto.responseDto.Product;
import genesys.code.orderManagement.model.Order;
import genesys.code.orderManagement.repository.OrderRepository;
import genesys.code.orderManagement.service.OrderService;
import genesys.code.orderManagement.service.ProductFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductFeignClient productFeignClient) {
        this.orderRepository = orderRepository;
        this.productFeignClient = productFeignClient;
    }

    @Override
    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "productServiceFallback")
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        // Fetch Product
        Product product = productFeignClient.getProductByProductCode(
                orderRequestDto.getProductCode());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // Validate Product
        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new RuntimeException("Product is inactive");
        }

        if (product.getQuantity() < orderRequestDto.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // Update Product Quantity
        productFeignClient.updateProduct(
                orderRequestDto.getProductCode(),
                orderRequestDto.getQuantity());

        // Calculate Total Amount
        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(orderRequestDto.getQuantity()));

        // Save Order
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setProductCode(product.getProductCode());
        order.setProductName(product.getProductName());

        order.setCustomerName(orderRequestDto.getCustomerName());
        order.setCustomerEmail(orderRequestDto.getCustomerEmail());
        order.setShippingAddress(orderRequestDto.getShippingAddress());

        order.setQuantity(orderRequestDto.getQuantity());
        order.setPrice(product.getPrice());
        order.setTotalAmount(totalAmount);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PROCESSING);

        order = orderRepository.save(order);

        // Prepare Response
        OrderResponseDto response = new OrderResponseDto();
        response.setStatusCode("200");

        response.setMessage("Order placed successfully");
        response.setOrderId(String.valueOf(order.getId()));
        response.setOrderStatus(String.valueOf(order.getStatus()));
        response.setTotalAmount(order.getTotalAmount());

        return response;
    }

    /**
     * Circuit Breaker Fallback Method
     */
    public OrderResponseDto productServiceFallback(
            OrderRequestDto orderRequestDto,
            Throwable throwable) {

        OrderResponseDto response = new OrderResponseDto();

        response.setStatusCode("503");

        response.setMessage(
                "Product Service is unavailable. Please try again later.");

        return response;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(int orderId) {
        OrderResponseDto response = new OrderResponseDto();


        return orderRepository.findById((long) orderId).orElse(null);
    }
}