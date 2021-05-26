package Server.commands;

import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

import java.time.Instant;
import java.util.Date;

/**
 * Команда "add" добавляет элемент в коллекцию
 */
public class AddCommand extends AbstractCommand {

    private String name;
    private String desciption;
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager){
        super("add {element}","добавить новый элемент в коллекцию");
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
            if (!argument.isEmpty()) throw new IncorrectArgumentException();
            Instant instant=Instant.now();
            collectionManager.addToCollection(worker);
            return  true;
        } catch (IncorrectArgumentException incorrectArgumentException){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров");
            return false;
        }
    }
}
