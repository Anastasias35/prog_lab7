package Server.commands;

import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

/**
 * Команда "clear" очищает  коллекцию
 */
public class ClearCommand extends AbstractCommand {

    private String name;
    private String description;
    CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager){
        super("clear", "очистить коллекцию");
        this.collectionManager=collectionManager;
    }

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker){
        try{
            if(!argument.isEmpty()) throw new IncorrectArgumentException();
            collectionManager.clearCollection();
            StringResponse.appendln("Коллекция очищена");
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров");
            return false;
        }
    }

}
