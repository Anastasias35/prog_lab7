package Server.commands;
import Client.util.User;
import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.DataBaseCollectionManager;
import Server.utilitka.StringResponse;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

public class RemoveLowerCommand extends AbstractCommand {

    private String name;
    private String discription;
    private CollectionManager collectionManager;
    private Worker worker;
    private DataBaseCollectionManager dataBaseCollectionManager;


    public RemoveLowerCommand(CollectionManager collectionManager,DataBaseCollectionManager dataBaseCollectionManager){
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager=collectionManager;
        this.dataBaseCollectionManager=dataBaseCollectionManager;

    }

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker, User user) {
        try{
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if(!argument.isEmpty()) throw new IncorrectArgumentException();
            collectionManager.deleteIfLower(worker,user); //переделать
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров ");
            return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }catch (SQLException exception){
            exception.printStackTrace();
            StringResponse.appendError("Произошла ошибка при обращении к базе данных");
            return false;
        }
    }
}
