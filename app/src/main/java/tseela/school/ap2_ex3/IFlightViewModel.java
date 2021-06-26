package tseela.school.ap2_ex3;

public interface IFlightViewModel {
    boolean connect();

    void setAileron(double aileron);

    void setElevator(double elevator);

    void updateRudder();

    void updateThrottle();

    void disconnect();
}
