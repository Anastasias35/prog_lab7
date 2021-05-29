package Server.commands;

import Client.util.User;
import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Common.exceptions.InvalidUsersDataException;
import Server.utilitka.DataBaseUserManager;
import Server.utilitka.StringResponse;

import java.sql.SQLException;

public class LoginCommand  extends AbstractCommand{

    private DataBaseUserManager dataBaseUserManager;

    public LoginCommand(DataBaseUserManager dataBaseUserManager){
        super("login","авторизация пользователя");
        this.dataBaseUserManager=dataBaseUserManager;
    }

    @Override
    public boolean execute(String argument, Worker worker, User user){
        try {
            if (!argument.isEmpty() || worker != null) throw new IncorrectArgumentException();
            if (dataBaseUserManager.checkUser(user)) {
                StringResponse.appendln("Пользователь успешно авторизован");
                return true;
            } else throw new InvalidUsersDataException();
        }catch (IncorrectArgumentException exception) {
            StringResponse.appendError("У этой команды не может быть таких аргументов");
        }catch (InvalidUsersDataException exception){
            StringResponse.appendError("Неверно введен логин или пароль");
        }catch (SQLException exception){
            exception.printStackTrace();
            StringResponse.appendError("Ошибка при обработке запроса в базе данных");
        }
        return  false;
    }
}
