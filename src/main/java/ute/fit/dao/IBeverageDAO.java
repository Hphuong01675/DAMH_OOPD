package ute.fit.dao;

import ute.fit.entity.BeverageEntity;
import java.util.List;

public interface IBeverageDAO {
    List<BeverageEntity> findAll();
    BeverageEntity findById(int id);
    void insert(BeverageEntity beverage);
    void update(BeverageEntity beverage);
    void delete(int id);
}