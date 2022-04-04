package com.cxx.homework.homework10;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName HikaricpTest
 * @Author xujiaxi
 * @Date 2022/4/4 14:06
 */
public class HikaricpTest {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/db2019");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            con = ds.getConnection();
            // r
            String querySql = "select * from payment";
            stmt = con.prepareStatement(querySql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("id:%s, serial:%s\r\n", rs.getInt("id"), rs.getString("serial"));
            }
            deleteMethod(con,stmt);
            insertMethod(con,stmt);
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


    private static void updateMethod(Connection con, PreparedStatement pstmt) throws SQLException {
        long startDateTimeStamp = System.currentTimeMillis();
        System.out.println("updateMethod开始时间戳：" + startDateTimeStamp);
        String updateSql = "update  `payment` set serial = 'update'  WHERE id = ?";
        pstmt = con.prepareStatement(updateSql);
        pstmt.setInt(1, 2);
        pstmt.executeUpdate();
        long endTimeStamp = System.currentTimeMillis();
        System.out.println("updateMethod结束时间戳：" + endTimeStamp);
    }

    private static void deleteMethod(Connection con, PreparedStatement pstmt) throws SQLException {
        long startDateTimeStamp = System.currentTimeMillis();
        System.out.println("deleteMethod开始时间戳：" + startDateTimeStamp);
        String deleteSql = "DELETE FROM `payment` WHERE id >= ?";
        pstmt = con.prepareStatement(deleteSql);
        pstmt.setString(1, "100");
        pstmt.executeUpdate();
        long endTimeStamp = System.currentTimeMillis();
        System.out.println("deleteMethod结束时间戳：" + endTimeStamp);
    }

    private static void insertMethod(Connection con, PreparedStatement pstmt) throws SQLException {
        long startDateTimeStamp = System.currentTimeMillis();
        System.out.println("insertMethod开始时间戳：" + startDateTimeStamp);
        String insertSqlFormat = "INSERT INTO `payment` VALUES (?,?)";
        int i = 0;
        Boolean getAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        pstmt = con.prepareStatement(insertSqlFormat);
        List<String> list = new ArrayList<>(10000);
        for (int j = 0; j < 10000; j++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            list.add(uuid);
        }
        while (i < 1000000) {
            String uuid = list.get(i % 10000);
            pstmt.setInt(1, i+100);
            pstmt.setString(2, uuid);
            pstmt.addBatch();
            if (i % 10000 == 0) {
                pstmt.executeBatch();
                con.commit();
            }

            i ++;
        }
        pstmt.executeBatch();
        con.commit();
        con.setAutoCommit(getAutoCommit);

        long endTimeStamp = System.currentTimeMillis();
        System.out.println("insertMethod结束时间戳：" + endTimeStamp);

    }
}
