package Server.commands;

import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

/**
 * Команда "info" выдает информацию о коллекции
 */
public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager){
        super("info","Вывести информацию о коллекции");
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
            if(!argument.isEmpty()) throw new IncorrectArgumentException();
            StringResponse.appendln("Информация о коллекции");
            StringResponse.appendln("Тип" + collectionManager.getClass().getName());
            if(collectionManager.getLastIntTime()==null ) {
                StringResponse.appendln("Коллекция не инициализирована");
            }
            else {
                StringResponse.appendln("Дата инициализации" + collectionManager.getLastIntTime());
            }
            StringResponse.appendln(collectionManager.sizeCollection());
            return true;
        }catch(IncorrectArgumentException exception){
            StringResponse.appendError("Команда не имеет параметров");
            return false;
        }
    }


}
