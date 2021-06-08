package Server.utilitka;

import Client.util.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUserManager {


    public static final String GET_USER_BY_ID="SELECT * FROM " + DataBaseHandler.USER_TABLE +
            " WHERE " + DataBaseHandler.USER_ID_COLUMN +" = ?";
    public static final String GET_ID_BY_LOGIN="SELECT * FROM " + DataBaseHandler.USER_TABLE +
            " WHERE login= ?";
    public static final String INSERT_NEW_USER="INSERT INTO "+ DataBaseHandler.USER_TABLE +
            " ("+ DataBaseHandler.USER_LOGIN_COLUMN + ", " +DataBaseHandler.USER_PASSWORD_COLUMN +
            ") VALUES (?, ?)";
    public static final String CHECK_USER_PASSWORD_AND_LOGIN=GET_ID_BY_LOGIN + " AND " +
            DataBaseHandler.USER_PASSWORD_COLUMN+ " = ?";

    private DataBaseHandler dataBaseHandler;

    public DataBaseUserManager(DataBaseHandler dataBaseHandler){
        this.dataBaseHandler=dataBaseHandler;
    }

    public User getUserById(Long id) throws SQLException{
        PreparedStatement preparedStatement;
        User user=null;
        preparedStatement=dataBaseHandler.getPreparedStatement(GET_USER_BY_ID);
        preparedStatement.setLong(1,id);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()) {
             user =new User(resultSet.getString(DataBaseHandler.USER_LOGIN_COLUMN),resultSet.getString(DataBaseHandler.USER_PASSWORD_COLUMN));
        }
        preparedStatement.close();
        return user;
    }

    public boolean checkUser(User user) throws SQLException{
        PreparedStatement preparedStatement;
        preparedStatement=dataBaseHandler.getPreparedStatement(CHECK_USER_PASSWORD_AND_LOGIN);
        preparedStatement.setString(1,user.getLogin());
        preparedStatement.setString(2,user.getPassword());
        ResultSet resultSet= preparedStatement.executeQuery();
        return resultSet.next();
    }

    public Long getIdByLogin(User user) throws SQLException{
        PreparedStatement preparedStatement;
        Long id=-1l;
        preparedStatement=dataBaseHandler.getPreparedStatement(GET_ID_BY_LOGIN);
        preparedStatement.setString(1,user.getLogin());
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            id=resultSet.getLong(DataBaseHandler.USER_ID_COLUMN);
        }
        resultSet.close();
        preparedStatement.close();
        return id;
    }

    public boolean addUser(User user) throws SQLException{
        PreparedStatement preparedStatement;
        if (getIdByLogin(user) !=-1) return false;
        else {
            preparedStatement = dataBaseHandler.getPreparedStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            if (preparedStatement.executeUpdate() ==0) throw new SQLException();
            preparedStatement.close();
       }
        return true;
    }

}
