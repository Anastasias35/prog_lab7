package Server.commands;

import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

/**
 * Команда "save" сохраняет коллекцию в файл
 */
public class SaveCommand extends AbstractCommand {

    private String name;
    private String description;
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager){
        super("save", "сохранить коллекцию в файл");
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
            collectionManager.saveCollection();
            System.out.println("Коллекция сохранена");
            return true;
        } catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров");
            return false;
        }
    }
}
