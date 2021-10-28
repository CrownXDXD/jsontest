import java.sql.*;

public class MySqlUtil {


  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost:8889/160063D?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

  // 数据库的用户名与密码，需要根据自己的设置
  static final String USER = "root";
  static final String PASS = "root";

  private static void insertData(){
    Connection conn = null;
    Statement stmt = null;
    // 注册 JDBC 驱动
    try {
      Class.forName(JDBC_DRIVER);
      System.out.println("connect to database..");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
      System.out.println(" ...");
      stmt = conn.createStatement();
      String sql = "INSERT INTO student " +
        "VALUES (100, 'C++', 'Li', 18)";
      stmt.executeUpdate(sql);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }







}
