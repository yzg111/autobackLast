package com.doc.Manager.JDBCManager;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.doc.Manager 于2020/9/28 由Administrator 创建 .
 *利用 JdbcTemplate来链接数据库查询数据（排除）
 * 利用原生链接数据库的代码来链接数据库
 */
@Component("JDBCManager")
public class JDBCManager {

    public Connection getConnByConfig(String driver, String url, String username, String password){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, username, password);
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
            conn = (Connection) DriverManager.getConnection(url, username, password);
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

    public List<String> getTableNameByCon(Connection con) {
        List<String> tablenames=new ArrayList<>();
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null,
                    new String[] { "TABLE" });
            while (rs.next()) {
                System.out.println("表名：" + rs.getString(3));
                System.out.println("表所属用户名：" + rs.getString(2));
                System.out.println("------------------------------");
                tablenames.add(rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            return tablenames;
        }

    }

    public List<String> getTableViewByCon(Connection con) {
        List<String> tablenames=new ArrayList<>();
        try {
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null,
                    new String[] { "VIEW" });
            while (rs.next()) {
                System.out.println("视图名：" + rs.getString(3));
                System.out.println("表所属用户名：" + rs.getString(2));
                System.out.println("------------------------------");
                tablenames.add(rs.getString(3));
            }
            con.close();
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            return tablenames;
        }

    }
}
