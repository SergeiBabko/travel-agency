package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITourTypeDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourType;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.TourType;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class TourTypeDaoImpl extends AbstractDaoImpl<ITourType, Integer> implements ITourTypeDao {

	@Override
	public ITourType createEntity() {
		return new TourType();
	}

	@Override
	public void insert(final ITourType entity) {
		executeStatement(new PreparedStatemenAction<ITourType>(
				String.format("insert into %s (type, created, updated) values(?,?,?)", getTableName()), true) {
			@Override
			public ITourType doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getType());
				pStmt.setObject(2, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(3, entity.getUpdated(), Types.TIMESTAMP);

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
	public void update(final ITourType entity) {
		executeStatement(new PreparedStatemenAction<ITourType>(
				String.format("update %s set type=?, updated=? where id=?", getTableName())) {
			@Override
			public ITourType doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getType());
				pStmt.setObject(2, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(3, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected ITourType parseRow(final ResultSet resultSet) throws SQLException {
		final ITourType entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setType(resultSet.getString("type"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "tour_type";
	}

	@Override
	public long getCount() {
		throw new RuntimeException("not implemented");
	}

}
