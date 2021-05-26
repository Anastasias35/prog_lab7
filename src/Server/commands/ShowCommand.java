package Server.commands;

import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

//доделать команду

/**
 * Команда "show" выводит все элементы коллекции в строковом представлении
 */
public class ShowCommand extends AbstractCommand {

    private String name;
    private String description;
    private CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager){
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager=collectionManager;
    }


    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker){
        try {
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if (!argument.isEmpty()) throw new IncorrectArgumentException();
            collectionManager.stringCollection();
            return true;
        }catch(IncorrectArgumentException exception){
            StringResponse.appendError("Команда " + getName() + " не имеет параметров");
            return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }
    }
}
