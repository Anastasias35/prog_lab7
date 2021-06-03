package Server.utilitka;

import Common.Request;
import Common.Response;
import Common.ResponseType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestProcessingThread extends Thread{
    private ProcessingOfRequest processingOfRequest;
    private Request request;
    private SocketChannel socketChannel;
    private Selector selector;
    private ExecutorService fixedThreadPool= Executors.newFixedThreadPool(1);

    public RequestProcessingThread(ProcessingOfRequest processingOfRequest,Request request, SocketChannel socketChannel, Selector selector){
        this.processingOfRequest=processingOfRequest;
        this.request=request;
        this.socketChannel=socketChannel;
        this.selector=selector;
    }

    @Override
    public void run() {
        try {
            Response response = processingOfRequest.getResponse(request);

            fixedThreadPool.submit(() -> {
                try {
                    sendResponse(response);
                    if (response.getResponseType().equals(ResponseType.EXIT)) {
                        System.out.println("Работа с клиентом завершена");
                        socketChannel.close();
                    }
                }catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
            join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void sendResponse(Response response) throws IOException{
        ByteBuffer byteBuffer= ByteBuffer.allocate(65536);
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
}
