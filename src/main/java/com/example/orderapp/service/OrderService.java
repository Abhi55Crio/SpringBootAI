package com.example.orderapp.service;

import com.example.orderapp.dto.OrderDto;
import com.example.orderapp.model.Order;
import com.example.orderapp.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private OrderDto toDto(Order o) {
        OrderDto dto = new OrderDto();
        dto.setId(o.getId());
        dto.setCustomerName(o.getCustomerName());
        dto.setItem(o.getItem());
        dto.setQuantity(o.getQuantity());
        dto.setPrice(o.getPrice());
        dto.setStatus(o.getStatus());
        dto.setCreatedAt(o.getCreatedAt());
        return dto;
    }

    private Order toEntity(OrderDto dto) {
        Order o = new Order();
        o.setCustomerName(dto.getCustomerName());
        o.setItem(dto.getItem());
        o.setQuantity(dto.getQuantity());
        o.setPrice(dto.getPrice());
        o.setStatus(dto.getStatus());
        // createdAt is set automatically on persist
        return o;
    }

    public OrderDto createOrder(OrderDto dto) {
        Order o = toEntity(dto);
        Order saved = orderRepository.save(o);
        return toDto(saved);
    }

    public OrderDto getOrder(Long id) {
        return orderRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public List<OrderDto> listOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto updateOrder(Long id, OrderDto dto) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (dto.getCustomerName() != null) existing.setCustomerName(dto.getCustomerName());
        if (dto.getItem() != null) existing.setItem(dto.getItem());
        if (dto.getQuantity() != null) existing.setQuantity(dto.getQuantity());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        Order saved = orderRepository.save(existing);
        return toDto(saved);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        orderRepository.deleteById(id);
    }
}