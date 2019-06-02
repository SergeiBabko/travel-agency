package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.INewsDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.INews;
import com.itacademy.jd2.bs.ta.dao.api.filter.NewsFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.News;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class NewsDaoImpl extends AbstractDaoImpl<INews, Integer> implements INewsDao {

	@Override
	public INews createEntity() {
		return new News();
	}

	@Override
	public void insert(final INews entity) {
		executeStatement(new PreparedStatemenAction<INews>(
				String.format("insert into %s (name, image, text, created, updated) values(?,?,?,?,?)", getTableName()),
				true) {
			@Override
			public INews doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getName());
				pStmt.setString(2, entity.getImage());
				pStmt.setString(3, entity.getText());
				pStmt.setObject(4, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(5, entity.getUpdated(), Types.TIMESTAMP);

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
	public void update(final INews entity) {
		executeStatement(new PreparedStatemenAction<INews>(
				String.format("update %s set name=?, image=?, text=?, updated=? where id=?", getTableName())) {
			@Override
			public INews doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getName());
				pStmt.setString(2, entity.getImage());
				pStmt.setString(3, entity.getText());
				pStmt.setObject(4, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(5, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected INews parseRow(final ResultSet resultSet) throws SQLException {
		final INews entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setName(resultSet.getString("name"));
		entity.setImage(resultSet.getString("image"));
		entity.setText(resultSet.getString("text"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "news";
	}

	@Override
	public List<INews> find(final NewsFilter filter) {
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
