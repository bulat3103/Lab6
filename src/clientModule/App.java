package clientModule;

import clientModule.utility.Console;
import common.exceptions.NotDeclaredValueException;
import common.exceptions.WrongAmountOfParametersException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class App {
    private static String host;
    private static int port;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //if (!checkAddress(args)) return;
        System.out.println("Клиент запущен");
        Scanner scanner = new Scanner(System.in);
        Console console = new Console(scanner);
        Client client = new Client("localhost", 8090, console);
        client.run();
        scanner.close();
    }

    private static boolean checkAddress(String[] address) {
        try {
            if (address.length != 2) throw new WrongAmountOfParametersException();
            host = address[0];
            port = Integer.parseInt(address[1]);
            if (port < 0) throw new NotDeclaredValueException();
            return true;
        } catch (WrongAmountOfParametersException e) {
            System.out.println("Нужно передавать 2 параметра!");
        } catch (NotDeclaredValueException e) {
            System.out.println("Значение порта не может быть отрицательным числом!");
        } catch (NumberFormatException e) {
            System.out.println("Порт должен быть представлен числом!");
        }
        return false;
    }
}

