package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itacademy.jd2.bs.ta.dao.api.IUserAccountDao;
import com.itacademy.jd2.bs.ta.dao.api.entity.IUserAccount;
import com.itacademy.jd2.bs.ta.dao.api.entity.enums.UserRole;
import com.itacademy.jd2.bs.ta.dao.api.filter.UserAccountFilter;
import com.itacademy.jd2.bs.ta.dao.api.util.NotUniqueUserException;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.entity.UserAccount;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;

@Repository
public class UserAccountDaoImpl extends AbstractDaoImpl<IUserAccount, Integer> implements IUserAccountDao {

	@Override
	public IUserAccount createEntity() {
		return new UserAccount();
	}

	@Override
	public void insert(final IUserAccount entity) {
		executeStatement(new PreparedStatemenAction<IUserAccount>(String.format(
				"insert into %s (email, password, first_name, last_name, role, created, updated) values(?,?,?,?,?,?,?)",
				getTableName()), true) {
			@Override
			public IUserAccount doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getEmail());
				pStmt.setString(2, entity.getPassword());
				pStmt.setString(3, entity.getFirstName());
				pStmt.setString(4, entity.getLastName());
				pStmt.setString(5, entity.getRole().name());
				pStmt.setObject(6, entity.getCreated(), Types.TIMESTAMP);
				pStmt.setObject(7, entity.getUpdated(), Types.TIMESTAMP);

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
	public void update(final IUserAccount entity) {
		executeStatement(new PreparedStatemenAction<IUserAccount>(String.format(
				"update %s set email=?, password=?, first_name=?, last_name=?, role=?, updated=? where id=?",
				getTableName())) {
			@Override
			public IUserAccount doWithPreparedStatement(final PreparedStatement pStmt) throws SQLException {
				pStmt.setString(1, entity.getEmail());
				pStmt.setString(2, entity.getPassword());
				pStmt.setString(3, entity.getFirstName());
				pStmt.setString(4, entity.getLastName());
				pStmt.setString(5, entity.getRole().name());
				pStmt.setObject(6, entity.getUpdated(), Types.TIMESTAMP);
				pStmt.setInt(7, entity.getId());

				pStmt.executeUpdate();
				return entity;
			}
		});
	}

	@Override
	protected IUserAccount parseRow(final ResultSet resultSet) throws SQLException {
		final IUserAccount entity = createEntity();
		entity.setId((Integer) resultSet.getObject("id"));
		entity.setEmail(resultSet.getString("email"));
		entity.setPassword(resultSet.getString("password"));
		entity.setFirstName(resultSet.getString("first_name"));
		entity.setLastName(resultSet.getString("last_name"));
		entity.setRole(UserRole.valueOf(resultSet.getString("role")));
		entity.setCreated(resultSet.getTimestamp("created"));
		entity.setUpdated(resultSet.getTimestamp("updated"));

		return entity;
	}

	@Override
	protected String getTableName() {
		return "user_account";
	}

	@Override
	public List<IUserAccount> find(final UserAccountFilter filter) {
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
	public IUserAccount getByEmail(final String email) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void insertUserAccount(final IUserAccount userAccount) throws NotUniqueUserException {
		throw new RuntimeException("not implemented");
	}

}
