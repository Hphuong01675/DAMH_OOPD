package ute.fit.service;

public interface IDiscountService {
    double getPriceWithVoucher(double originalPrice, String promotionCode);
}
