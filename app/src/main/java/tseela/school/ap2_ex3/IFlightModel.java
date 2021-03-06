package tseela.school.ap2_ex3;

import java.util.concurrent.ExecutionException;

public interface IFlightModel {
    // return whether or not the connection was successful
    boolean connect(String ip, int port) throws ExecutionException, InterruptedException;

    boolean isConnected();

    void setAileron(double value);

    void setElevator(double value);

    void setRudder(double value);

    void setThrottle(double value);

    void disconnect();
}
