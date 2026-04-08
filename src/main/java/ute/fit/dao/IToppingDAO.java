package ute.fit.dao;

import java.util.List;

import ute.fit.entity.ToppingEntity;
import ute.fit.model.ToppingDTO;

public interface IToppingDAO {
	List<ToppingEntity> findAll();
	ToppingDTO findById(Long id);
}
