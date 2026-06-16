package com.villagecart.order.controller;

import com.villagecart.order.entity.Order;
import com.villagecart.order.entity.OrderStatus;
import com.villagecart.order.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping()
    public ResponseEntity<Order> placeOrder(@RequestBody Order order){
        Order saveOrder=orderService.createOrder(order);
        return new ResponseEntity<>(saveOrder, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long id){
        Order order=orderService.getOrderDetails(id);
        return ResponseEntity.ok(order);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> changeStatus (@PathVariable Long id,@RequestParam
    OrderStatus newStatus){
        Order updatedOrder =orderService.updateOrderStatus(id,newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
