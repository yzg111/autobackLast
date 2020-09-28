package com.doc.Manager.JDBCManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * com.doc.Manager 于2020/9/28 由Administrator 创建 .
 *利用 JdbcTemplate来链接数据库查询数据（排除）
 * 利用原生链接数据库的代码来链接数据库
 */
public class JDBCManager {

    public Connection getConnByConfig(String driver,String url,String username,String password){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getConn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/renshoua?useUnicode=true&characterEncoding=utf8";
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConn(Connection conn) {

        try {
            if (conn != null && !conn.isClosed()) {

                conn.close();

            }
        } catch (SQLException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
