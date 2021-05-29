package Server.utilitka;


import Client.util.User;
import Common.Request;
import Common.Response;
import Common.ResponseType;
import Common.data.Worker;

//класс обработки запроса и формирования ответа для отправки клиенту
public class ProcessingOfRequest {
    private CommandManager commandManager;

    public  ProcessingOfRequest(CommandManager commandManager){
        this.commandManager=commandManager;
    }

    public Response getResponse(Request request){
        User user=new User(request.getUser().getLogin(),PasswordHasher.hasher(request.getUser().getPassword()));
        ResponseType responseType=choiceCommand(request.getCommand(),request.getArgument(),request.getWorker(),user);
        return new Response(responseType,StringResponse.getAndClear());
    }

    public  ResponseType choiceCommand(String commandName, String  argument, Worker newWorker, User user){
        switch (commandName){
            case "help":
                if(!commandManager.help(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "execute_script":
                if(!commandManager.executeScript(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "clear":
                if(!commandManager.clear(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "info":
                if(!commandManager.info(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "print_descending":
                if(!commandManager.printDescending(argument,newWorker,user)) return  ResponseType.ERROR;
                break;
            case "count_less_than_position":
                if(!commandManager.countLessThanPosition(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "exit":
                if(!commandManager.exit(argument,newWorker,user)) return ResponseType.ERROR;
                else return  ResponseType.EXIT;
            case "show":
                if(!commandManager.show(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "remove_by_id":
                if(!commandManager.removeById(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "add":
                if(!commandManager.add(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "add_if_min":
                if(!commandManager.addIfMin(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "add_if_max":
                if(!commandManager.addIfMax(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "print_field_ascending_salary":
                if(!commandManager.printFieldAscendingSalary(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "remove_lower":
                if(!commandManager.removeLower(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "update":
                if(!commandManager.update(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "login":
                if(!commandManager.login(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            case "registration":
                if(!commandManager.registration(argument,newWorker,user)) return ResponseType.ERROR;
                break;
            default:
                StringResponse.appendError("Неверна введена команда. Введите help для справки");
                return ResponseType.ERROR;

        }
        return ResponseType.OK;
    }

}
