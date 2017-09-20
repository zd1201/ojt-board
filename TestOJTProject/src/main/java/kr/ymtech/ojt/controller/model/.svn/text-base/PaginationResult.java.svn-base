/*
 *
 * This file is generated under this project, "vulnerability-website".
 *
 * Date  : 2016. 10. 31. 오후 3:52:32
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package kr.ymtech.ojt.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since 2016. 10. 31.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class PaginationResult<T> implements IPaginationResult<List<T>> {

    private int totalCount;

    private int pageNum;

    private int itemCountPerPage;

    private List<T> items = new ArrayList<T>();

    /**
     * 
     * @since 2016. 10. 31.
     */
    public PaginationResult() {
    }

    public synchronized boolean addItem(T item) {
        if (this.items == null) {
            this.items = new ArrayList<T>();
        }

        return this.items.add(item);
    }

    /**
     * @see com.lguplus.nwvuln.web.controller.pagination.IPaginationResult#getItemCountPerPage()
     */
    @Override
    public int getItemCountPerPage() {
        return this.itemCountPerPage;
    }

    /**
     * @see com.lguplus.nwvuln.web.controller.pagination.IPaginationResult#getItems()
     */
    @Override
    public List<T> getItems() {
        return this.items;
    }

    /**
     * @see com.lguplus.nwvuln.web.controller.pagination.IPaginationResult#getPageNum()
     */
    @Override
    public int getPageNum() {
        return this.pageNum;
    }

    /**
     * @see com.lguplus.nwvuln.web.controller.pagination.IPaginationResult#getTotalCount()
     */
    @Override
    public int getTotalCount() {
        return this.totalCount;
    }

    /**
     * @param itemCountPerPage
     *            the itemCountPerPage to set
     *
     * @since 2016. 10. 31.
     */
    public void setItemCountPerPage(int itemCountPerPage) {
        this.itemCountPerPage = itemCountPerPage;
    }

    /**
     * @param items
     *            the items to set
     *
     * @since 2016. 10. 31.
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * @param pageNum
     *            the pageNum to set
     *
     * @since 2016. 10. 31.
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     *
     * @since 2016. 10. 31.
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}