package Server;

import Server.commands.*;
import Server.utilitka.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            DataBaseHandler dataBaseHandler=new DataBaseHandler();
            DataBaseUserManager dataBaseUserManager=new DataBaseUserManager(dataBaseHandler);
            DataBaseCollectionManager dataBaseCollectionManager=new DataBaseCollectionManager(dataBaseHandler,dataBaseUserManager);
            CollectionManager collectionManager = new CollectionManager(dataBaseCollectionManager);
            CommandManager commandManager=new CommandManager( new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ExitCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new AddCommand(collectionManager,dataBaseCollectionManager),
                    new RemoveByIdCommand(collectionManager,dataBaseCollectionManager),
                    new PrintFieldAscendingSalaryCommand(collectionManager),
                    new CountLessThanPositionCommand(collectionManager),
                    new UpdateCommand(collectionManager,dataBaseCollectionManager),
                    new AddIfMaxCommand(collectionManager,dataBaseCollectionManager),
                    new AddIfMinCommand(collectionManager,dataBaseCollectionManager),
                    new RemoveLowerCommand(collectionManager,dataBaseCollectionManager),
                    new PrintDescendingCommand(collectionManager,dataBaseCollectionManager),
                    new ExecuteScriptCommand(),
                    new LoginCommand(dataBaseUserManager),
                    new RegistrationCommand(dataBaseUserManager));

            ProcessingOfRequest processingOfRequest=new ProcessingOfRequest(commandManager);
            Server server = new Server(1616,processingOfRequest);
            server.work();

        }
}
