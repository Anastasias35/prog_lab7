package Server.utilitka;

import Client.util.User;
import Common.data.Worker;
import Server.commands.AbstractCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// запуск команд


// добавлять сюда  команды сразу после их создания для обработки
//добавить аннотацию для javadoc

/**
 * Класс для запуска команд
 */
public class CommandManager {

    private List<AbstractCommand> command= new ArrayList<>();
    private AbstractCommand helpCommand;
    private AbstractCommand infoCommand;
    private AbstractCommand exitCommand;
    private AbstractCommand showCommand;
    private AbstractCommand clearCommand;
    private AbstractCommand addCommand;
    private AbstractCommand removeByIdCommand;
    private AbstractCommand printFieldAscendingSalaryCommand;
    private AbstractCommand countLessThanPositionCommand;
    private AbstractCommand updateCommand;
    private AbstractCommand addIfMaxCommand;
    private AbstractCommand addIfMinCommand;
    private AbstractCommand removeLowerCommand;
    private AbstractCommand printDescendingCommand;
    private AbstractCommand executeScriptCommand;
    private AbstractCommand loginCommand;
    private AbstractCommand registrationCommand;
    private ReadWriteLock collectionlock=new ReentrantReadWriteLock();

    public CommandManager(AbstractCommand helpCommand,AbstractCommand infoCommand,AbstractCommand exitCommand, AbstractCommand showCommand,AbstractCommand clearCommand,
                          AbstractCommand addCommand,AbstractCommand removeByIdCommand, AbstractCommand printFieldAscendingSalaryCommand,
                          AbstractCommand countLessThanPositionCommand, AbstractCommand updateCommand, AbstractCommand addIfMaxCommand, AbstractCommand addIfMinCommand,
                          AbstractCommand removeLowerCommand, AbstractCommand printDescendingCommand,AbstractCommand executeScriptCommand, AbstractCommand loginCommand,
                          AbstractCommand registrationCommand){


        this.helpCommand=helpCommand;
        this.infoCommand=infoCommand;
        this.exitCommand=exitCommand;
        this.showCommand=showCommand;
        this.clearCommand=clearCommand;
        this.addCommand=addCommand;
        this.removeByIdCommand=removeByIdCommand;
        this.printFieldAscendingSalaryCommand=printFieldAscendingSalaryCommand;
        this.countLessThanPositionCommand=countLessThanPositionCommand;
        this.updateCommand=updateCommand;
        this.addIfMaxCommand=addIfMaxCommand;
        this.addIfMinCommand=addIfMinCommand;
        this.removeLowerCommand=removeLowerCommand;
        this.printDescendingCommand=printDescendingCommand;
        this.executeScriptCommand=executeScriptCommand;
        this.loginCommand=loginCommand;
        this.registrationCommand=registrationCommand;


        command.add(helpCommand);
        command.add(infoCommand);
        command.add(exitCommand);
        command.add(showCommand);
        command.add(clearCommand);
        command.add(addCommand);
        command.add(removeByIdCommand);
        command.add(printFieldAscendingSalaryCommand);
        command.add(countLessThanPositionCommand);
        command.add(updateCommand);
        command.add(addIfMaxCommand);
        command.add(addIfMinCommand);
        command.add(removeLowerCommand);
        command.add(printDescendingCommand);
        command.add(executeScriptCommand);

    }


    /**
     * Выводит все возможные команды с их описанием
     * @param argument
     * @return состояние работы команды
     */
    public boolean help(String argument, Worker worker, User user){
      if(helpCommand.execute(argument,worker,user)){
          for (AbstractCommand command1:command){
                StringResponse.appendln(command1.getName() + ": " + command1.getDescription()); //need to fix!!!!
          }
          return true;
      }
      else {
          return false;
      }
    }

    /**
     * Запускает команду информации о коллекции
     * @param argument
     * @return состояние работы коллекции
     */
    public boolean info(String argument, Worker worker,User user){
        collectionlock.readLock().lock();
        try{
            return infoCommand.execute(argument,worker,user);
        }finally {
            collectionlock.readLock().unlock();
        }
    }

