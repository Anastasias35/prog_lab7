package Client;

import Client.util.NewConsole;
import Common.Request;
import Common.exceptions.ConnectionErrorException;
import Common.Response;
import Common.ResponseType;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private String host;
    private int port;
    private NewConsole console;
    private int reconnecting;
    private boolean connection;
    private boolean run;
    private final int maxconnect=5;
    private SocketChannel socketChannel;
  //  private ObjectOutputStream objectOutputStream;



    public Client(String host, int port, NewConsole console){
        this.host=host;
        this.port=port;
        this.console=console;
    }


    public void connect() throws ConnectionErrorException{
        connection=false;
        do{
            try{
                if (reconnecting>0) System.out.println("Переподключение...");
                socketChannel=SocketChannel.open(new InetSocketAddress(host,port));
                System.out.println("Подключение с сервером установлено");
                connection=true;
                reconnecting=0;
            } catch (UnknownHostException e) {
                System.out.println("Неизвестное имя хоста");
            } catch (IOException e) {
                System.out.println("Произошла ошибка при попытке подключения с сервером");
                throw new ConnectionErrorException();
            }
        }while(!connection);
    }

    //доработать
    public void work() {
        run=true;
         while(run){
            try {
                connect();
                run = sendToServer();
            } catch (ConnectionErrorException e) {
                if(reconnecting>=maxconnect){
                    System.out.println("Превышено максимальное количество попыток подключения");
                    System.exit(0);
                }
            }
            reconnecting++;

        }
         System.out.println("Завершение работы");
         System.exit(0);
    }


    //отправка запроса на сервер и получение ответа
    public boolean sendToServer() throws ConnectionErrorException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(65536);
        Request requestToServer = null;
        Response responseFromServer = null;
        do {
            try {
                requestToServer= console.actMode();
                if (!requestToServer.isEmpty()) {
                    byteBuffer.clear();
                    byteBuffer.put(serialization(requestToServer));
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                    socketChannel.read(byteBuffer);
                    responseFromServer = deserialization(byteBuffer.array());
                    System.out.println(responseFromServer.getInf());
                }

            }catch (ClassNotFoundException e) {
                System.out.println("Призошла ошибка при чтении данных");
            }catch(ClassCastException exception){
                System.out.println("Соединение с сервером прервано");
                reconnecting++;
                connect();
                throw new ConnectionErrorException();
            } catch (IOException e) {
            }
        } while (!responseFromServer.getResponseType().equals(ResponseType.EXIT));
        return  false;
    }


    public byte[] serialization(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] byteRequest=byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return byteRequest;
    }


    public Response deserialization(byte[] byteResponse) throws IOException, ClassNotFoundException,ClassCastException {
        Response response=null;
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteResponse);
        ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
        response=(Response)objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }

}
