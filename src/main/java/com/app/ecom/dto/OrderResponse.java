package com.app.ecom.dto;

import com.app.ecom.model.OrderItem;
import com.app.ecom.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
}
