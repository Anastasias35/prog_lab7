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

/**
 *  Команда "add_if_max" добавляет элемент,если он больше максимального в коллекции
 */
public class AddIfMaxCommand extends AbstractCommand{

    private String name;
    private String description;
    private CollectionManager collectionManager;
    private DataBaseCollectionManager dataBaseCollectionManager;

    private RemoveByIdCommand removeByIdCommand;

    public AddIfMaxCommand(CollectionManager collectionManager, DataBaseCollectionManager dataBaseCollectionManager){
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
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
            if(collectionManager.maxCoordinates(worker)){
                collectionManager.addToCollection(worker);
                dataBaseCollectionManager.addWorker(worker,user);
                StringResponse.appendln("Worker добавлен в базу данных");


            }
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда "+ getName() +" не должна иметь аргументы");
            //return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
           // return false;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
