package com.lifeonwalden.forestbatis.mapper;

import com.lifeonwalden.forestbatis.bean.ColumnInfo;
import com.lifeonwalden.forestbatis.bean.Config;
import com.lifeonwalden.forestbatis.bean.StatementInfo;
import com.lifeonwalden.forestbatis.exception.DataAccessException;
import com.lifeonwalden.forestbatis.meta.TableMeta;
import com.lifeonwalden.forestbatis.result.ParameterHandler;
import com.lifeonwalden.forestbatis.result.RecordHandler;
import com.lifeonwalden.forestbatis.result.ReturnColumnHanlder;
import com.lifeonwalden.forestbatis.result.StreamResultSetCallback;
import com.lifeonwalden.forestbatis.sql.DeleteBuilder;
import com.lifeonwalden.forestbatis.sql.InsertBuilder;
import com.lifeonwalden.forestbatis.sql.SelectBuilder;
import com.lifeonwalden.forestbatis.sql.UpdateBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCommonMapper<T> implements CommonMapper<T> {
    private final int FETCH_SIZE = 2048;

    protected abstract Config getConfig();

    protected abstract Connection getConnection();

    protected abstract void releaseConnection();

    protected abstract InsertBuilder<T> getBaseInsertBuilder();

    protected abstract DeleteBuilder<T> getBaseDeleteBuilder();

    protected abstract UpdateBuilder<T> getBaseUpdateBuilder();

    protected abstract SelectBuilder<T> getBaseSelectBuilder();

    protected abstract RecordHandler<T> getBaseRecordHandler();

    protected abstract ParameterHandler getParameterHandler();

    protected abstract ReturnColumnHanlder getReturnColumnHandler();

    protected abstract TableMeta getTable();

    @Override
    public List<T> select(T param) {
        return select(param, getBaseSelectBuilder(), getBaseRecordHandler());
    }

    @Override
    public void select(T param, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, getBaseSelectBuilder(), getBaseRecordHandler(), streamResultSetCallback, FETCH_SIZE);
    }

    @Override
    public void select(T param, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        select(param, getBaseSelectBuilder(), getBaseRecordHandler(), streamResultSetCallback, fetchSize);
    }

    @Override
    public List<T> select(T param, SelectBuilder<T> selectBuilder) {
        return select(param, selectBuilder, getBaseRecordHandler());
    }

    @Override
    public void select(T param, SelectBuilder<T> selectBuilder, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, selectBuilder, getBaseRecordHandler(), streamResultSetCallback, FETCH_SIZE);
    }

    @Override
    public void select(T param, SelectBuilder<T> selectBuilder, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        select(param, selectBuilder, getBaseRecordHandler(), streamResultSetCallback, fetchSize);
    }

    @Override
    public List<T> select(T param, RecordHandler<T> recordHandler) {
        return select(param, getBaseSelectBuilder(), recordHandler);
    }

    @Override
    public void select(T param, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, getBaseSelectBuilder(), recordHandler, streamResultSetCallback, FETCH_SIZE);
    }

    @Override
    public void select(T param, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        select(param, getBaseSelectBuilder(), recordHandler, streamResultSetCallback, fetchSize);
    }

    @Override
    public List<T> select(T param, SelectBuilder<T> selectBuilder, RecordHandler<T> recordHandler) {
        Connection connection = getConnection();
        StatementInfo statementInfo = selectBuilder.build(param);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, param);
            }

            ResultSet rs = preparedStatement.executeQuery();

            List<ColumnInfo> columnInfoList;
            if (!selectBuilder.isRuntimeChangeable()) {
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
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public void select(T param, SelectBuilder<T> selectBuilder, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback) {
        select(param, selectBuilder, recordHandler, streamResultSetCallback, FETCH_SIZE);
    }

    @Override
    public void select(T param, SelectBuilder<T> selectBuilder, RecordHandler<T> recordHandler, StreamResultSetCallback<T> streamResultSetCallback, int fetchSize) {
        Connection connection = getConnection();
        StatementInfo statementInfo = selectBuilder.build(param);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            preparedStatement.setFetchSize(fetchSize);
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, param);
            }

            ResultSet rs = preparedStatement.executeQuery();

            List<ColumnInfo> columnInfoList;
            if (!selectBuilder.isRuntimeChangeable()) {
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
                streamResultSetCallback.process(recordHandler.convert(rs, columnInfoList));
            }
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public Integer updateWithQuery(T value) {
        return updateWithQuery(value, getBaseUpdateBuilder());
    }

    @Override
    public Integer updateWithQuery(T value, UpdateBuilder<T> updateBuilder) {
        Connection connection = getConnection();
        StatementInfo statementInfo = updateBuilder.build(value);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, value);
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public int[] updateWithQuery(List<T> valueList) {
        return updateWithQuery(valueList, getBaseUpdateBuilder());
    }

    @Override
    public int[] updateWithQuery(List<T> valueList, UpdateBuilder<T> updateBuilder) {
        if (updateBuilder.isRuntimeChangeable()) {
            throw new RuntimeException("Please use static builder for batch operation");
        }

        Connection connection = getConnection();
        try {
            StatementInfo statementInfo = updateBuilder.build();
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                for (T value : valueList) {
                    getParameterHandler().set(statementInfo, preparedStatement, value);
                    preparedStatement.addBatch();
                }
            }

            return preparedStatement.executeBatch();
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public Integer deleteWithQuery(T param) {
        return deleteWithQuery(param, getBaseDeleteBuilder());
    }

    @Override
    public Integer deleteWithQuery(T param, DeleteBuilder<T> deleteBuilder) {
        Connection connection = getConnection();
        StatementInfo statementInfo = deleteBuilder.build(param);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, param);
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public int[] deleteWithQuery(List<T> paramList) {
        return deleteWithQuery(paramList, getBaseDeleteBuilder());
    }

    @Override
    public int[] deleteWithQuery(List<T> paramList, DeleteBuilder<T> deleteBuilder) {
        if (deleteBuilder.isRuntimeChangeable()) {
            throw new RuntimeException("Please use static builder for batch operation");
        }

        Connection connection = getConnection();
        StatementInfo statementInfo = deleteBuilder.build();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                for (T param : paramList) {
                    getParameterHandler().set(statementInfo, preparedStatement, param);
                    preparedStatement.addBatch();
                }
            }

            return preparedStatement.executeBatch();
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public Integer insert(T value) {
        return insert(value, getBaseInsertBuilder());
    }

    @Override
    public Integer insert(T value, InsertBuilder<T> insertBuilder) {
        Connection connection = getConnection();
        StatementInfo statementInfo = insertBuilder.build(value);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                getParameterHandler().set(statementInfo, preparedStatement, value);
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }

    @Override
    public int[] insert(List<T> valueList) {
        return insert(valueList, getBaseInsertBuilder());
    }

    @Override
    public int[] insert(List<T> valueList, InsertBuilder<T> insertBuilder) {
        if (insertBuilder.isRuntimeChangeable()) {
            throw new RuntimeException("Please use static builder for batch operation");
        }

        Connection connection = getConnection();
        StatementInfo statementInfo = insertBuilder.build();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statementInfo.getSql());
            if (statementInfo.getProps().isPresent()) {
                for (T value : valueList) {
                    getParameterHandler().set(statementInfo, preparedStatement, value);
                    preparedStatement.addBatch();
                }
            }

            return preparedStatement.executeBatch();
        } catch (SQLException e) {
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
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
            releaseConnection();
            throw new DataAccessException(e);
        } finally {
            releaseConnection();
        }
    }
}
