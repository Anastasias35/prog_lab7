package Server.utilitka;

import java.io.Console;
import java.sql.*;
import java.util.Scanner;

public class DataBaseHandler {

    public static final String USER_TABLE="Users";
    public static final String WORKER_TABLE="worker";
    public static final String USER_ID_COLUMN="id";
    public static final String USER_LOGIN_COLUMN="login";
    public static final String USER_PASSWORD_COLUMN="password";

    public static final String WORKER_ID_COLUMN="id";
    public static final String WORKER_NAME_COLUMN="name";
    public static final String WORKER_X_COLUMN="x";
    public static final String WORKER_Y_COLUMN="y";
    public static final String WORKER_CREATIONDATE_COLUMN="creationDate";
    public static final String WORKER_SALARY_COLUMN="salary";
    public static final String WORKER_STARTDATE_COLUMN="startDate";
    public static final String WORKER_ENDDATE_COLUMN="endDate";
    public static final String WORKER_POSITION_COLUMN="position";
    public static final String WORKER_WEIGHT_COLUMN="weight";
    public static final String WORKER_EYECOLOR_COLUMN="eyeColor";
    public static final String WORKER_HAIRCOLOR_COLUMN="hairColor";
    public static final String WORKER_NATIONALITY_COLUMN="nationality";
    public static final String WORKER_USER_ID_COLUMN="userid";
    private final String JDBC_DRIVER = "org.postgresql.Driver";

    private final String url = "jdbc:postgresql://localhost:5716/studs";
    private String login;
    private String password;
    private Connection connection;

    public DataBaseHandler() {
        connectWithDataBase();
    }


    public void connectWithDataBase(){
        Scanner scanner=new Scanner(System.in);
        Console console = System.console();
        System.out.println("Подключение к базе данных");
        while(true) {
            System.out.println("Введите логин:");
            this.login=scanner.nextLine().trim();
            System.out.println("Введите пароль:");
            this.password=String.valueOf(console.readPassword());
            try { ;
                Class.forName("org.postgresql.Driver");
                connection= DriverManager.getConnection(url,login,password);
                System.out.println("Соединение установлено");
                break;
            } catch (ClassNotFoundException exception) {
                System.out.println("Неверно введен логин или пароль");
            } catch (SQLException exception) {
                System.out.println("Драйвер не найден");
                System.exit(0);
            }
        }
    }


    public PreparedStatement getPreparedStatement(String request,boolean key) throws  SQLException{
        PreparedStatement preparedStatement=null;
        try{
            if(connection==null) throw new SQLException();
            int generateKeys=key? Statement.RETURN_GENERATED_KEYS:Statement.NO_GENERATED_KEYS;
            preparedStatement=connection.prepareStatement(request,generateKeys);
            return preparedStatement;
        }catch(SQLException exception){
            if(connection==null) System.out.println("Соединение не установлено");
            throw  new SQLException();
        }

    }

}
