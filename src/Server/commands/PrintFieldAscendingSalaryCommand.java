package Server.commands;

import Common.data.Worker;
import Common.exceptions.EmptyCollection;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.CollectionManager;
import Server.utilitka.StringResponse;

/**
 * Команда "print_field_ascending_salary" выведит значения поля salary всех элементов в порядке возрастания
 */
public class PrintFieldAscendingSalaryCommand extends AbstractCommand {

    private String name;
    private String description;
    private CollectionManager collectionManager;

    public PrintFieldAscendingSalaryCommand(CollectionManager collectionManager){
        super("print_field_ascending_salary","вывести значения поля salary всех элементов в порядке возрастания");
        this.collectionManager=collectionManager;
    }

    /**
     * Выполнение команды
     * @param argument
     * @return состояние выполнение команды
     */
    @Override
    public boolean execute(String argument, Worker worker){
        try{
            if(collectionManager.sizeCollection()==0) throw new EmptyCollection();
            if(!argument.isEmpty()) throw new IncorrectArgumentException();
            collectionManager.sortSalary();
            return true;
        }catch (IncorrectArgumentException exception){
            StringResponse.appendError("Команда "+ getName() + " не имеет параметров");
            return false;
        }catch(EmptyCollection exception){
            StringResponse.appendError("Коллекция пуста");
            return false;
        }
    }
}
