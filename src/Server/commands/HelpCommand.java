package Server.commands;

import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Server.utilitka.StringResponse;

//import  utility.Console;

/**
 * Команда "help" выдает все информацию по всем доступным команднам
 */
public class HelpCommand extends AbstractCommand{

      public HelpCommand(){
          super("help", "вывести справку по доступным командам");
      }

    /**
     * Выполнение команды
     * @param arguments
     * @return состояние выполнения команды
     */
    @Override
      public boolean execute(String arguments, Worker worker){
          try{
              if(!arguments.isEmpty()) throw new IncorrectArgumentException();
              return  true;
          }catch(IncorrectArgumentException exception){
              StringResponse.appendError("У этой команды нет параметров");
              return  false;
          }
      }
}
