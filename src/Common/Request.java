package Common;

import Client.util.User;
import Common.data.Worker;
import Server.commands.AbstractCommand;

import java.io.Serializable;

//Класс формирования запроса клиента,который посылается на сервер
public class Request  implements Serializable {

    private String command;
    private String argument;
    private Worker worker;
    private User user;

    public Request(String command, String argument, Worker worker, User user){
        this.command=command;
        this.argument=argument;
        this.worker=worker;
        this.user=user;
    }

    public Request(String command,String argument,User user){
        this.command=command;
        this.argument=argument;
        this.user=user;
    }

    public Request(String command,String argument, Worker worker) {
        this.command=command;
        this.argument = argument;
        this.worker = worker;
    }

    public Request(String command, String argument){
        this.command=command;
        this.argument=argument;
        this.worker=null;
    }

    public Request(){
        this.command=null;
        this.argument="";
        this.worker=null;
    }

    public String getCommand(){
        return command;
    }

    public boolean isEmpty(){
        return command.isEmpty()&&argument.isEmpty()&&worker==null;
    }

    public String getArgument(){
        return argument;
    }

    public Worker getWorker(){
        return worker;
    }


    public User getUser() {
        return user;
    }
}
