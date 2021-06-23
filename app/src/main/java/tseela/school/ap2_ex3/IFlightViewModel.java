package tseela.school.ap2_ex3;

public interface IFlightViewModel {
    boolean connect();

    void updateAileron();

    void updateElevator();

    void updateRudder();

    void updateThrottle();

    void disconnect();
}
