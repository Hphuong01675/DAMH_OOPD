package ute.fit.service;

import jakarta.servlet.http.Part;
import ute.fit.entity.BeverageEntity;
import ute.fit.model.BeverageDTO;

import java.util.List;

public interface IBeverageService {
	List<BeverageDTO> getAllBeverages(String keyword);
    BeverageDTO getById(int id);
    void save(BeverageDTO dto, Part filePart) throws Exception;
    void toggleStatus(int id);
}