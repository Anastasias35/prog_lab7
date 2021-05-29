package Server.commands;

import Client.util.User;
import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Common.exceptions.UserAlreadyExistException;
import Server.utilitka.DataBaseUserManager;
import Server.utilitka.StringResponse;

import java.sql.SQLException;

public class RegistrationCommand  extends AbstractCommand{

    private DataBaseUserManager dataBaseUserManager;
    public RegistrationCommand(DataBaseUserManager dataBaseUserManager){
        super("registration"," регистрация нового пользователя");
        this.dataBaseUserManager=dataBaseUserManager;
    }

    @Override
    public boolean execute(String argument, Worker worker, User user){
        try {
            if (!argument.isEmpty() || worker != null) throw new IncorrectArgumentException();
            if(dataBaseUserManager.addUser(user)) {
                StringResponse.appendln("Регистрация прошла успешно");
                return true;
            }else throw new UserAlreadyExistException();
        }catch(IncorrectArgumentException exception){
            StringResponse.appendError("У этой команды не может быть таких аргументов");
        }catch (UserAlreadyExistException exception){
            StringResponse.appendError("Такой пользователь уже существует");
        }catch (SQLException exception){
            exception.printStackTrace();
            StringResponse.appendError("Произошла ошибка при обращении к базе данных");
        }
        return false;
    }
}
