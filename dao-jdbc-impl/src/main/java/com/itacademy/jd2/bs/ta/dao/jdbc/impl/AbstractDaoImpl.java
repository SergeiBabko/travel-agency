package com.itacademy.jd2.bs.ta.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;

import com.itacademy.jd2.bs.ta.dao.api.IDao;
import com.itacademy.jd2.bs.ta.dao.api.filter.AbstractFilter;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.PreparedStatemenAction;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.SQLExecutionException;
import com.itacademy.jd2.bs.ta.dao.jdbc.impl.util.StatemenAction;

public abstract class AbstractDaoImpl<ENTITY, ID> implements IDao<ENTITY, ID> {

	@Value("${jdbc.url}")
	private String url;

	@Value("${jdbc.user}")
	private String user;

	@Value("${jdbc.password}")
	private String password;

	@PreDestroy
	public void beforeDestroy() {
	}

	@PostConstruct
	private void init() {
		if (url == null) {
			throw new IllegalArgumentException("[jdbc.url] cant be null");
		}

		if (password == null) {
			throw new IllegalArgumentException("[jdbc.password] cant be null");
		}

		if (user == null) {
			throw new IllegalArgumentException("[jdbc.user] cant be null");
		}
	}

	@Override
	public ENTITY getById(final ID id) {
		StatemenAction<ENTITY> action = (statement) -> {
			statement.executeQuery("select * from " + getTableName() + " where id=" + id);

			final ResultSet resultSet = statement.getResultSet();
			final Set<String> columns = resolveColumnNames(resultSet);

			final boolean hasNext = resultSet.next();
			ENTITY result = null;
			if (hasNext) {
				result = parseRow(resultSet, columns);
			}

			resultSet.close();
			return result;
		};
		ENTITY entityById = executeStatement(action);
		return entityById;
	}

	@Override
	public List<ENTITY> getAll() {
		StatemenAction<List<ENTITY>> action = new StatemenAction<List<ENTITY>>() {
			@Override
			public List<ENTITY> doWithStatement(final Statement statement) throws SQLException {
				statement.executeQuery("select * from " + getTableName());

				final ResultSet resultSet = statement.getResultSet();
				final Set<String> columns = resolveColumnNames(resultSet);
				final List<ENTITY> result = new ArrayList<>();
				boolean hasNext = resultSet.next();
				while (hasNext) {
					result.add(parseRow(resultSet, columns));
					hasNext = resultSet.next();
				}
				resultSet.close();
				return result;
			}
		};
		return executeStatement(action);
	}

	@Override
	public void delete(final ID id) {
		executeStatement(
				new PreparedStatemenAction<Integer>(String.format("delete from %s where id=?", getTableName())) {
					@Override
					public Integer doWithPreparedStatement(final PreparedStatement prepareStatement)
							throws SQLException {
						prepareStatement.setObject(1, id);
						return prepareStatement.executeUpdate();
					}
				});
	}

	@Override
	public void deleteAll() {
		executeStatement(new PreparedStatemenAction<Integer>("delete from " + getTableName()) {
			@Override
			public Integer doWithPreparedStatement(final PreparedStatement prepareStatement) throws SQLException {
				final int executeUpdate = prepareStatement.executeUpdate();
				return executeUpdate;
			}
		});
	}

	protected <T> T executeStatement(final StatemenAction<T> action) {
		try (Connection c = getConnection(); Statement stmt = c.createStatement()) {
			c.setAutoCommit(false);
			return action.doWithStatement(stmt);

		} catch (final SQLException e) {
			throw new SQLExecutionException(e); // wrap catchable exception with
			// runtime
		}
	}

