package tseela.school.ap2_ex3;

public interface IClient {
    // returns whether or not the connection was successful
    boolean connect(String ip, int port);

    void send(String message);

    void disconnect();
}
