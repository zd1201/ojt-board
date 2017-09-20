/*
 *
 * This file is generated under this project, "kr.re.etri.fncp.playnet.launcher".
 *
 * Date  : 2015. 8. 4. 오후 3:43:49
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package kr.ymtech.ojt.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

/**
 * 단일개체를 조회하는 경우 예외를 발생시키는 메소드를 Override 한 클래스.
 * 
 * @since 2015. 8. 4.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 * @see #queryForObject(java.lang.String, org.springframework.jdbc.core.RowMapper)
 * @see #queryForObject(java.lang.String, org.springframework.jdbc.core.RowMapper, java.lang.Object[])
 * @see #queryForObject(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowMapper)
 * @see #queryForObject(java.lang.String, java.lang.Object[], int[], org.springframework.jdbc.core.RowMapper)
 * 
 */
public class CustomJdbcTemplate extends JdbcTemplate {

    /**
     * 
     * @since 2015. 8. 4.
     */
    public CustomJdbcTemplate() {
    }

    /**
     * @param dataSource
     * @since 2015. 8. 4.
     */
    public CustomJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * @param dataSource
     * @param lazyInit
     * @since 2015. 8. 4.
     */
    public CustomJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    /**
     * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Object[], int[],
     *      org.springframework.jdbc.core.RowMapper)
     */
    @Override
    public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {

        List<T> results = query(sql, args, argTypes, new RowMapperResultSetExtractor<T>(rowMapper, 1));

        return requiredSingleResult(results);
    }

    /**
     * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String, java.lang.Object[],
     *      org.springframework.jdbc.core.RowMapper)
     */
    @Override
    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));

        return requiredSingleResult(results);
    }

    /**
     * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String,
     *      org.springframework.jdbc.core.RowMapper)
     */
    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = query(sql, rowMapper);

        return requiredSingleResult(results);
    }

    /**
     * @see org.springframework.jdbc.core.JdbcTemplate#queryForObject(java.lang.String,
     *      org.springframework.jdbc.core.RowMapper, java.lang.Object[])
     */
    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        List<T> results = query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));

        return requiredSingleResult(results);
    }

    private <T> T requiredSingleResult(List<T> results) {
        return results != null && results.size() > 0 ? results.get(0) : null;
    }
}
