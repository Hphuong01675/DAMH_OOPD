package ute.fit.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Product product; 
    private int quantity;
    private double unitPrice; // Giá chốt tại thời điểm bỏ vào giỏ hàng
    private double subTotal;
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        // Lấy giá tổng sau khi đã qua Decorator (Beverage + Toppings)
        this.unitPrice = product.getPrice(); 
    }
    
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getSubTotal() {
        return unitPrice * quantity;
    }

    public String getLineDescription() {
        return String.format("%d x %s", quantity, product.getDescription());
    }
    



}