package ute.fit.service;

import jakarta.servlet.http.Part;
import ute.fit.model.BeverageBuilder;
import ute.fit.model.BeverageDTO;
//import ute.fit.model.OrderItemDTO;

import java.util.List;

public interface IBeverageService {
	List<BeverageDTO> getAllBeverages(String keyword);
	List<BeverageDTO> findAll();
    BeverageDTO getById(int id);
    void save(BeverageDTO dto, Part filePart) throws Exception;
    void toggleStatus(int id);
    BeverageBuilder getBeverageBuilder(int id);
}