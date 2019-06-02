package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITourDateDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.TourDateFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Tour;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.TourDate;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class TourDateDaoImpl extends AbstractDaoImpl<ITourDate, Integer> implements ITourDateDao {

	@Override
	public ITourDate createEntity() {
		return new TourDate();
	}

	@Override
	public void insert(final ITourDate entity) {
		executeStatement(new PreparedStatemenAction<ITourDate>(String.format(
				"insert into %s (tour_id, num_person, date_start, date_end, created, updated) values(?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public ITourDate doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getTour().getId());
				pStmt.setInt(2, entity.getNumPerson());
				pStmt.setObject(3, entity.getDateStart(), Types.DATE);
				pStmt.setObject(4, entity.getDateEnd(), Types.DATE);
				pStmt.setObject(5, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(6, entity.getUpdated(), Types.TIMESTAMP);

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
	public void update(final ITourDate entity) {
		executeStatement(new PreparedStatemenAction<ITourDate>(
				String.format("update %s set tour_id=?, num_person=?, date_start=?, date_end=?, updated=? where id=?",
						getTableName())) {
			@Override
			public ITourDate doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getTour().getId());
				pStmt.setInt(2, entity.getNumPerson());
				pStmt.setObject(3, entity.getDateStart(), Types.DATE);
				pStmt.setObject(4, entity.getDateEnd(), Types.DATE);
				pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(6, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected ITourDate parseRow(final ResultSet resultSet) throws SQLException {
		final ITourDate entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));

		final ITour tour = new Tour();
		tour.setId((Integer) resultSet.getObject("tour_id"));
		entity.setTour(tour);

		entity.setNumPerson(resultSet.getInt("num_person"));
		entity.setDateStart(resultSet.getDate("date_start"));
		entity.setDateEnd(resultSet.getDate("date_end"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "tour_date";
	}

	@Override
	public List<ITourDate> getByTourId(final TourDateFilter filter, final  Integer tourId) {
		throw new RuntimeException("not implemented");
	}

}
