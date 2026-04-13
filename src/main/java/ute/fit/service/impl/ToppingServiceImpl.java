package ute.fit.service.impl;

import java.util.ArrayList;
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
	
	@Override
	public List<ToppingDTO> findAll() {
        List<ToppingDTO> list = new ArrayList<>();
        list.add(new ToppingDTO(1, "Pearl", 5000));
        list.add(new ToppingDTO(2, "CheeseFoam", 9000));
        list.add(new ToppingDTO(3, "Caramel", 9000));
        list.add(new ToppingDTO(4, "Jelly", 4000));
        return list;
    }
}
