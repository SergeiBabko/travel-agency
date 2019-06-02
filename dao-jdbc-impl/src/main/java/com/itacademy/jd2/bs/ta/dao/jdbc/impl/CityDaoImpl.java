package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ICityDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICountry;
import com.itacademy.jd2.bs.ta.dao.api.filter.CityFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.City;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Country;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class CityDaoImpl extends AbstractDaoImpl<ICity, Integer> implements ICityDao {

	@Override
	public ICity createEntity() {
		return new City();
	}

	@Override
	public void insert(final ICity entity) {
		executeStatement(new PreparedStatemenAction<ICity>(
				String.format("insert into %s (name, country_id, created, updated) values(?,?,?,?)", getTableName()),
				true) {
			@Override
			public ICity doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getName());
				pStmt.setInt(2, entity.getCountry().getId());
				pStmt.setObject(3, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);

				pStmt.executeUpdate();

				final ResultSet rs = pStmt.getGeneratedKeys();
				rs.next();
				final int id = rs.getInt("id");
				rs.close();
				entity.setId(id);
				return entity;
			}
		});
	}

	@Override
	public void update(final ICity entity) {
		executeStatement(new PreparedStatemenAction<ICity>(
				String.format("update %s set name=?, country_id=?, updated=? where id=?", getTableName())) {
			@Override
			public ICity doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getName());
				pStmt.setInt(2, entity.getCountry().getId());
				pStmt.setObject(3, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(4, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected ICity parseRow(final ResultSet resultSet) throws SQLException {
		final ICity entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setName(resultSet.getString("name"));

		final ICountry country = new Country();
		country.setId((Integer) resultSet.getObject("country_id"));
		entity.setCountry(country);

		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "city";
	}

	@Override
	public List<ICity> find(final CityFilter filter) {
		final StringBuilder sqlTile = new StringBuilder("");
		appendSort(filter, sqlTile);
		appendPaging(filter, sqlTile);
		return executeFindQuery(sqlTile.toString());
	}

	@Override
	public long getCount() {
		throw new RuntimeException("not implemented");
	}
}
