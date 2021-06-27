package tseela.school.ap2_ex3;

import java.util.concurrent.ExecutionException;

public interface IFlightViewModel {
    boolean connect() throws ExecutionException, InterruptedException;

    void setAileron(double aileron);

    void setElevator(double elevator);

    void updateRudder();

    void updateThrottle();

    void disconnect();
}
