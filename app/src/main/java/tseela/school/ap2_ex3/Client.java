package tseela.school.ap2_ex3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements IClient{
    private Socket socket;
    private PrintWriter out;

    public Client() {}

    @Override
    public boolean connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void send(String message) {
        System.out.println(message);
        out.print(message);
        out.flush();
    }

    @Override
    public void disconnect() {
        try {
            socket.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
