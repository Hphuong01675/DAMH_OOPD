package ute.fit.service;

import ute.fit.model.Product;

public interface ProductService {
	Product buildCustomDrink(Long productId,
            String size,
            String sugar,
            String ice,
            String[] toppingIds);
}
