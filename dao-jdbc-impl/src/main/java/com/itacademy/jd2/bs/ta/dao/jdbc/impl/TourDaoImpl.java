package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITourDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICity;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.TourStatus;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.City;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Tour;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.TourType;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class TourDaoImpl extends AbstractDaoImpl<ITour, Integer> implements ITourDao {

	@Override
	public ITour createEntity() {
		return new Tour();
	}

	@Override
	public void insert(final ITour entity) {
		executeStatement(new PreparedStatemenAction<ITour>(String.format(
				"insert into %s (tour_status, name, price, tour_type_id, nights, image, description, city_id, address, created, updated) values(?,?,?,?,?,?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public ITour doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getTourStatus().name());
				pStmt.setString(2, entity.getName());
				pStmt.setInt(3, entity.getPrice());
				pStmt.setInt(4, entity.getTourType().getId());
				pStmt.setInt(5, entity.getNights());
				pStmt.setString(6, entity.getImage());
				pStmt.setString(7, entity.getDescription());
				pStmt.setInt(8, entity.getCity().getId());
				pStmt.setString(9, entity.getAddress());
				pStmt.setObject(10, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(11, entity.getUpdated(), Types.TIMESTAMP);

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
	public void update(final ITour entity) {
		executeStatement(new PreparedStatemenAction<ITour>(String.format(
				"update %s set tour_status=?, name=?, price=?, tour_type_id=?, nights=?, image=?, description=?, city_id=?, address=?, updated=? where id=?",
				getTableName())) {
			@Override
			public ITour doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getTourStatus().name());
				pStmt.setString(2, entity.getName());
				pStmt.setInt(3, entity.getPrice());
				pStmt.setInt(4, entity.getTourType().getId());
				pStmt.setInt(5, entity.getNights());
				pStmt.setString(6, entity.getImage());
				pStmt.setString(7, entity.getDescription());
				pStmt.setInt(8, entity.getCity().getId());
				pStmt.setString(9, entity.getAddress());
				pStmt.setObject(10, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(11, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected ITour parseRow(final ResultSet resultSet) throws SQLException {
		final ITour entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setTourStatus(TourStatus.valueOf(resultSet.getString("tour_status")));
		entity.setName(resultSet.getString("name"));
		entity.setPrice(resultSet.getInt("price"));

		final ITourType tourType = new TourType();
		tourType.setId((Integer) resultSet.getObject("tour_type_id"));
		entity.setTourType(tourType);

		entity.setNights(resultSet.getInt("nights"));
		entity.setImage(resultSet.getString("image"));
		entity.setDescription(resultSet.getString("description"));

		final ICity city = new City();
		city.setId((Integer) resultSet.getObject("city_id"));
		entity.setCity(city);

		entity.setAddress(resultSet.getString("address"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "tour";
	}

	@Override
	public List<ITour> find(final TourFilter filter) {
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
	public Integer getMaxPrice() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Integer getMaxNights() {
		throw new RuntimeException("not implemented");
	}

}
