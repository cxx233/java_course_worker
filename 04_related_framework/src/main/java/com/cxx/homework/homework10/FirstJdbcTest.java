package com.cxx.homework.homework10;

import java.sql.*;
import java.util.UUID;

/**
 * @ClassName FirstJdbcTest
 * @Description 使用 JDBC 原生接口，实现数据库的增删改查操作。
 * @Date 2022/4/4 10:52
 */
public class FirstJdbcTest {
    /**
     * 测试的数据库
     * <pre>
     *     SET FOREIGN_KEY_CHECKS=0;
     *
     * -- ----------------------------
     * -- Table structure for payment
     * -- ----------------------------
     * DROP TABLE IF EXISTS `payment`;
     * CREATE TABLE `payment` (
     * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
     * `serial` varchar(200) DEFAULT '',
     * PRIMARY KEY (`id`)
     * ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
     * </pre>
     *
     * @param args
     */
    public static void main(String[] args)  {
        String jdbcName = "com.mysql.jdbc.Driver";
        String dbUserName = "root";
        String dbPassword = "root";

        String dbUrl = "jdbc:mysql://localhost:3306/db2019?" + "useUnicode=true&characterEncoding" +
                "=UTF8";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(jdbcName);
             con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);

             stmt = con.createStatement();

            // r
            String querySql = "select * from payment";
            rs = stmt.executeQuery(querySql);
            while (rs.next()) {
                System.out.printf("id:%s, serial:%s\r\n", rs.getInt("id"), rs.getString("serial"));
            }
            deleteMethod(con,stmt);
//            insertMethod(con,stmt);
            updateMethod(con,stmt);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){//关闭记录集
                try{
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(stmt!=null){//关闭说明对象
                try{
                    stmt.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

            if(con!=null){//关闭连接，就像关门一样，先关里面的，最后关最外面的
                try{
                    con.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }

            }
        }

    }

    private static void updateMethod(Connection con, Statement stmt) throws SQLException {
        long startDateTimeStamp = System.currentTimeMillis();
        System.out.println("开始时间戳：" + startDateTimeStamp);
        String updateSql = "update  `payment` set serial = 'update'  WHERE id = 1";
        stmt.executeUpdate(updateSql);
        long endTimeStamp = System.currentTimeMillis();
        System.out.println("结束时间戳：" + endTimeStamp);
    }

    private static void deleteMethod(Connection con, Statement stmt) throws SQLException {
        long startDateTimeStamp = System.currentTimeMillis();
        System.out.println("开始时间戳：" + startDateTimeStamp);
        String deleteSql = "DELETE FROM `payment` WHERE id > 100";
        stmt.execute(deleteSql);
        long endTimeStamp = System.currentTimeMillis();
        System.out.println("结束时间戳：" + endTimeStamp);
    }

    private static void insertMethod(Connection con, Statement stmt) throws SQLException {
        long startDateTimeStamp = System.currentTimeMillis();
        System.out.println("开始时间戳：" + startDateTimeStamp);
        String insertSqlFormat = "INSERT INTO `payment` VALUES ('%s','%s')";
        int i = 0;
        while (i < 10000000) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String insertSql = String.format(insertSqlFormat, i + 100, uuid);

                stmt.execute(insertSql);

            i ++;
        }

        long endTimeStamp = System.currentTimeMillis();
        System.out.println("结束时间戳：" + endTimeStamp);

    }
}
