package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.IBookedDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IBooked;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.BookedFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Booked;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Customer;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.TourDate;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class BookedDaoImpl extends AbstractDaoImpl<IBooked, Integer> implements IBookedDao {

	@Override
	public IBooked createEntity() {
		return new Booked();
	}

	@Override
	public void insert(final IBooked entity) {
		executeStatement(new PreparedStatemenAction<IBooked>(String.format(
				"insert into %s (customer_id, tour_date_id, num_person, price, message, processed, created, updated) values(?,?,?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public IBooked doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getCustomer().getId());
				pStmt.setInt(2, entity.getTourDate().getId());
				pStmt.setObject(3, entity.getNumPerson(), Types.INTEGER);
				pStmt.setInt(4, entity.getPrice());
				pStmt.setString(5, entity.getMessage());
				pStmt.setBoolean(6, entity.getProcessed());
				pStmt.setObject(7, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(8, entity.getUpdated(), Types.TIMESTAMP);

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
	public void update(final IBooked entity) {
		executeStatement(new PreparedStatemenAction<IBooked>(String.format(
				"update %s set customer_id=?, tour_date_id=?, num_person=?, price=?, message=?, processed=?, updated=? where id=?",
				getTableName())) {
			@Override
			public IBooked doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getCustomer().getId());
				pStmt.setInt(2, entity.getTourDate().getId());
				pStmt.setObject(3, entity.getNumPerson(), Types.INTEGER);
				pStmt.setInt(4, entity.getPrice());
				pStmt.setString(5, entity.getMessage());
				pStmt.setBoolean(6, entity.getProcessed());
				pStmt.setObject(7, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(8, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected IBooked parseRow(final ResultSet resultSet) throws SQLException {
		final IBooked entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));

		final ICustomer customer = new Customer();
		customer.setId((Integer) resultSet.getObject("customer_id"));
		entity.setCustomer(customer);

		final ITourDate tourDate = new TourDate();
		tourDate.setId((Integer) resultSet.getObject("tour_date_id"));
		entity.setTourDate(tourDate);

		entity.setNumPerson((Integer) resultSet.getObject("num_person"));
		entity.setPrice(resultSet.getInt("price"));
		entity.setMessage(resultSet.getString("message"));
		entity.setProcessed(resultSet.getBoolean("processed"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "booked";
	}

	@Override
	public List<IBooked> find(final BookedFilter filter) {
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
	public List<IBooked> getByCustomerId(final BookedFilter filter, final Integer customerId) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public long getCountByTourId(final Integer tourId) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<Integer, Long> getBookedByMonth() {
		throw new RuntimeException("not implemented");
	}

}
