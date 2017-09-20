/*
 *
 * This file is generated under this project, "kr.re.etri.fncp.playnet.launcher".
 *
 * Date  : 2015. 8. 4. 오후 3:35:10
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package kr.ymtech.ojt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @since 2015. 8. 4.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public interface IRowMapperSetter<T> {

    /**
     * 
     * @param rs
     *            검색된 데이타 Row
     * @param rowNum
     *            Row 번호
     * @return
     * @throws SQLException
     *
     * @since 2015. 8. 4.
     */
    public T set(ResultSet rs, int rowNum) throws SQLException;

}
