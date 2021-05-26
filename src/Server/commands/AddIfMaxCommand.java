package Server.commands;

import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

import java.time.Instant;
import java.util.Date;

/**
 *  Команда "add_if_max" добавляет элемент,если он больше максимального в коллекции
 */
public class AddIfMaxCommand extends AbstractCommand{

    private String name;
    private String description;
    private CollectionManager collectionManager;

    private RemoveByIdCommand removeByIdCommand;

    public AddIfMaxCommand(CollectionManager collectionManager){
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager=collectionManager;
    }

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker) {
        try{
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if(!argument.isEmpty()) throw new IncorrectArgumentException();
            if(collectionManager.maxCoordinates(worker)){
                collectionManager.addToCollection(worker);
                StringResponse.appendln("Worker добавлен в коллекцию");
            }
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда "+ getName() +" не должна иметь аргументы");
            return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }
    }
}
