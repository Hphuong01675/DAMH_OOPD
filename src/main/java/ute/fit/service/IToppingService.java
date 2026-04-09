package ute.fit.service;

import java.util.List;

import ute.fit.model.ToppingDTO;

public interface IToppingService {
	List<ToppingDTO> getAllToppings();
	public List<ToppingDTO> findAll();
}
