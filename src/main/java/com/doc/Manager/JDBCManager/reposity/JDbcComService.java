package com.doc.Manager.JDBCManager.reposity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * com.doc.Manager.JDBCManager.reposity 于2020/9/28 由Administrator 创建 .
 */
public interface JDbcComService {
    //测试连接数据库
    public Boolean TestConnect(String driver,String url,String username,String password);
    //设置链接的数据库信息
    public void SetConnection(String driver,String url,String username,String password);
    //获取一个数据库中所有的表名称
    public List<String> getTablesName();
    //获取一个数据库中所有的视图名称
    public List<String> getTablesView();
    //根据sql查询数据
    public ResultSet selectDataBysql(String sql) throws SQLException;
    //根据表名查询数据
    public ResultSet selectDataByTablename(String tablename) throws SQLException;

    //根据表名查询出表里面的字段
    public List<String> GetClounmsByTableName(String tablename) throws SQLException;

    //根据sql语句查询出相应结果集的字段数据
    public List<String> GetClounmsBySql(String sql) throws SQLException;
}
