package ute.fit.model;

import java.util.HashMap;
import java.util.Map;

public class ToppingBuilder 
{
    private Product base;
    private Map<String, Integer> toppings = new HashMap<>();

    public ToppingBuilder setBase(Product base) {
        this.base = base;
        return this;
    }

    // ➕ tăng topping
    public ToppingBuilder addTopping(String type, int quantity) {
        toppings.put(type, toppings.getOrDefault(type, 0) + quantity);
        return this;
    }

    // ➖ giảm 1
    public ToppingBuilder decreaseTopping(String type) {
        if (toppings.containsKey(type)) {
            int current = toppings.get(type);
            if (current <= 1) {
                toppings.remove(type);
            } else {
                toppings.put(type, current - 1);
            }
        }
        return this;
    }

    // ❌ xóa hết
    public ToppingBuilder removeTopping(String type) {
        toppings.remove(type);
        return this;
    }

    // 🔥 build
    public Product build() {
        if (base == null) throw new IllegalStateException("Base required");

        Product result = base;

        for (Map.Entry<String, Integer> entry : toppings.entrySet()) {
            String type = entry.getKey();
            int quantity = entry.getValue();

            for (int i = 0; i < quantity; i++) {
                result = ToppingFactory.createTopping(type, result);
            }
        }

        return result;
    }
    
}
