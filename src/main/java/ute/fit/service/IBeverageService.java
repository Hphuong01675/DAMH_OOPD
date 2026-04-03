package ute.fit.service;

import jakarta.servlet.http.Part;
import ute.fit.entity.BeverageEntity;
import java.util.List;

public interface IBeverageService {
    List<BeverageEntity> getAll();
    BeverageEntity getById(int id);
    void save(BeverageEntity beverage, Part filePart) throws Exception;
    void delete(int id);
}