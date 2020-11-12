package com.doc.Entity.MogoEntity.ComEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回分页数据类
 * 
 * @author hk
 * 
 *         2012-10-26 下午8:23:15
 */
public class Pagination<T> {

    /**
     * 一页数据默认20条
     */
    private int     count = 20;
    /**
     * 当前页码
     */
    private int     page;

    /**
     * 上一页
     */
    private int     upPage;

    /**
     * 下一页
     */
    private int     nextPage;
    /**
     * 一共有多少条数据
     */
    private long    total;

    /**
     * 一共有多少页
     */
    private int     totalPage;
    /**
     * 数据集合
     */
    private List<T> results;

    /**
     * 获取第一条记录位置
     * 
     * @return
     */
    public int getFirstResult() {
        return (this.getPage() - 1) * this.getCount();
    }

    /**
     * 获取最后记录位置
     * 
     * @return
     */
    public int getLastResult() {
        if (this.getPage() >= this.totalPage)
            return (int) this.total;
        return this.getPage() * this.getCount();
    }

    /**
     * 计算一共多少页
     */
    public void setTotalPage() {
        this.totalPage = (int) ((this.total % this.count > 0) ? (this.total / this.count + 1)
            : this.total / this.count);
        if (this.page > this.totalPage) {
            this.page = this.totalPage;
        }
    }

    /**
     * 设置 上一页
     */
    public void setUpPage() {
        this.upPage = (this.page > 1) ? this.page - 1 : this.page;
    }

    /**
     * 设置下一页
     */
    public void setNextPage() {
        if (this.page < this.totalPage) {
            this.nextPage = this.page + 1;
        } else if (this.page == this.totalPage) {
            this.nextPage = this.page;
        } else {
            this.nextPage = this.totalPage;
        }
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getUpPage() {
        return upPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count <= 0) {
            this.count = 10;
            return;
        }
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long totalCount2) {
        this.total = totalCount2;
    }

    public List<T> getResults() {
        return results == null ? new ArrayList<T>(0) : results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public Pagination(int page, int count, long total) {
        this.setCount(count);
        this.setTotal(total);
        this.setTotalPage();// 设置总共页数
        this.setPage(page);
        this.setUpPage();// 设置上一页
        this.setNextPage();// 设置下一页
    }

    public Pagination(){

    }

    /**
     * 处理查询后的结果数据
     * 
     * @param items
     *            查询结果集
     * @param count
     *            总数
     */
    public void build(List<T> items) {
        this.setResults(items);
        this.setTotalPage();
        this.setNextPage();
        this.setUpPage();
    }
}
