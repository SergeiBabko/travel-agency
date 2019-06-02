package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ICustomerDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.CustomerStatus;
import com.itacademy.jd2.bs.ta.dao.api.filter.CustomerFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Customer;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class CustomerDaoImpl extends AbstractDaoImpl<ICustomer, Integer> implements ICustomerDao {

	@Override
	public ICustomer createEntity() {
		return new Customer();
	}

	@Override
	public void insert(final ICustomer entity) {
		executeStatement(new PreparedStatemenAction<ICustomer>(String.format(
				"insert into %s (id, status, middle_name, birthday, passport_number, passport_start, passport_end, phone_number, country, city, street, bonus, created, updated) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public ICustomer doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getId());
				pStmt.setString(2, entity.getStatus().name());
				pStmt.setString(3, entity.getMiddleName());
				pStmt.setString(4, entity.getBirthday());
				pStmt.setString(5, entity.getPassportNumber());
				pStmt.setString(6, entity.getPassportStart());
				pStmt.setString(7, entity.getPassportEnd());
				pStmt.setObject(8, entity.getPhoneNumber(), Types.INTEGER);
				pStmt.setString(9, entity.getCountry());
				pStmt.setString(10, entity.getCity());
				pStmt.setString(11, entity.getStreet());
				pStmt.setObject(12, entity.getBonus(), Types.INTEGER);
				pStmt.setObject(13, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(14, entity.getUpdated(), Types.TIMESTAMP);

				pStmt.executeUpdate();

				return entity;
			}
		});
	}

	@Override
	public void update(final ICustomer entity) {
		executeStatement(new PreparedStatemenAction<ICustomer>(String.format(
				"update %s set status=?, middle_name=?, birthday=?, passport_number=?, passport_start=?, passport_end=?, phone_number=?, country=?, city=?, street=?, bonus=?, updated=? where id=?",
				getTableName())) {
			@Override
			public ICustomer doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getStatus().name());
				pStmt.setString(2, entity.getMiddleName());
				pStmt.setString(3, entity.getBirthday());
				pStmt.setString(4, entity.getPassportNumber());
				pStmt.setString(5, entity.getPassportStart());
				pStmt.setString(6, entity.getPassportEnd());
				pStmt.setObject(7, entity.getPhoneNumber(), Types.INTEGER);
				pStmt.setString(8, entity.getCountry());
				pStmt.setString(9, entity.getCity());
				pStmt.setString(10, entity.getStreet());
				pStmt.setInt(11, entity.getBonus());
				pStmt.setObject(12, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(13, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected ICustomer parseRow(final ResultSet resultSet) throws SQLException {
		final ICustomer entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setStatus(CustomerStatus.valueOf(resultSet.getString("status")));
		entity.setMiddleName(resultSet.getString("middle_name"));
		entity.setBirthday(resultSet.getString("birthday"));
		entity.setPassportNumber(resultSet.getString("passport_number"));
		entity.setPassportStart(resultSet.getString("passport_start"));
		entity.setPassportEnd(resultSet.getString("passport_end"));
		entity.setPhoneNumber(resultSet.getString("phone_number"));
		entity.setCountry(resultSet.getString("country"));
		entity.setCity(resultSet.getString("city"));
		entity.setStreet(resultSet.getString("street"));
		entity.setBonus((Integer) resultSet.getObject("bonus"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "customer";
	}

	@Override
	public List<ICustomer> find(final CustomerFilter filter) {
		final StringBuilder sqlTile = new StringBuilder("");
		appendSort(filter, sqlTile);
		appendPaging(filter, sqlTile);
		return executeFindQuery(sqlTile.toString());
	}

	@Override
	public long getCount() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String activateOrDeactivate(final Integer customerId) {
		throw new RuntimeException("not implemented");
	}

}
