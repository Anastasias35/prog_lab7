package Client;

import Client.util.Creator;
import Client.util.NewConsole;

import java.util.Scanner;

public class Main1 {

    public static void main(String[] args){
        System.out.println("Клиент запущен");
        Scanner scanner=new Scanner(System.in);
        Creator creator=new Creator(scanner);
        NewConsole console=new NewConsole(scanner,creator);
        Client client=new Client("localhost", 1616,console);

        client.work();
        scanner.close();
    }
}
