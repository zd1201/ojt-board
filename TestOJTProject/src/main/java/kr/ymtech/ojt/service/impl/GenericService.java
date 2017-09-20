/*
 *
 * This file is generated under this project, "vulnerability-website".
 *
 * Date  : 2016. 10. 31. 오후 3:32:47
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package kr.ymtech.ojt.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import kr.ymtech.ojt.dao.model.MemberModel;

/**
 * 
 */
public class GenericService implements InitializingBean, DisposableBean {

    protected final String SCN = getClass().getSimpleName();

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 
     * @since 2017. 08. 11.
     */
    public GenericService() {
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
    }

	public boolean registMember(MemberModel memberModel) {
		// TODO Auto-generated method stub
		return false;
	}

}
