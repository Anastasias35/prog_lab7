package Client.util;

import Common.Request;
import Common.exceptions.IncorrectVariableException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Entrance {

    private final String login="login";
    private final String registration="registration";
    private Scanner scanner;


    public Entrance(Scanner scanner){
        this.scanner=scanner;
    }

    public Request createLoginRequest(){
        String command=askAboutRegistration() ? login :registration;
        return new Request(command,"",null,new User(askLogin(),askPassword()));

    }

    public String askLogin(){
        String login1;
        while(true) {
            try {
                System.out.println("Введите логин:");
                login1 = scanner.nextLine().trim();
                if (login1.isEmpty()) throw new NoSuchElementException();
                break;

            }catch(NoSuchElementException exception){
                System.out.println("Логин не может быть пустым");
            }
        }
        return login1;
    }

    public String askPassword(){
        String password1;
        while(true){
            try{
                System.out.println("Введите пароль:");
                password1=scanner.nextLine().trim();
                if(password1.isEmpty()) throw  new NoSuchElementException();
                break;
            }catch (NoSuchElementException exception){
                System.out.println("Пароль не может быть пустым");
            }
        }
        return password1;
    }

    public boolean askAboutRegistration(){
        boolean question;
        while(true){
            try{
                System.out.println("У вас уже есть учетная запись?(Да/Нет)");
                String question1=scanner.nextLine().trim();
                if (question1.equals("Да")) return  true;
                else if (question1.equals("Нет")) return  false;
                else throw new IncorrectVariableException();
            }catch (IncorrectVariableException exception){
                System.out.println("Неверный формат ответа");
            }
        }
    }



}
