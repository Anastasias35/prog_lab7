package Server.utilitka;

import Client.util.User;
import Common.data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;

//работа с коллекцией в базе данных
public class DataBaseCollectionManager {

    private static final String ALL_WORKER_REQUEST="SELECT  * FROM " + DataBaseHandler.WORKER_TABLE;
    private static final String ADD_NEW_WORKER_REQUEST="INSERT INTO " + DataBaseHandler.WORKER_TABLE + " (" +
            DataBaseHandler.WORKER_NAME_COLUMN + ", " +
            DataBaseHandler.WORKER_X_COLUMN + ", " +
            DataBaseHandler.WORKER_Y_COLUMN +", " +
            DataBaseHandler.WORKER_CREATIONDATE_COLUMN +", " +
            DataBaseHandler.WORKER_SALARY_COLUMN +", " +
            DataBaseHandler.WORKER_STARTDATE_COLUMN +", " +
            DataBaseHandler.WORKER_ENDDATE_COLUMN + ", " +
            DataBaseHandler.WORKER_POSITION_COLUMN + ", " +
            DataBaseHandler.WORKER_WEIGHT_COLUMN +", " +
            DataBaseHandler.WORKER_EYECOLOR_COLUMN + ", " +
            DataBaseHandler.WORKER_HAIRCOLOR_COLUMN +", " +
            DataBaseHandler.WORKER_NATIONALITY_COLUMN + ", " +
            DataBaseHandler.WORKER_USER_ID_COLUMN + ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?)";
    private static final String DELETE_WORKER_BY_USER_ID_REQUEST="DELETE FROM " + DataBaseHandler.WORKER_TABLE +
            " WHERE " + DataBaseHandler.WORKER_USER_ID_COLUMN + " = ?";
    private static final String CHECK_WORKER_ID_BY_USER_ID_REQUEST=" SELECT * FROM " +DataBaseHandler.WORKER_TABLE +
            " WHERE " + DataBaseHandler.WORKER_ID_COLUMN + " = ? AND "+
            DataBaseHandler.WORKER_USER_ID_COLUMN + " = ?";
    private static final String UPDATE_WORKER_BY_ID="UPDATE " + DataBaseHandler.WORKER_TABLE + " SET " +
            DataBaseHandler.WORKER_NAME_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_X_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_Y_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_CREATIONDATE_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_SALARY_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_STARTDATE_COLUMN + " = ?, "+
            DataBaseHandler.WORKER_ENDDATE_COLUMN + " = ?, "+
            DataBaseHandler.WORKER_POSITION_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_WEIGHT_COLUMN + " = ?, " +
            DataBaseHandler.WORKER_EYECOLOR_COLUMN + " = ?, "+
            DataBaseHandler.WORKER_HAIRCOLOR_COLUMN + " = ?, "+
            DataBaseHandler.WORKER_NATIONALITY_COLUMN + " = ? WHERE " + DataBaseHandler.WORKER_ID_COLUMN + " = ?";
    private static final String DELETE_WORKER_BY_USER_ID_AND_ID_REQUEST= "DELETE FROM " +DataBaseHandler.WORKER_TABLE+
            " WHERE " + DataBaseHandler.WORKER_ID_COLUMN + " = ? AND " +DataBaseHandler.WORKER_USER_ID_COLUMN + " = ? ";





    private DataBaseHandler dataBaseHandler;
    private DataBaseUserManager dataBaseUserManager;

    public DataBaseCollectionManager (DataBaseHandler dataBaseHandler, DataBaseUserManager dataBaseUserManager){
        this.dataBaseHandler=dataBaseHandler;
        this.dataBaseUserManager=dataBaseUserManager;
    }


    public Worker getWorker(ResultSet resultSet) throws SQLException{
        try {
            Long id = resultSet.getLong(DataBaseHandler.WORKER_ID_COLUMN);
            String name = resultSet.getString(DataBaseHandler.WORKER_NAME_COLUMN);
            if (name.isEmpty()) throw new NullPointerException();
            Integer coordinatesX = resultSet.getInt(DataBaseHandler.WORKER_X_COLUMN);
            if (coordinatesX == null) throw new NullPointerException();
            Double coordinatesY = resultSet.getDouble(DataBaseHandler.WORKER_Y_COLUMN);
            if (coordinatesY == null) throw new NullPointerException();
            Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);
            Instant instant = Instant.now();
            Long salary = resultSet.getLong(DataBaseHandler.WORKER_SALARY_COLUMN);
            LocalDateTime startDate = LocalDateTime.parse(resultSet.getString(DataBaseHandler.WORKER_STARTDATE_COLUMN));
            LocalDateTime endDate = LocalDateTime.parse(resultSet.getString(DataBaseHandler.WORKER_ENDDATE_COLUMN));
            Position position = Position.valueOf(resultSet.getString(DataBaseHandler.WORKER_POSITION_COLUMN));
            Double weight = resultSet.getDouble(DataBaseHandler.WORKER_WEIGHT_COLUMN);
            Color eyeColor = Color.valueOf(resultSet.getString(DataBaseHandler.WORKER_EYECOLOR_COLUMN));
            Color hairColor = Color.valueOf(resultSet.getString(DataBaseHandler.WORKER_HAIRCOLOR_COLUMN));
            Country nationality = Country.valueOf(resultSet.getString(DataBaseHandler.WORKER_NATIONALITY_COLUMN));
            if (nationality==null) throw new NullPointerException();
            Person person = new Person(weight, eyeColor, hairColor, nationality);
            User user = dataBaseUserManager.getUserById(resultSet.getLong(DataBaseHandler.WORKER_USER_ID_COLUMN));
            return new Worker(id,name,coordinates,Date.from(instant),salary,startDate,endDate, position,person,user);
        }catch (NullPointerException exception){
            return null;
        }


    }

    public LinkedHashSet<Worker> getWorkerFromDataBase() throws SQLException {
        LinkedHashSet<Worker> workerLinkedHashSet=new LinkedHashSet<Worker>();
        PreparedStatement preparedStatement;
        preparedStatement=dataBaseHandler.getPreparedStatement(ALL_WORKER_REQUEST);
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            Worker worker=getWorker(resultSet);
            if (worker!=null) {
                workerLinkedHashSet.add(getWorker(resultSet));
            }
        }
        return workerLinkedHashSet;
    }

    public boolean addWorker(Worker worker,User user) throws SQLException{
        PreparedStatement preparedStatement;
        preparedStatement=dataBaseHandler.getPreparedStatement(ADD_NEW_WORKER_REQUEST);
        preparedStatement.setString(1,worker.getName());
        preparedStatement.setLong(2,worker.getCoordinates().getX());
        preparedStatement.setDouble(3,worker.getCoordinates().getY());
        preparedStatement.setString(4,String.valueOf(worker.getCreationDate()));
        preparedStatement.setLong(5,worker.getSalary());
        preparedStatement.setString(6,String.valueOf(worker.getStartDate()));
        preparedStatement.setString(7,String.valueOf(worker.getEndDate()));
        preparedStatement.setString(8,String.valueOf(worker.getPosition()));
        preparedStatement.setDouble(9,worker.getPerson().getWeight());
        preparedStatement.setString(10,String.valueOf(worker.getPerson().getEyecolor()));
        preparedStatement.setString(11,String.valueOf(worker.getPerson().getHaircolor()));
        preparedStatement.setString(12,String.valueOf(worker.getPerson().getNationality()));
        preparedStatement.setLong(13,dataBaseUserManager.getIdByLogin(user));
        if(preparedStatement.executeUpdate() !=0) {
            return true;
        }
        else{
            throw new SQLException();
        }
    }

    public void deleteWorker(User user) throws SQLException{
        PreparedStatement preparedStatement;
        preparedStatement=dataBaseHandler.getPreparedStatement(DataBaseCollectionManager.DELETE_WORKER_BY_USER_ID_REQUEST);
        preparedStatement.setLong(1,dataBaseUserManager.getIdByLogin(user));
        preparedStatement.executeUpdate();
    }


    public LinkedHashSet<Worker> clearCollection(User user) throws SQLException{
        deleteWorker(user);
        return getWorkerFromDataBase();

    }

    public boolean checkWorker(Long id,User user) throws SQLException{
        PreparedStatement preparedStatement=dataBaseHandler.getPreparedStatement(CHECK_WORKER_ID_BY_USER_ID_REQUEST);
        preparedStatement.setLong(1,id);
        preparedStatement.setLong(2,dataBaseUserManager.getIdByLogin(user));
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }



    public void deleteWorkerByUserIdAndIdRequest(Worker worker,User user) throws SQLException{
        PreparedStatement preparedStatement=dataBaseHandler.getPreparedStatement(DELETE_WORKER_BY_USER_ID_AND_ID_REQUEST);
        preparedStatement.setLong(1,worker.getId());
        preparedStatement.setLong(2,dataBaseUserManager.getIdByLogin(user));
        preparedStatement.executeUpdate();
    }

    public void updateWorkerById(Worker worker,Long  id) throws  SQLException{
        PreparedStatement preparedStatement=dataBaseHandler.getPreparedStatement(UPDATE_WORKER_BY_ID);
        preparedStatement.setString(1,worker.getName());
        preparedStatement.setLong(2,worker.getCoordinates().getX());
        preparedStatement.setDouble(3,worker.getCoordinates().getY());
        preparedStatement.setString(4,String.valueOf(worker.getCreationDate()));
        preparedStatement.setLong(5,worker.getSalary());
        preparedStatement.setString(6,String.valueOf(worker.getStartDate()));
        preparedStatement.setString(7,String.valueOf(worker.getEndDate()));
        preparedStatement.setString(8,String.valueOf(worker.getPosition()));
        preparedStatement.setDouble(9,worker.getPerson().getWeight());
        preparedStatement.setString(10,String.valueOf(worker.getPerson().getEyecolor()));
        preparedStatement.setString(11,String.valueOf(worker.getPerson().getHaircolor()));
        preparedStatement.setString(12,String.valueOf(worker.getPerson().getNationality()));
        preparedStatement.setLong(13,id);
        preparedStatement.executeUpdate();
    }




}
