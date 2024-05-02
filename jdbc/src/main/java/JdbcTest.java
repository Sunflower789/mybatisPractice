import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {
    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/data_1?useServerPrepStmts=true";
    // 数据库用户和密码
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) throws SQLException {
        // DQL
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try {
            // 注册数据库驱动类，mysql5后可忽略
            //Class.forName(JDBC_DRIVER);
            // 获取数据库连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 设置手动提交事务，默认是自动提交
            //conn.setAutoCommit(false);
            // 定义sql语句
            String sql = "select * from tb_user where name = ?;";
            // 获取预处理
            preparedStatement = conn.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1,"zhangsan");
            resultSet = preparedStatement.executeQuery();

            // 处理结果集(获取字段并封装对象)
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String userLevel = resultSet.getString("user_level");
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setAge(age);
                user.setUserLevel(userLevel);
                userList.add(user);
            }
            System.out.println(userList);
            //conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //conn.rollback();
        } finally {
            // 关闭资源
            try {
                if(resultSet != null){
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

/*
        // DML
        Connection conn = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        Integer resultSet = null;

        Integer age = 27;
        String name = "wangwu";
        String userLevel = "A2";

        try {
            // 注册数据库驱动类，mysql5后可忽略
            //Class.forName(JDBC_DRIVER);
            // 获取数据库连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 定义sql语句
            String sql = "insert into `tb_user`(age,name,user_level) VALUES(?,?,?);";
            // 执行sql
//            statement = conn.createStatement();
//            resultSet = statement.executeUpdate(sql);
            // 预编译后执行
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,age);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,userLevel);
            resultSet = preparedStatement.executeUpdate();
            // 处理结果集
            if(resultSet > 0){
                System.out.println(resultSet);
            }else {
                System.out.println("fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
//                if (statement != null) {
//                    statement.close();
//                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
*/


    }

}
