package com.villagecart.order.service;

import com.villagecart.order.dto.ProductResponseDto;
import com.villagecart.order.entity.Order;
import com.villagecart.order.entity.OrderItem;
import com.villagecart.order.entity.OrderStatus;
import com.villagecart.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Service
public class OrderService {

    final OrderRepository orderRepository;
    final  RestClient restClient;
    public OrderService(OrderRepository orderRepository,RestClient restClient){
        this.orderRepository=orderRepository;
        this.restClient=restClient;
    }

    // 1 place brand new order and calculate the total price of the bill dynamically

    @Transactional
    public Order createOrder(Order order) {
        BigDecimal calculatedTotal = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            ProductResponseDto product = restClient.get()
                    .uri("/api/products/{id}", item.getProductId())
                    .retrieve().onStatus(status -> status.value() == 404, ((request, response) -> {
                        throw new RuntimeException("Product validation failed : ID" + item.getProductId() + "does not exist at the vendor shop!");
                    })).body(ProductResponseDto.class);

            BigDecimal itemTotal = item.getPriceAtPurchase().
                    multiply(BigDecimal.valueOf(item.getQuantity()));
            calculatedTotal = calculatedTotal.add(itemTotal);
            order.addOrderItem(item);
        }
        order.setTotalPrice(calculatedTotal);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    //2 update order lifeCycle state shopkeeper accepts the order
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found with ID:" + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    //3 Fetch unique order details with its nested menu choices
    @Transactional(readOnly = true)
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findOrderWithItems(orderId)
                .orElseThrow(() -> new RuntimeException("order details not found for ID:" + orderId));

    }
}
