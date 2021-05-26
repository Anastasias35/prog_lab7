package Server;

import Server.commands.*;
import Server.utilitka.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "";
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Вы не ввели имя файла в аргументе командной строки");
        } finally {
            DataBaseHandler dataBaseHandler=new DataBaseHandler();
            DataBaseUserManager dataBaseUserManager=new DataBaseUserManager(dataBaseHandler);
            FileManager fileManager = new FileManager(fileName);
            CollectionManager collectionManager = new CollectionManager(fileManager);
           CommandManager commandManager=new CommandManager( new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ExitCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new AddCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new PrintFieldAscendingSalaryCommand(collectionManager),
                    new CountLessThanPositionCommand(collectionManager),
                    new UpdateCommand(collectionManager),
                    new AddIfMaxCommand(collectionManager),
                    new AddIfMinCommand(collectionManager),
                    new RemoveLowerCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new ExecuteScriptCommand(),
                    new LoginCommand(dataBaseUserManager),
                    new RegistrationCommand(dataBaseUserManager));

            ProcessingOfRequest processingOfRequest=new ProcessingOfRequest(commandManager);
            Server server = new Server(1616,processingOfRequest);
            server.work();

        }
    }
}
