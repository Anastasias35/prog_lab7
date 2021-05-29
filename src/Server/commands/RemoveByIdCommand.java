package Server.commands;

import Client.util.User;
import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.DataBaseCollectionManager;
import Server.utilitka.StringResponse;

import java.sql.SQLException;

/**
 * Команда "remove_by_id" удаляет элемент из коллекции по его id
 */
public class RemoveByIdCommand extends AbstractCommand {

    private String name;
    private String description;
    private CollectionManager collectionManager;
    private Long id;
    private DataBaseCollectionManager dataBaseCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DataBaseCollectionManager dataBaseCollectionManager){
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionManager=collectionManager;
        this.dataBaseCollectionManager=dataBaseCollectionManager;
    }


    //добавить ошибку на пустую коллекцию

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker, User user) {
        try {
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if (argument.isEmpty()) throw new IncorrectArgumentException();
            id = Long.parseLong(argument);
            if (collectionManager.comparingId(id)) {
                if (dataBaseCollectionManager.checkWorker(id,user)) {
                    collectionManager.removeItem(id);
                    StringResponse.appendln("worker с таким id удален");
                } else StringResponse.appendln("Пользователь не может удалить этот элемент");
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
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }
    }
}
