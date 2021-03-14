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
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);

    public Client(String host, int port, Console console) {
        this.host = host;
        this.port = port;
        this.console = console;
    }

    public void run(String fileName){
        try {
            datagramChannel = DatagramChannel.open();
            address = new InetSocketAddress("localhost", this.port);
            datagramChannel.connect(address);
            Request loadCollection = new Request("loadCollection", fileName);
            sendRequest(loadCollection);
            Response responseLoading = getResponse();
            System.out.print(responseLoading.getResponseBody());
            Request requestToServer = null;
            Response serverResponse = null;
            do {
                requestToServer = serverResponse != null ? console.interactiveMode(serverResponse.getResponseCode()) :
                        console.interactiveMode(null);
                if (requestToServer.isEmpty()) continue;
                sendRequest(requestToServer);
                serverResponse = getResponse();
                System.out.print(serverResponse.getResponseBody());
            } while(!requestToServer.getCommandName().equals("exit"));
            System.out.println("Работа клиента успешно завершена!");
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("Произошла ошибка при работе с сервером!");
            System.exit(0);
        }
    }

    private void sendRequest(Request requestToServer) throws IOException {
        byteBuffer.put(serialize(requestToServer));
        byteBuffer.flip();
        datagramChannel.send(byteBuffer, address);
        byteBuffer.clear();
    }

    private Response getResponse() throws IOException, ClassNotFoundException {
        datagramChannel.receive(byteBuffer);
        byteBuffer.flip();
        Response serverResponse = deserialize(byteBuffer);
        byteBuffer.clear();
        return serverResponse;
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
