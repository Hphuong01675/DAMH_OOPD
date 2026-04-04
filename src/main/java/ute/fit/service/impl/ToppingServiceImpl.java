package ute.fit.service.impl;

import java.util.List;

import ute.fit.dao.IToppingDAO;
import ute.fit.dao.impl.ToppingDAOImpl;
import ute.fit.entity.ToppingEntity;
import ute.fit.service.IToppingService;

public class ToppingServiceImpl implements IToppingService {
	IToppingDAO dao = new ToppingDAOImpl();
	
	@Override
	public List<ToppingEntity> getAllToppings() {
		return dao.findAll();
	}
}
