package ute.fit.dao;

import java.util.List;

import ute.fit.entity.ToppingEntity;

public interface IToppingDAO {
	List<ToppingEntity> findAll();
}
