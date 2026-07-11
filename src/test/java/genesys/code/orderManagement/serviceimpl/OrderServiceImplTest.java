package genesys.code.orderManagement.serviceimpl;

import genesys.code.orderManagement.dto.requestDto.NotificationRequestDto;
import genesys.code.orderManagement.dto.requestDto.OrderRequestDto;
import genesys.code.orderManagement.dto.responseDto.OrderResponseDto;
import genesys.code.orderManagement.dto.responseDto.Product;
import genesys.code.orderManagement.model.Order;
import genesys.code.orderManagement.repository.OrderRepository;
import genesys.code.orderManagement.service.EmailFeignClient;
import genesys.code.orderManagement.service.ProductFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    private EmailFeignClient emailFeignClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Product product;
    private OrderRequestDto requestDto;

    @BeforeEach
    void setUp() {

        product = new Product();
        product.setProductCode("PRD1001");
        product.setProductName("iPhone");
        product.setDescription("Apple Phone");
        product.setPrice(BigDecimal.valueOf(50000));
        product.setQuantity(10);
        product.setActive(true);

        requestDto = new OrderRequestDto();
        requestDto.setProductCode("PRD1001");
        requestDto.setQuantity(2);
        requestDto.setCustomerName("Anik");
        requestDto.setCustomerEmail("anik@gmail.com");
        requestDto.setShippingAddress("Bangalore");
    }

    @Test
    void createOrder() {

        when(productFeignClient.getProductByProductCode(anyString()))
                .thenReturn(product);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(1L);
                    return order;
                });

        OrderResponseDto response = orderService.createOrder(requestDto);

        assertNotNull(response);
        assertEquals("200", response.getStatusCode());
        assertEquals("Order placed successfully", response.getMessage());
        assertEquals("1", response.getOrderId());

        verify(productFeignClient).updateProduct("PRD1001", 2);
        verify(orderRepository).save(any(Order.class));
        verify(emailFeignClient).sendNotification(any(NotificationRequestDto.class));
    }

    @Test
    void createOrder_ProductNotFound() {

        when(productFeignClient.getProductByProductCode(anyString()))
                .thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.createOrder(requestDto));

        assertEquals("Product not found", ex.getMessage());
    }

    @Test
    void createOrder_ProductInactive() {

        product.setActive(false);

        when(productFeignClient.getProductByProductCode(anyString()))
                .thenReturn(product);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.createOrder(requestDto));

        assertEquals("Product is inactive", ex.getMessage());
    }

    @Test
    void createOrder_InsufficientStock() {

        product.setQuantity(1);

        when(productFeignClient.getProductByProductCode(anyString()))
                .thenReturn(product);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.createOrder(requestDto));

        assertEquals("Insufficient stock", ex.getMessage());
    }

    @Test
    void productServiceFallback() {

        OrderResponseDto response =
                orderService.productServiceFallback(
                        requestDto,
                        new RuntimeException("Service Down"));

        assertNotNull(response);
        assertEquals("503", response.getStatusCode());
        assertEquals(
                "Product Service is unavailable. Please try again later.",
                response.getMessage());
    }

    @Test
    void getAllOrders() {

        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrder() {

        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(orderRepository).findById(1L);
    }

    @Test
    void mapNotification() {

        Order order = new Order();

        order.setOrderNumber("ORD001");
        order.setProductName("iPhone");
        order.setProductDescription("Apple Phone");
        order.setQuantity(2);
        order.setPrice(BigDecimal.valueOf(50000));
        order.setTotalAmount(BigDecimal.valueOf(100000));
        order.setStatus(Order.OrderStatus.PROCESSING);

        NotificationRequestDto dto =
                orderService.mapNotification(order, requestDto);

        assertEquals("anik@gmail.com", dto.getEmail());
        assertEquals("ORD001", dto.getOrderNumber());
        assertEquals("iPhone", dto.getProductName());
        assertEquals("Apple Phone", dto.getProductDescription());
        assertEquals("2", dto.getQuantity());
        assertEquals("50000", dto.getPrice());
        assertEquals("100000", dto.getTotalAmount());
        assertEquals("PROCESSING", dto.getProcessingStatus());
        assertEquals("Bangalore", dto.getShippingAddress());
    }
}