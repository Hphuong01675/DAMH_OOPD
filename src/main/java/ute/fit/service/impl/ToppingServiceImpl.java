package ute.fit.service.impl;

import java.util.List;

import ute.fit.dao.IToppingDAO;
import ute.fit.dao.impl.ToppingDAOImpl;
import ute.fit.entity.ToppingEntity;
import ute.fit.model.ToppingDTO;
import ute.fit.service.IToppingService;

public class ToppingServiceImpl implements IToppingService {
	IToppingDAO dao = new ToppingDAOImpl();
	
	private ToppingDTO toDTO(ToppingEntity e) {
        ToppingDTO dto = new ToppingDTO();
        dto.setToppingID(e.getToppingID());
        dto.setName(e.getName());
        dto.setPrice(e.getPrice());
        return dto;
    }
	
	@Override
	public List<ToppingDTO> getAllToppings() {
		return dao.findAll()
	              .stream()
	              .map(this::toDTO)
	              .toList();
	}
}
