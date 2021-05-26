package Server.commands;
import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

import java.time.Instant;
import java.util.Date;

public class RemoveLowerCommand extends AbstractCommand {

    private String name;
    private String discription;
    private CollectionManager collectionManager;
    private Worker worker;


    public RemoveLowerCommand(CollectionManager collectionManager){
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
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
            collectionManager.deleteIfLower(worker);
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров ");
            return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }
    }
}
