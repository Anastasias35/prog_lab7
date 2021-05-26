package Server.commands;

import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

/**
 * Команда "exit" завершает программу
 */
public class ExitCommand extends AbstractCommand {
    private String name;
    private String description;
    private CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager){
        super("exit "," завершить программу (без сохранения в файл)");
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
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда "+getName() + " не имеет параметров" );
            return false;
        }
    }
}
