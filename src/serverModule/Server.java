package serverModule;

import common.utility.Request;
import common.utility.Response;
import common.utility.ResponseCode;
import serverModule.utility.RequestManager;

import java.io.*;
import java.net.*;

public class Server {
    private int port;
    private RequestManager requestManager;
    private DatagramSocket socket;
    private SocketAddress address;

    public Server(int port, RequestManager requestManager) {
        this.port = port;
        this.requestManager = requestManager;
    }

    public void run() throws IOException, ClassNotFoundException {
        System.out.println("Запуск сервера!");
        boolean processStatus = true;
        while (processStatus) {
            processStatus = processingClientRequest();
        }
    }

    private boolean processingClientRequest(){
        Request request = null;
        Response response = null;
        try {
            socket = new DatagramSocket(8090);
            do {
                byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
                DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
                socket.receive(getPacket);
                request = deserialize(getPacket, getBuffer);
                System.out.println("Получена команда '" + request.getCommandName() + "'");
                response = executeRequest(request);
                System.out.println("Команда '" + request.getCommandName() + "' выполнена");
                byte[] sendBuffer = serialize(response);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, getPacket.getAddress(), getPacket.getPort());
                socket.send(sendPacket);
            } while (response.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        } catch (IOException | ClassNotFoundException exception) {}
        return true;
    }

    private Response executeRequest(Request request) {
        Response response = requestManager.manage(request);
        return response;
    }

    private Request deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    private byte[] serialize(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
}