    /**
     * Запускает команду выхода из программы
     * @param argument
     * @return состояние работы программы
     */
    public boolean exit(String argument,Worker worker,User user){
        return exitCommand.execute(argument,worker,user);
    }

    /**
     * Запускает команду очистки коллекции
     * @param argument
     * @return состояние работы программы
     */
    public boolean clear(String argument, Worker worker,User user){
        collectionlock.writeLock().lock();
        try {
            return clearCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /**
     * Запускает команду добавления элемента в коллекцию
     * @param argument
     * @return состояние работы программы
     */
    public boolean add(String argument, Worker worker,User user){
        collectionlock.writeLock().lock();
        try {
            return addCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /**
     * Запускает команду удаления элемента из коллекции по его id
     * @param argument
     * @return состояние работы команды
     */
    public boolean removeById(String argument, Worker worker,User user){
        collectionlock.writeLock().lock();
        try {
            return removeByIdCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /**
     * Запускает команду вывода всех элементов из коллекции
     * @param argument
     * @return состояние работы программы
     */
    public boolean show(String argument,Worker worker,User user){
        collectionlock.readLock().lock();
        try {
            return showCommand.execute(argument, worker, user);
        }finally {
            collectionlock.readLock().unlock();
        }
    }

    /**
     * Запускает команду вычисление количества элементов, значения поля position которых меньше заданного
     * @param argument
     * @return состояние работы программы
     */
    public boolean countLessThanPosition(String argument, Worker worker,User user){
        collectionlock.readLock().lock();
        try {
            return countLessThanPositionCommand.execute(argument, worker, user);
        }finally {
            collectionlock.readLock().unlock();
        }
    }

    /**
     * Запускает программу вывода поля salary
     * @param argument
     * @return состояние работы команды
     */
    public boolean printFieldAscendingSalary(String argument, Worker worker,User user){
        collectionlock.readLock().lock();
        try {
            return printFieldAscendingSalaryCommand.execute(argument, worker, user);
        }finally {
            collectionlock.readLock().unlock();
        }
    }

    /**
     * Запускает программу обновление элемента по его id
     * @param argument
     * @return состояние работы программы
     */
    public boolean update(String argument, Worker worker,User user){
        collectionlock.writeLock().lock();
        try {
            return updateCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /**
     * Запускает программу добавления элемента,если он больше
     * @param argument
     * @return состояние работы программы
     */
    public boolean addIfMax(String argument, Worker worker,User user){
        collectionlock.writeLock().lock();
        try {
            return addIfMaxCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /**
     * Запускает программу добавления элемента,если он меньше
     * @param argument
     * @return состояние работы программы
     */
    public boolean addIfMin(String argument, Worker worker,User user) {
        collectionlock.writeLock().lock();
        try {
            return addIfMinCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /**
     * Запускает команду удаления всех элементов, меньших, чем заданный
     * @param argument
     * @return состояние работы программы
     */
    public boolean removeLower(String argument, Worker worker,User user){
        collectionlock.writeLock().lock();
        try {
            return removeLowerCommand.execute(argument, worker, user);
        }finally {
            collectionlock.writeLock().unlock();
        }
    }

    /*public boolean executeScript(String argument) {
        return executeScriptCommand.execute(argument);
    }

     */

    /**
     * Запускает команду вывода элементов из коллекции в порядке убывания
     * @param argument
     * @return состояние работы программы
     */
    public boolean printDescending(String argument,Worker worker,User user) {
        collectionlock.readLock().lock();
        try {
            return printDescendingCommand.execute(argument, worker, user);
        }finally {
            collectionlock.readLock().unlock();
        }
    }

    /**
     * Запускает команду считывание и исполнения скрипта из указанного файла
     * @param argument
     * @return состояние работы программы
     */
    public boolean executeScript(String argument, Worker worker,User user){
        return executeScriptCommand.execute(argument,worker,user);

    }

    public boolean login(String argumnet,Worker worker,User user){
        return loginCommand.execute(argumnet,worker,user);
    }

    public boolean registration(String argument, Worker worker, User user){
        return registrationCommand.execute(argument,worker,user);
    }

}


