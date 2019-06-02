package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.IFavoriteDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ICustomer;
import com.itacademy.jd2.bs.ta.dao.api.entity.IFavorite;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITour;
import com.itacademy.jd2.bs.ta.dao.api.filter.FavoriteFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Customer;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Favorite;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.Tour;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class FavoriteDaoImpl extends AbstractDaoImpl<IFavorite, Integer> implements IFavoriteDao {

	@Override
	public IFavorite createEntity() {
		return new Favorite();
	}

	@Override
	public void insert(final IFavorite entity) {
		executeStatement(new PreparedStatemenAction<IFavorite>(String.format(
				"insert into %s (customer_id, tour_id, created, updated) values(?,?,?,?)", getTableName()), true) {
			@Override
			public IFavorite doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getCustomer().getId());
				pStmt.setInt(2, entity.getTour().getId());
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
	public void update(final IFavorite entity) {
		executeStatement(new PreparedStatemenAction<IFavorite>(
				String.format("update %s set customer_id=?, tour_id=?, updated=? where id=?", getTableName())) {
			@Override
			public IFavorite doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setInt(1, entity.getCustomer().getId());
				pStmt.setInt(2, entity.getTour().getId());
				pStmt.setObject(3, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(4, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected IFavorite parseRow(final ResultSet resultSet) throws SQLException {
		final IFavorite entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));

		final ICustomer customer = new Customer();
		customer.setId((Integer) resultSet.getObject("customer_id"));
		entity.setCustomer(customer);

		final ITour tour = new Tour();
		tour.setId((Integer) resultSet.getObject("tour_id"));
		entity.setTour(tour);

		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "favorite";
	}

	@Override
	public List<IFavorite> find(final FavoriteFilter filter) {
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
	public List<IFavorite> getByCustomerId(final Integer id, final FavoriteFilter filter) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public long getCountByTourId(final Integer tourId) {
		throw new RuntimeException("not implemented");
	}

}
