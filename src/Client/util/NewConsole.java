package Client.util;

import Common.CommandType;
import Common.Request;
import Common.data.Worker;
import Common.exceptions.IncorrectArgumentException;
import Common.exceptions.RecursionException;
import Common.ResponseType;
import Server.utilitka.CollectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.Instant;
import java.util.*;
//Класс, работающий с консолью

public class NewConsole {
    private Creator creator;
    private Scanner scanner;
    private Stack<Scanner> scannerStack = new Stack<>();
    private ArrayList<String> scriptName = new ArrayList<>();


    public NewConsole(Scanner scanner, Creator creator) {
        this.scanner = scanner;
        this.creator=creator;
    }


    //Мы получаем результат сервера после ввода предыдущей команды и формируем новый запрос на сервер
    public Request actMode(User user) {
        String[] userCommand = {"", ""};
        CommandType inputType = null;
        ResponseType responseType = null;
        String responseBody = null;
        try {
            do {
                while (!scannerStack.isEmpty() && !scanner.hasNext()) {
                    scanner.close();
                    scannerStack.pop();
                }
                if (!scannerStack.isEmpty()) {
                    userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    if (userCommand[0].equals("execute_script")) {
                        for (String name : scriptName) {
                            if (userCommand[1].equals(name)) {
                                throw new RecursionException();
                            }
                        }
                    }
                }
                if (scannerStack.isEmpty()) {
                    scriptName.clear();
                    scanner = new Scanner(System.in);
                    System.out.println("Введите желаемую команду");
                    userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                inputType = choiceCommand(userCommand);
                if (inputType == CommandType.OBJECT) {
                    Creator creator = new Creator(scanner);
                    Instant instant = Instant.now();
                    return new Request(userCommand[0], userCommand[1], new Worker(0, creator.inputName(), creator.inputCoordinates(), Date.from(instant), creator.inputSalary(),
                            creator.inputStartDate(), creator.inputEndDate(), creator.inputPosition(), creator.inputPerson(),user),user);

                } else if (inputType == CommandType.SCRIPT) {
                    File file = new File(userCommand[1]);
                    scriptName.add(userCommand[1]);
                    try {
                        scannerStack.push(scanner);
                        scanner = new Scanner(file);
                        if (!scanner.hasNext()) throw new NoSuchElementException();

                        break;
                    } catch (FileNotFoundException exception) {
                        if (!file.canRead() & file.exists()) System.out.println("Нет прав на чтение файла");
                        else System.out.println("Файл с таким  именем не найден");
                    } catch (NoSuchElementException exception) {
                        System.out.println("Файл пуст");
                    }
                }
            } while (inputType == CommandType.ERROR);
        } catch (RecursionException exception) {
            System.out.println("Скрипты не могут вызываться рекурсивно");
            while (!scannerStack.isEmpty()) {
                scanner.close();
                scanner = scannerStack.pop();
            }
            scriptName.clear();
        }catch(NoSuchElementException exception){
            return new Request("exit","",user);
        }
        return new Request(userCommand[0], userCommand[1],user);
    }


    //формируется тип команды
    public CommandType choiceCommand(String[] userCommand) {
        try {
            switch (userCommand[0]) {
                case "":
                    return CommandType.ERROR;
                case "help":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "info":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "show":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "add":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OBJECT;
                case "update":
                    if (userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OBJECT;
                case "remove_by_id":
                    if (userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "clear":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "execute_script":
                    if (userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.SCRIPT;
                case "exit":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "add_if_max":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OBJECT;
                case "add_if_min":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OBJECT;
                case "remove_lower":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OBJECT;
                case "count_less_than_position":
                    if (userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "print_descending":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                case "print_field_ascending_salary":
                    if (!userCommand[1].isEmpty()) throw new IncorrectArgumentException();
                    return CommandType.OK;
                default:
                    System.out.println(userCommand[0] + "не найдена");
                    System.out.println("Введите help для справки");
                    return CommandType.ERROR;
            }
        } catch (IncorrectArgumentException e) {
            return CommandType.OK;
        }
    }
}
