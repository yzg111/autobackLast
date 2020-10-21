package com.doc.Manager.JDBCManager;


import com.doc.Manager.JDBCManager.reposity.JDbcComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.doc.Manager.JDBCManager 于2020/9/28 由Administrator 创建 .
 */
@Component("JdbcComServiceImpl")
public class JdbcComServiceImpl implements JDbcComService {
    @Autowired
    private JDBCManager jdbcManager;

    private Connection connection=null;

    @Override
    public Boolean TestConnect(String driver, String url, String username, String password)  {
        Connection conn=null;
        Boolean flag=false;
        try {
            Class.forName(driver);
            conn =  DriverManager.getConnection(url, username, password);
            if (conn!=null){
                flag=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public void SetConnection(String driver, String url, String username, String password){
        this.connection=jdbcManager.getConnByConfig(driver,url,username,password);
    }

    public List<String> getTablesName(){
        return jdbcManager.getTableNameByCon(this.connection);
    }

    @Override
    public List<String> getTablesView() {
        return jdbcManager.getTableViewByCon(this.connection);
    }

    public ResultSet selectDataBysql(String sql) throws SQLException {
        //2.创建statement类对象，用来执行SQL语句！！
         Statement statement = this.connection.createStatement();
        //要执行的SQL语句
//        String sql = "select * from emp";
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    @Override
    public ResultSet selectDataByTablename(String tablename) throws SQLException {
        //2.创建statement类对象，用来执行SQL语句！！
        Statement statement = this.connection.createStatement();
        //要执行的SQL语句
        String sql = "select * from "+tablename;
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    @Override
    public List<String> GetClounmsByTableName(String tablename) throws SQLException {
        List<String> results=new ArrayList<>();
        //2.创建statement类对象，用来执行SQL语句！！
        Statement statement = this.connection.createStatement();
        //要执行的SQL语句
        String sql = "select * from "+tablename;
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        //获取bai结果集元数据
        ResultSetMetaData rsmd=rs.getMetaData();
        //总列数
        int size=rsmd.getColumnCount();
        //打印bai列名
        for(int i=1;i<=size;i++)
        {
            System.out.println(rsmd.getColumnName(i));
            results.add(rsmd.getColumnName(i));
        }
        return results;
    }

    @Override
    public List<String> GetClounmsBySql(String sql) throws SQLException {
        List<String> results=new ArrayList<>();
        //2.创建statement类对象，用来执行SQL语句！！
        Statement statement = this.connection.createStatement();
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        //获取bai结果集元数据
        ResultSetMetaData rsmd=rs.getMetaData();
        //总列数
        int size=rsmd.getColumnCount();
        //打印表列名
        for(int i=1;i<=size;i++)
        {
            System.out.println(rsmd.getColumnName(i));
            results.add(rsmd.getColumnName(i));
        }
        return results;
    }


}
