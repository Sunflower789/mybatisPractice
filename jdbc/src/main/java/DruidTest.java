import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DruidTest {

    static final String DB_URL = "jdbc:mysql://localhost:3306/data_1?useServerPrepStmts=true";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        DruidDataSource dataSource = null;

        try {
            // 配置 Druid 数据源
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(dataSourceProperties());
            // 从连接池获取连接
            conn = dataSource.getConnection();
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
        } catch (Exception e) {
            e.printStackTrace();
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
    }

    // 定义连接池配置，也可以改成从配置文件中获取
    private static Properties dataSourceProperties() {
        Properties properties = new Properties();
        properties.setProperty("url", DB_URL);
        properties.setProperty("username", USER);
        properties.setProperty("password", PASS);
        // 初始化连接数量
        properties.setProperty("initialSize", "10");
        // 最大连接数
        properties.setProperty("maxActive", "20");
        // 最大等待时间
        properties.setProperty("maxWait", "3000");
        // 其他配置项，参考 Druid 文档或源码
        // properties.setProperty("validationQuery", "SELECT 1");
        // properties.setProperty("testWhileIdle", "true");
        // properties.setProperty("testOnBorrow", "false");
        // properties.setProperty("testOnReturn", "false");
        // properties.setProperty("poolPreparedStatements", "true");
        // properties.setProperty("maxOpenPreparedStatements", "20");
        // else
        return properties;
    }
}
