package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.IReviewDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IReview;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITourDate;
import com.itacademy.jd2.bs.ta.dao.api.filter.ReviewFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Customer;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Review;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.TourDate;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class ReviewDaoImpl extends AbstractDaoImpl<IReview, Integer> implements IReviewDao {

	@Override
	public IReview createEntity() {
		return new Review();
	}

	@Override
	public void insert(final IReview entity) {
		executeStatement(new PreparedStatemenAction<IReview>(String.format(
				"insert into %s (review, rating, customer_id, tour_date_id, created, updated) values(?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public IReview doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getReview());
				pStmt.setInt(2, entity.getRating());
				pStmt.setInt(3, entity.getCustomer().getId());
				pStmt.setInt(4, entity.getTourDate().getId());
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
	public void update(final IReview entity) {
		executeStatement(new PreparedStatemenAction<IReview>(
				String.format("update %s set review=?, rating=?, customer_id=?, tour_date_id=?, updated=? where id=?",
						getTableName())) {
			@Override
			public IReview doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getReview());
				pStmt.setInt(2, entity.getRating());
				pStmt.setInt(3, entity.getCustomer().getId());
				pStmt.setInt(4, entity.getTourDate().getId());
				pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(6, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected IReview parseRow(final ResultSet resultSet) throws SQLException {
		final IReview entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setReview(resultSet.getString("review"));
		entity.setRating(resultSet.getInt("rating"));

		final ICustomer customer = new Customer();
		customer.setId((Integer) resultSet.getObject("customer_id"));
		entity.setCustomer(customer);

		final ITourDate tourDate = new TourDate();
		tourDate.setId((Integer) resultSet.getObject("tour_date_id"));
		entity.setTourDate(tourDate);

		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "review";
	}

	@Override
	public List<IReview> find(final ReviewFilter filter) {
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
	public List<IReview> getByCustomerId(final ReviewFilter filter, final Integer customerId) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<IReview> getByTourId(final ReviewFilter filter, final  Integer tourId) {
		throw new RuntimeException("not implemented");
	}

}
