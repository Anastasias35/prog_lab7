package Server.commands;

import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

import java.time.Instant;
import java.util.Date;

public class UpdateCommand extends  AbstractCommand{

    private  String name;
    private  String description;
    private CollectionManager collectionManager;
    private RemoveByIdCommand removeByIdCommand;

    public UpdateCommand(CollectionManager collectionManager){
        super("update id {element}" , "обновить значение элемента коллекции, id которого равен заданному");
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
            if(argument.isEmpty()) throw new IncorrectArgumentException();
            Long id1=Long.parseLong(argument);
            if (collectionManager.comparingId(id1)) {
                collectionManager.addToCollection(worker);
            }
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда "+ getName() +" должна иметь параметр id");
            return false;
        }catch (NullPointerException exception){
            StringResponse.appendError("Элемент с таким id не найден");
            return false;
        }catch (NumberFormatException exception){
            StringResponse.appendError("id должен быть целым числом");
            return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }
    }
}
