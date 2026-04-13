package ute.fit.dao;

import ute.fit.entity.BeverageEntity;
import java.util.List;

public interface IBeverageDAO {
    List<BeverageEntity> findAll();
    BeverageEntity findById(int id);
    List<BeverageEntity> searchByName(String keyword);
    BeverageEntity findByName(String name);
    void insert(BeverageEntity beverage);
    void update(BeverageEntity beverage);
    void toggleSellable(int id);
}