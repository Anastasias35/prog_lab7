package Server.utilitka;


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
        ResponseType responseType=choiceCommand(request.getCommand(),request.getArgument(),request.getWorker());
        return new Response(responseType,StringResponse.getAndClear());
    }

    public  ResponseType choiceCommand(String commandName, String  argument, Worker newWorker){
        switch (commandName){
            case "help":
                if(!commandManager.help(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "execute_script":
                if(!commandManager.executeScript(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "clear":
                if(!commandManager.clear(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "info":
                if(!commandManager.info(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "print_descending":
                if(!commandManager.printDescending(argument,newWorker)) return  ResponseType.ERROR;
                break;
            case "countless_than_position":
                if(!commandManager.countLessThanPosition(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "exit":
                if(!commandManager.exit(argument,newWorker)) return ResponseType.ERROR;
                else return  ResponseType.EXIT;
            case "show":
                if(!commandManager.show(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "save":
                if(!commandManager.save(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "remove_by_id":
                if(!commandManager.removeById(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "add":
                if(!commandManager.add(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "add_if_min":
                if(!commandManager.addIfMin(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "add_if_max":
                if(!commandManager.addIfMax(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "print_field_ascending_salary":
                if(!commandManager.printFieldAscendingSalary(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "remove_lower":
                if(!commandManager.removeLower(argument,newWorker)) return ResponseType.ERROR;
                break;
            case "update":
                if(!commandManager.update(argument,newWorker)) return ResponseType.ERROR;
                break;
            default:
                StringResponse.appendError("Неверна введена команда. Введите help для справки");
                return ResponseType.ERROR;

        }
        return ResponseType.OK;
    }

}
