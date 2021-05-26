package Server.utilitka;

import Client.util.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUserManager {


    public static final String GET_USER_BY_ID="SELECT * FROM " + DataBaseHandler.USER_TABLE +
            " WHERE " + DataBaseHandler.USER_ID_COLUMN +" =?";
    public static final String GET_ID_BY_LOGIN="SELECT * FROM " + DataBaseHandler.USER_TABLE +
            " WHERE " + DataBaseHandler.USER_LOGIN_COLUMN + " =?";
    public static final String INSERT_NEW_USER="INSERT INTO "+ DataBaseHandler.USER_TABLE +
            " ( "+ DataBaseHandler.USER_LOGIN_COLUMN + " ," +DataBaseHandler.USER_PASSWORD_COLUMN +
            " ) VALUES (?,?)";
    public static final String CHECK_USER_PASSWORD_AND_LOGIN=" SELECT * FROM " +DataBaseHandler.USER_TABLE+
            " WHERE " +DataBaseHandler.USER_LOGIN_COLUMN + " =? AND" +DataBaseHandler.USER_PASSWORD_COLUMN + " =?";

    private DataBaseHandler dataBaseHandler;

    public DataBaseUserManager(DataBaseHandler dataBaseHandler){
        this.dataBaseHandler=dataBaseHandler;
    }

    public User getUserById(Long id) throws SQLException{
        PreparedStatement preparedStatement;
        User user=null;
        preparedStatement=dataBaseHandler.getPreparedStatement(GET_USER_BY_ID,false);
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
        preparedStatement=dataBaseHandler.getPreparedStatement(CHECK_USER_PASSWORD_AND_LOGIN,false);
        preparedStatement.setString(1,user.getLogin());
        preparedStatement.setString(2,user.getPassword());
        ResultSet resultSet= preparedStatement.executeQuery();
        preparedStatement.close();
        return resultSet.next();
    }

    public Long getIdByLogin(User user) throws SQLException{
        PreparedStatement preparedStatement;
        Long id=null;
        preparedStatement=dataBaseHandler.getPreparedStatement(GET_ID_BY_LOGIN,false);
        preparedStatement.setString(1,user.getLogin());
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            id=resultSet.getLong(DataBaseHandler.USER_ID_COLUMN);
        }
        preparedStatement.close();
        return id;
    }

    public boolean addUser(User user) throws SQLException{
        PreparedStatement preparedStatement;
        if (getIdByLogin(user) ==-1) return false;
        else {
            preparedStatement = dataBaseHandler.getPreparedStatement(INSERT_NEW_USER, true);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.close();
        }
        return true;
    }

}
