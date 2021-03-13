package clientModule;

import clientModule.utility.Console;
import common.utility.Request;
import common.utility.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    private String host;
    private int port;
    private Console console;
    private DatagramChannel datagramChannel;
    private SocketAddress address;

    public Client(String host, int port, Console console) {
        this.host = host;
        this.port = port;
        this.console = console;
    }

    public void run() throws IOException, ClassNotFoundException {
        Request requestToServer = null;
        Response serverResponse = null;
        //try {
            datagramChannel = DatagramChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            datagramChannel.connect(address);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            do {
                requestToServer = console.interactiveMode();
                if (requestToServer.isEmpty()) continue;
                byteBuffer.put(serialize(requestToServer));
                byteBuffer.flip();
                datagramChannel.send(byteBuffer, address);
                byteBuffer.clear();
                datagramChannel.receive(byteBuffer);
                byteBuffer.flip();
                serverResponse = deserialize(byteBuffer);
                byteBuffer.clear();
                System.out.println(serverResponse.getResponseBody());
            } while(!requestToServer.getCommandName().equals("exit"));
            System.out.println("Работа клиента успешно завершена!");
        //} catch (Exception e) {}
    }

    private byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }

    private Response deserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }
}
