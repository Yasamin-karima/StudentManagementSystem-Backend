package dataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SQLConnect {
    private static SQLConnect sqlConnect;
    private static final String url = "jdbc:sqlite:src/main/java/dataBase/data.db";
    private static final Connection conn;
    private static final Statement create;

    static {
        try {
            conn = DriverManager.getConnection(url);
            create = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("failed to load SQLConnect class, creating connection error");
            throw new RuntimeException(e);
        }
    }

    private SQLConnect() {}
    public static SQLConnect getInstance(){
        if (sqlConnect == null){
            sqlConnect = new SQLConnect();
        }
        return sqlConnect;
    }

    public List<String> query (String sql){
        List<String> result = new ArrayList<>();

        try (ResultSet rs = create.executeQuery(sql)){
            int columnCount = rs.getMetaData().getColumnCount();
            StringBuilder stringBuilder = new StringBuilder();
            while (rs.next()){
                for (int i = 1; i <= columnCount; i++) {
                    if (i == columnCount) {
                        stringBuilder.append(rs.getObject(i).toString());
                    } else {
                        stringBuilder.append(rs.getObject(i).toString()).append("@");
                    }
                }
                result.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        } catch (SQLException e) {
            System.err.println("error in query method");
            throw new RuntimeException(e);
        }
        return result;
    }

    public void insert (String sql){
        try {
            create.execute(sql);
        } catch (SQLException e) {
            System.err.println("error in query method");
            throw new RuntimeException(e);
        }
    }

    public void delete (String sql){
        try {
            create.execute(sql);
        } catch (SQLException e) {
            System.err.println("error in query method");
            throw new RuntimeException(e);
        }
    }

    public void update (String sql){
        try {
            create.execute(sql);
        } catch (SQLException e) {
            System.err.println("error in query method");
            throw new RuntimeException(e);
        }
    }

}
