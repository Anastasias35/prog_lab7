package Server;

import Server.commands.*;
import Server.utilitka.CollectionManager;
import Server.utilitka.CommandManager;
import Server.utilitka.FileManager;
import Server.utilitka.ProcessingOfRequest;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = "";
        try {
            fileName = args[0];
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Вы не ввели имя файла в аргументе командной строки");
        } finally {
            FileManager fileManager = new FileManager(fileName);
            CollectionManager collectionManager = new CollectionManager(fileManager);
           CommandManager commandManager=new CommandManager( new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ExitCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new AddCommand(collectionManager),
                    new RemoveByIdCommand(collectionManager),
                    new PrintFieldAscendingSalaryCommand(collectionManager),
                    new CountLessThanPositionCommand(collectionManager),
                    new UpdateCommand(collectionManager),
                    new AddIfMaxCommand(collectionManager),
                    new AddIfMinCommand(collectionManager),
                    new RemoveLowerCommand(collectionManager),
                    new PrintDescendingCommand(collectionManager),
                    new ExecuteScriptCommand());


            ProcessingOfRequest processingOfRequest=new ProcessingOfRequest(commandManager);
            Server server = new Server(1616,processingOfRequest);
            server.work();

        }
    }
}