	protected <T> T executeStatement(final PreparedStatemenAction<T> action) {
		try (Connection c = getConnection();
				PreparedStatement pStmt = action.isReturnGeneratedKeys()
						? c.prepareStatement(action.getSql(), Statement.RETURN_GENERATED_KEYS)
						: c.prepareStatement(action.getSql())) {
			c.setAutoCommit(false);
			try {
				final T doWithPreparedStatement = action.doWithPreparedStatement(pStmt);
				c.commit();
				return doWithPreparedStatement;
			} catch (final Exception e) {
				c.rollback();
				throw new RuntimeException(e);
			}

		} catch (final SQLException e) {
			throw new SQLExecutionException(e);
		}
	}

	public List<ENTITY> getByProperty(final String tableField, final String prop) {
		return executeFindQueryWithCustomSelect(
				String.format("select * from %s where %s='%s'", getTableName(), tableField, prop));
	}

	protected List<ENTITY> executeFindQuery(final String sqlFragment) {
		return executeFindQueryWithCustomSelect(String.format("select * from %s %s", getTableName(), sqlFragment));
	}

	protected List<ENTITY> executeFindQueryWithCustomSelect(final String fullSql) {
		return executeStatement(new StatemenAction<List<ENTITY>>() {
			@Override
			public List<ENTITY> doWithStatement(final Statement statement) throws SQLException {
				statement.executeQuery(fullSql);

				final ResultSet resultSet = statement.getResultSet();

				final Set<String> columns = resolveColumnNames(resultSet);

				final List<ENTITY> result = new ArrayList<>();
				boolean hasNext = resultSet.next();
				while (hasNext) {
					result.add(parseRow(resultSet, columns));
					hasNext = resultSet.next();
				}
				resultSet.close();
				return result;
			}

		});
	}

	protected long executeCountQuery(final String sql) {
		return executeStatement(new StatemenAction<Long>() {
			@Override
			public Long doWithStatement(final Statement statement) throws SQLException {
				final String fullSql = String.format("select count(*) as total from %s %s", getTableName(), sql);

				statement.executeQuery(fullSql);

				final ResultSet resultSet = statement.getResultSet();
				resultSet.next();

				final long total = resultSet.getLong("total");
				resultSet.close();
				return total;
			}
		});
	}

	private Set<String> resolveColumnNames(final ResultSet resultSet) throws SQLException {
		final ResultSetMetaData rsMetaData = resultSet.getMetaData();
		final int numberOfColumns = rsMetaData.getColumnCount();
		final Set<String> columns = new HashSet<>();
		for (int i = 1; i <= numberOfColumns; i++) {
			columns.add(rsMetaData.getColumnName(i));
		}
		return columns;
	}

	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	protected ENTITY parseRow(final ResultSet resultSet) throws SQLException {
		throw new UnsupportedOperationException(
				"this method should be overriden in particular *Impl class or use alternative "
						+ "com.itacademy.jd2.bs.ta.dao.jdbc.AbstractDaoImpl.parseRow(ResultSet, List<String>)");
	};

	protected ENTITY parseRow(final ResultSet resultSet, final Set<String> columns) throws SQLException {
		// this method allows to specify in particular DAO the parser which
		// accepts list of columns. but by default it will fall back to
		// com.itacademy.jd2.dz.cardealer.dao.jdbc.AbstractDaoImpl.parseRow(ResultSet)
		return parseRow(resultSet);
	};

	protected void appendSort(final AbstractFilter filter, final StringBuilder sql) {
		final String sortColumn = filter.getSortColumn();
		final Boolean sortOrder = filter.getSortOrder();

		if (sortColumn != null) {
			sql.append(String.format(" order by %s", sortColumn));

			if (sortOrder != null) {
				sql.append(" ");
				sql.append(sortOrder ? "asc" : "desc");
			}
		}
	}

	protected void appendPaging(final AbstractFilter filter, final StringBuilder sql) {
		final Integer limit = filter.getLimit();
		final Integer offset = filter.getOffset();

		if (limit != null) {
			sql.append(" limit " + limit);
		}

		if (offset != null) {
			sql.append(" offset " + offset);
		}
	}

	protected abstract String getTableName();

}
