package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;
import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.builder.DeleteBuilder;
import com.lifeonwalden.forestbatis.builder.InsertBuilder;
import com.lifeonwalden.forestbatis.builder.SelectBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateBuilder;
import com.lifeonwalden.forestbatis.exception.DataAccessException;
import com.lifeonwalden.forestbatis.meta.ColumnMeta;
import com.lifeonwalden.forestbatis.meta.OrderBy;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.ParameterHandler;
import com.lifeonwalden.forestbatis.result.RecordHandler;
import com.lifeonwalden.forestbatis.result.ReturnColumnHanlder;
import com.lifeonwalden.forestbatis.result.StreamResultSetCallback;
import com.lifeonwalden.forestbatis.builder.DeleteSqlBuilder;
import com.lifeonwalden.forestbatis.builder.InsertSqlBuilder;
import com.lifeonwalden.forestbatis.builder.SelectSqlBuilder;
import com.lifeonwalden.forestbatis.builder.UpdateSqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCommonMapper<T> implements CommonMapper<T> {
    private static Logger logger = LoggerFactory.getLogger(AbstractCommonMapper.class);

    protected abstract Config getConfig();

    protected abstract Connection getConnection();

    protected abstract void releaseConnection(Connection connection);

    protected abstract InsertBuilder<T> getBaseInsertBuilder();

    protected abstract DeleteBuilder<T> getBaseDeleteBuilder();

    protected abstract UpdateBuilder<T> getBaseUpdateBuilder();

    protected abstract SelectBuilder<T> getFullSelectBuilder();

    protected abstract SelectBuilder<T> getBaseSelectBuilder();

    protected abstract RecordHandler<T> getBaseRecordHandler();

    protected abstract ParameterHandler getParameterHandler();

    protected abstract ReturnColumnHanlder getReturnColumnHandler();

    protected abstract TableMeta getTable();

    @Override
    public List<T> selectAll() {
        return select(null, getFullSelectBuilder(), getBaseRecordHandler());
    }

    @Override
    public List<T> selectAll(ColumnMeta... excludeReturnColumnList) {
        if (null != excludeReturnColumnList && excludeReturnColumnList.length > 0) {

            return select(null, getFullSelectBuilder().excludeReturnColumn(Arrays.asList(excludeReturnColumnList)), getBaseRecordHandler());
        } else {
            throw new RuntimeException("Invalid parameter. The exclude return column list should not be empty.");
        }
    }

    @Override
    public List<T> selectAll(OrderBy... orderByList) {
        if (null != orderByList && orderByList.length > 0) {

            return select(null, getFullSelectBuilder().overrideOrder(Arrays.asList(orderByList)), getBaseRecordHandler());
        } else {
            throw new RuntimeException("Invalid parameter. The order by list should not be empty.");
        }
    }

    @Override
    public void selectAll(StreamResultSetCallback<T> streamResultSetCallback) {

    }

    @Override
    public void selectAll(StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {

    }

    @Override
    public List<T> select(T param) {
        return select(param, getBaseSelectBuilder(), getBaseRecordHandler());
    }

    @Override
    public void select(T param, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, getBaseSelectBuilder(), getBaseRecordHandler(), streamResultSetCallback, getConfig().getFetchSize());
    }

    @Override
    public void select(T param, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        select(param, getBaseSelectBuilder(), getBaseRecordHandler(), streamResultSetCallback, fetchSize);
    }

    @Override
    public List<T> select(T param, SelectSqlBuilder<T> selectSqlBuilder) {
        return select(param, selectSqlBuilder, getBaseRecordHandler());
    }

    @Override
    public void select(T param, SelectSqlBuilder<T> selectSqlBuilder, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, selectSqlBuilder, getBaseRecordHandler(), streamResultSetCallback, getConfig().getFetchSize());
    }

    @Override
    public void select(T param, SelectSqlBuilder<T> selectSqlBuilder, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        select(param, selectSqlBuilder, getBaseRecordHandler(), streamResultSetCallback, fetchSize);
    }

    @Override
    public List<T> select(T param, RecordHandler<T> recordHandler) {
        return select(param, getBaseSelectBuilder(), recordHandler);
    }

    @Override
    public void select(T param, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, getBaseSelectBuilder(), recordHandler, streamResultSetCallback, getConfig().getFetchSize());
    }

    @Override
    public void select(T param, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        select(param, getBaseSelectBuilder(), recordHandler, streamResultSetCallback, fetchSize);
    }

    @Override
    public List<T> select(T param, SelectSqlBuilder<T> selectSqlBuilder, RecordHandler<T> recordHandler) {
        Connection connection = getConnection();
        StatementInfo statementInfo = selectSqlBuilder.build(param);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, param);
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            ResultSet rs = preparedStatement.executeQuery();

            List<ColumnInfo> columnInfoList;
            if (!selectSqlBuilder.isRuntimeChangeable()) {
                Optional<List<ColumnInfo>> _columnInfoSet = statementInfo.getReturnColumns();
                if (_columnInfoSet.isPresent()) {
                    columnInfoList = _columnInfoSet.get();
                } else {
                    columnInfoList = getReturnColumnHandler().setupReturnColumn(rs);
                    statementInfo.setReturnColumns(Optional.of(columnInfoList));
                }
            } else {
                columnInfoList = getReturnColumnHandler().setupReturnColumn(rs);
            }

            List<T> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(recordHandler.convert(rs, columnInfoList));
            }

            return resultList;
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void select(T param, SelectSqlBuilder<T> selectSqlBuilder, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, selectSqlBuilder, recordHandler, streamResultSetCallback, getConfig().getFetchSize());
    }

    @Override
    public void select(T param, SelectSqlBuilder<T> selectSqlBuilder, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        Connection connection = getConnection();
        StatementInfo statementInfo = selectSqlBuilder.build(param);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            preparedStatement.setFetchSize(fetchSize);
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, param);
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            ResultSet rs = preparedStatement.executeQuery();

            List<ColumnInfo> columnInfoList;
            if (!selectSqlBuilder.isRuntimeChangeable()) {
                Optional<List<ColumnInfo>> _columnInfoList = statementInfo.getReturnColumns();
                if (_columnInfoList.isPresent()) {
                    columnInfoList = _columnInfoList.get();
                } else {
                    columnInfoList = getReturnColumnHandler().setupReturnColumn(rs);
                    statementInfo.setReturnColumns(Optional.of(columnInfoList));
                }
            } else {
                columnInfoList = getReturnColumnHandler().setupReturnColumn(rs);
            }

            while (rs.next()) {
                if (!streamResultSetCallback.process(recordHandler.convert(rs, columnInfoList))) {
                    break;
                }
            }
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Integer updateWithQuery(T value) {
        return updateWithQuery(value, getBaseUpdateBuilder());
    }

    @Override
    public Integer updateWithQuery(T value, UpdateSqlBuilder<T> updateSqlBuilder) {
        Connection connection = getConnection();
        StatementInfo statementInfo = updateSqlBuilder.build(value);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, value);
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public int[] updateWithQuery(List<T> valueList, UpdateSqlBuilder<T> updateSqlBuilder) {
        if (updateSqlBuilder.isRuntimeChangeable()) {
            throw new RuntimeException("Please use static builder for batch operation");
        }

        Connection connection = getConnection();
        try {
            StatementInfo statementInfo = updateSqlBuilder.build();
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                for (T value : valueList) {
                    getParameterHandler().set(statementInfo, preparedStatement, value);
                    preparedStatement.addBatch();
                }
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            return preparedStatement.executeBatch();
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Integer deleteWithQuery(T param) {
        return deleteWithQuery(param, getBaseDeleteBuilder());
    }

    @Override
    public Integer deleteWithQuery(T param, DeleteSqlBuilder<T> deleteSqlBuilder) {
        Connection connection = getConnection();
        StatementInfo statementInfo = deleteSqlBuilder.build(param);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, param);
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public int[] deleteWithQuery(List<T> paramList, DeleteSqlBuilder<T> deleteSqlBuilder) {
        if (deleteSqlBuilder.isRuntimeChangeable()) {
            throw new RuntimeException("Please use static builder for batch operation");
        }

        Connection connection = getConnection();
        StatementInfo statementInfo = deleteSqlBuilder.build();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                for (T param : paramList) {
                    getParameterHandler().set(statementInfo, preparedStatement, param);
                    preparedStatement.addBatch();
                }
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            return preparedStatement.executeBatch();
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Integer insert(T value) {
        return insert(value, getBaseInsertBuilder());
    }

    @Override
    public Integer insert(T value, InsertSqlBuilder<T> insertSqlBuilder) {
        Connection connection = getConnection();
        StatementInfo statementInfo = insertSqlBuilder.build(value);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, value);
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public int[] insert(List<T> valueList) {
        return insert(valueList, getBaseInsertBuilder());
    }

    @Override
    public int[] insert(List<T> valueList, InsertSqlBuilder<T> insertSqlBuilder) {
        if (insertSqlBuilder.isRuntimeChangeable()) {
            throw new RuntimeException("Please use static builder for batch operation");
        }

        Connection connection = getConnection();
        StatementInfo statementInfo = insertSqlBuilder.build();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                for (T value : valueList) {
                    getParameterHandler().set(statementInfo, preparedStatement, value);
                    preparedStatement.addBatch();
                }
            } else if (logger.isDebugEnabled()) {
                logger.debug(statementInfo.getSql());
            }

            return preparedStatement.executeBatch();
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Integer truncate() {
        Connection connection = getConnection();
        TableMeta tableMeta = getTable();
        try {
            if (getConfig().isWithSchema()) {
                return connection.createStatement().executeUpdate("truncate table ".concat(getConfig().getSchema()).concat(".").concat(tableMeta.getName()));
            } else {
                return connection.createStatement().executeUpdate("truncate table ".concat(tableMeta.getName()));
            }
        } catch (SQLException e) {
            releaseConnection(connection);
            throw new DataAccessException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
