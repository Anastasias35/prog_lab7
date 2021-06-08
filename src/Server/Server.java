package Server;

import Common.Request;
import Server.utilitka.ProcessingOfRequest;
import Common.Response;
import Common.ResponseType;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;
    private Console console;
    private Selector selector;
    private SocketAddress socketAddress;
    ProcessingOfRequest processingOfRequest;

    public Server(int port, ProcessingOfRequest processingOfRequest){
        this.port=port;
        this.processingOfRequest=processingOfRequest;
    }

    public void connection(ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel channel = null;
        try {
            while (channel == null) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    if (selectionKey.isAcceptable()) {
                        channel = serverSocketChannel.accept();
                        System.out.println("Соединение с клиентом установлено");
                        selectionKeys.remove(selectionKey);
                        if (channel != null) {
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                        } else break;
                    }
                }
                return;
            }
        } catch (BindException exception) {
            System.out.print("Порт занят,  попробуйте смените значение порта в программе");
            System.exit(0);
        }
    }

    public Request getRequest() throws IOException,ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
        Request request = null;
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                if (selectionKey.isReadable()) {
                    socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    if (byteBuffer.hasRemaining()) {
                        System.out.println("Чтение запроса...");
                        request=deserialization(byteBuffer.array());
                        System.out.println("Обратывается команда " + request.getCommand());
                    }
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                    byteBuffer.clear();
                    selectionKeys.remove(selectionKey);
                    return request;

                }
            }
        }

    }

    public void sendResponse(Response response) throws IOException {
        ByteBuffer byteBuffer=ByteBuffer.allocate(65536);
        byte[]  byte1;
        socketChannel=null;
        selector.select();
        Set<SelectionKey> keys=selector.selectedKeys();
        for(SelectionKey selectionKey:keys){
            if(selectionKey.isWritable()){
                socketChannel=(SocketChannel) selectionKey.channel();
                byte1=serialization(response);
                byteBuffer.put(byte1);
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
                System.out.println("Отправка ответа клиенту");
                byteBuffer.clear();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_READ);
                keys.remove(selectionKey);
            }
        }
    }

    public void work() {
        try {
            System.out.println("Запуск сервера");
            System.out.println("Сервер успешно запущен");
            Scanner scanner =new Scanner(System.in);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Завершаю программу.");
            }));


            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverSocketChannel.configureBlocking(false);
            while (true) {
                ExecutorService fixedThreadPool= Executors.newFixedThreadPool(3);
                connection(serverSocketChannel);
                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Request request=getRequest();
                            Runnable runnable1=new Runnable() {
                                @Override
                                public void run() {
                                    if (request!=null) {
                                        Response response = processingOfRequest.getResponse(request);
                                        fixedThreadPool.submit(() -> {
                                            try {
                                                sendResponse(response);
                                                if (response.getResponseType().equals(ResponseType.EXIT)) {
                                                    System.out.println("Работа с клиентом завершена");
                                                    socketChannel.close();
                                                }
                                            } catch (IOException exception) {
                                                exception.printStackTrace();
                                            }
                                        });
                                        fixedThreadPool.shutdown();
                                    }
                                }
                            };
                            Thread thread1=new Thread(runnable1);
                            thread1.start();
                            thread1.join();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        } catch (ClassNotFoundException exception) {
                            exception.printStackTrace();
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    }
                };
                Thread thread=new Thread(runnable);
                thread.start();
                thread.join();
            }
        }catch (IOException exception){
            System.out.println();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public byte[] serialization(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] byteRequest=byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return byteRequest;
    }

    public Request deserialization(byte[] byteRequest) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteRequest);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }
}