package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.ITaDescriptionDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.ITaDescription;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.TaDescription;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class TaDescriptionDaoImpl extends AbstractDaoImpl<ITaDescription, Integer> implements ITaDescriptionDao {

	@Override
	public ITaDescription createEntity() {
		return new TaDescription();
	}

	@Override
	public void insert(final ITaDescription entity) {
		executeStatement(new PreparedStatemenAction<ITaDescription>(String.format(
				"insert into %s (description, contacts, address, image_1, image_2, image_3, created, updated) values(?,?,?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public ITaDescription doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getDescription());
				pStmt.setString(2, entity.getContacts());
				pStmt.setString(3, entity.getAddress());
				pStmt.setString(4, entity.getImage1());
				pStmt.setString(5, entity.getImage2());
				pStmt.setString(6, entity.getImage3());
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
	public void update(final ITaDescription entity) {
		executeStatement(new PreparedStatemenAction<ITaDescription>(String.format(
				"update %s set description=?, contacts=?, address=?, image_1=?, image_2=?, image_3=?, updated=? where id=?",
				getTableName())) {
			@Override
			public ITaDescription doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getDescription());
				pStmt.setString(2, entity.getContacts());
				pStmt.setString(3, entity.getAddress());
				pStmt.setString(4, entity.getImage1());
				pStmt.setString(5, entity.getImage2());
				pStmt.setString(6, entity.getImage3());
				pStmt.setObject(7, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(8, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected ITaDescription parseRow(final ResultSet resultSet) throws SQLException {
		final ITaDescription entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setDescription(resultSet.getString("description"));
		entity.setContacts(resultSet.getString("contacts"));
		entity.setAddress(resultSet.getString("address"));
		entity.setImage1(resultSet.getString("image_1"));
		entity.setImage2(resultSet.getString("image_2"));
		entity.setImage3(resultSet.getString("image_3"));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "ta_description";
	}

}
