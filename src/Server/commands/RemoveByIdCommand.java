package Server.commands;

import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

/**
 * Команда "remove_by_id" удаляет элемент из коллекции по его id
 */
public class RemoveByIdCommand extends AbstractCommand {

    private String name;
    private String description;
    private CollectionManager collectionManager;
    private Long id;

    public RemoveByIdCommand(CollectionManager collectionManager){
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionManager=collectionManager;
    }


    //добавить ошибку на пустую коллекцию

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker) {
        try {
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if (argument.isEmpty()) throw new IncorrectArgumentException();
            id = Long.parseLong(argument);
            if (collectionManager.comparingId(id)) { //!!! подумкать
                collectionManager.removeItem(id);
            } else {
                StringResponse.appendln("Worker c таким id не найден :(");
            }
            return true;
        } catch (IncorrectArgumentException exception) {
            StringResponse.appendError("Команда " + getName() + " имеет параметры");
            return false;
        } catch (NumberFormatException exception) {
            StringResponse.appendError("id должен быть целым числом");
            return false;
        }catch (EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }
    }
}
