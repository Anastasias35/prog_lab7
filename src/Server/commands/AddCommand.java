package Server.commands;

import Client.util.User;
import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.DataBaseCollectionManager;
import Server.utilitka.StringResponse;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

/**
 * Команда "add" добавляет элемент в коллекцию
 */
public class AddCommand extends AbstractCommand {

    private String name;
    private String desciption;
    private CollectionManager collectionManager;
    private DataBaseCollectionManager dataBaseCollectionManager;

    public AddCommand(CollectionManager collectionManager,DataBaseCollectionManager dataBaseCollectionManager){
        super("add {element}","добавить новый элемент в коллекцию");
        this.collectionManager=collectionManager;
        this.dataBaseCollectionManager=dataBaseCollectionManager;


    }

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker, User user){
        try{
            if (!argument.isEmpty()) throw new IncorrectArgumentException();
            Instant instant=Instant.now();
            if(dataBaseCollectionManager.addWorker(worker,user)){
                collectionManager.addToCollection(worker);
                StringResponse.appendln("Worker успешно добавлен в базу данных");
                return  true;
            }
            return false;
        } catch (IncorrectArgumentException incorrectArgumentException){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров");
            return false;
        }catch (SQLException exception){
            exception.printStackTrace();
            StringResponse.appendln("Произошла ошибка при добавлении элемента в базу данных");
            return false;
        }
    }
}
