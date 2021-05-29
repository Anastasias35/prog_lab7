package Server.commands;

import Client.util.User;
import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.DataBaseCollectionManager;
import Server.utilitka.StringResponse;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

public class UpdateCommand extends  AbstractCommand{

    private  String name;
    private  String description;
    private CollectionManager collectionManager;
    private RemoveByIdCommand removeByIdCommand;
    private DataBaseCollectionManager dataBaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager,DataBaseCollectionManager dataBaseCollectionManager){
        super("update id {element}" , "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager=collectionManager;
        this.dataBaseCollectionManager=dataBaseCollectionManager;
    }

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнения команды
     */
    @Override
    public boolean execute(String argument, Worker worker, User user) {
        try{
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if(argument.isEmpty()) throw new IncorrectArgumentException();
            Long id1=Long.parseLong(argument);
            if(collectionManager.comparingId(id1)){
                if (dataBaseCollectionManager.checkWorker(id1,user)) {
                    dataBaseCollectionManager.updateWorkerById(worker,id1);
                    collectionManager.addToCollection(worker);
                    StringResponse.appendln("Worker успешно изменен");
                } else{
                    StringResponse.appendln(" Пользователь не может изменить значение в коллекции");
                }
            } else {
                   StringResponse.appendln("Элемента с таким id не существует");
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
        }catch (SQLException exception){
            exception.printStackTrace();
            return false;
        }
    }
}
