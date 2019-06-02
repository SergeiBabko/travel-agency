package com.itacademy.jd2.bs.ta.dao.api;

import java.util.List;

import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;

public interface ICustomerDao extends IDao<ICustomer, Integer> {

	List<ICustomer> find(CustomerFilter filter);

	long getCount();

	String activateOrDeactivate(Integer customerId);

}
