package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

public interface IDao<ENTITY, ID> {

	ENTITY createEntity();

	ENTITY getById(ID id);

	List<ENTITY> getAll();

	void insert(ENTITY entity);

	void update(ENTITY entity);

	void delete(ID id);

	void deleteAll();

}
