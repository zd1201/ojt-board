/*
 *
 * This file is generated under this project, "kr.re.etri.fncp.playnet.launcher".
 *
 * Date  : 2015. 8. 4. 오후 3:31:32
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package kr.ymtech.ojt.dao;

import java.util.Locale;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @since 2015. 8. 4.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public interface IGenericDao extends InitializingBean {

    JdbcTemplate getJdbcTemplate();

    String getQuery(String name);

    String getQuery(String name, Object[] args, Locale locale);

    ReloadableResourceBundleMessageSource getQuerySource();
}
