/*
 *
 * This file is generated under this project, "kr.re.etri.fncp.playnet.launcher".
 *
 * Date  : 2015. 8. 4. 오후 3:32:10
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package kr.ymtech.ojt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import kr.ymtech.ojt.dao.CustomJdbcTemplate;
import kr.ymtech.ojt.dao.IGenericDao;
import kr.ymtech.ojt.dao.IRowMapperSetter;
import open.commons.Result;

/**
 * 
 * @since 2015. 8. 4.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public abstract class GenericDaoImpl implements IGenericDao {

    protected Log logger = LogFactory.getLog(getClass());

    private DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    private ReloadableResourceBundleMessageSource querySource;

    /**
     * 
     * @since 2015. 8. 4.
     */
    public GenericDaoImpl() {
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * 
     * @param sql
     * @param setter
     *            {@link PreparedStatement}가 생성된 후 조건절 생성
     * @return
     *
     * @since 2014. 9. 5.
     */
    protected Result<Integer> execute(String sql, PreparedStatementSetter setter) {

        Result<Integer> result = new Result<Integer>();

        try {
            int count = getJdbcTemplate().execute(new ConnectionCallback<Integer>() {
                @Override
                public Integer doInConnection(Connection con) throws SQLException, DataAccessException {
                    int count = 0;
                    PreparedStatement stmt = null;
                    try {
                        stmt = con.prepareStatement(sql);

                        setter.setValues(stmt);

                        count = stmt.executeUpdate();
                    } catch (Exception e) {
                        result.setMessage(e.getLocalizedMessage());

                        logger.warn(e.getLocalizedMessage(), e);
                    } finally {
                        if (stmt != null) {
                            stmt.close();
                        }
                    }

                    return count;
                }
            });

            result.setData(count);
            if (count > 0) {
                result.setMessage("OK");
            } else if (result.getMessage() == null) {
                result.setMessage("변경된 데이타가 없습니다.");
            }

            result.setResult(count > 0);

        } catch (Exception e) {
            logger.warn("query: " + sql + e);

            result.setData(0);
            result.setMessage(e.getLocalizedMessage());
        }

        return result;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            // jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate = new CustomJdbcTemplate(dataSource);
        }

        return jdbcTemplate;
    }

    /**
     * 하나의 튜플만 반환한다.
     * 
     * @param sql
     * @param params
     * @param setter
     * @return
     *
     * @since 2014. 9. 5.
     */
    protected <T> Result<T> getObject(String sql, Object[] params, IRowMapperSetter<T> setter) {
        Result<T> result = new Result<T>();

        try {
            T object = queryForObject(sql, params, setter);

            result.setData(object);
            result.setMessage(object != null ? "OK" : "검색결과가 없습니다.");
            result.setResult(true);
        } catch (Exception e) {
            logger.warn("query: " + sql, e);

            result.setMessage(e.getLocalizedMessage());
        }

        return result;
    }

    /**
     * 하나의 튜플만 반환한다.
     * 
     * @param sql
     * @param params
     * @param setter
     * @return
     *
     * @since 2014. 9. 5.
     */
    protected <T> Result<List<T>> getObjectList(String sql, Object[] params, IRowMapperSetter<T> setter) {
        Result<List<T>> result = new Result<List<T>>();

        try {
            List<T> objects = query(sql, params, setter);

            result.setData(objects);
            result.setResult(objects != null);

        } catch (Exception e) {
            logger.warn("query: " + sql, e);

            result.setMessage(e.getLocalizedMessage());
        }

        return result;
    }

    public String getQuery(String name) throws NoSuchMessageException {
        return querySource.getMessage(name, null, null);
    }

    public String getQuery(String name, Object[] args, Locale locale) throws NoSuchMessageException {
        return querySource.getMessage(name, args, locale);
    }

    public ReloadableResourceBundleMessageSource getQuerySource() {
        return querySource;
    }

    protected <T> List<T> query(String sql, IRowMapperSetter<T> setter) {
        return getJdbcTemplate().query(sql, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return setter.set(rs, rowNum);
            }
        });
    }

    protected <T> List<T> query(String sql, Object[] params, IRowMapperSetter<T> setter) {
        return getJdbcTemplate().query(sql, params == null ? new Object[] {} : params, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return setter.set(rs, rowNum);
            }
        });
    }

    protected <T> T queryForObject(String sql, IRowMapperSetter<T> setter) {
        return getJdbcTemplate().queryForObject(sql, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return setter.set(rs, rowNum);
            }
        });
    }

    protected <T> T queryForObject(String sql, Object[] params, IRowMapperSetter<T> setter) {
        return getJdbcTemplate().queryForObject(sql, params == null ? new Object[] {} : params, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return setter.set(rs, rowNum);
            }
        });
    }

    /**
     * 
     * @param dataSource
     *
     * @since 2015. 8. 4.
     */
    @Autowired
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 
     * @param querySource
     *
     * @since 2015. 8. 4.
     */
    @Autowired
    @Qualifier("querySource")
    public void setQuerySource(ReloadableResourceBundleMessageSource querySource) {
        this.querySource = querySource;
    }
    
    public StringBuffer setSqlWhereIn(String columnName, Collection<String> data, StringBuffer buf, String concatenator) {

        buf.append(concatenator);

        buf.append(' ');
        buf.append(columnName);
        buf.append(' ');
        buf.append(" IN (");
        buf.append('?');

        for (int i = 1; i < data.size(); i++) {
            buf.append(",?");
        }

        buf.append(')');

        return buf;
    }
    
    public StringBuffer setSqlWhereNotIn(String columnName, Collection<String> data, StringBuffer buf, String concatenator) {
    	
    	buf.append(concatenator);
    	
    	buf.append(' ');
    	buf.append(columnName);
    	buf.append(' ');
    	buf.append(" NOT IN (");
    	buf.append('?');
    	
    	for (int i = 1; i < data.size(); i++) {
    		buf.append(",?");
    	}
    	
    	buf.append(')');
    	
    	return buf;
    }

}
