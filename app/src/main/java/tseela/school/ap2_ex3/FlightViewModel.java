package tseela.school.ap2_ex3;

public class FlightViewModel implements IFlightViewModel {
    private IFlightModel model;
    private String ip;
    private int port;
    private double aileron, elevator, rudder, throttle;

    public FlightViewModel() {
        model = new FlightModel();
    }

    @Override
    public boolean connect() {
        return model.connect(ip, port);
    }

    @Override
    public void updateAileron() {
        model.setAileron(aileron);
    }

    @Override
    public void updateElevator() {
        model.setElevator(elevator);
    }

    @Override
    public void updateRudder() {
        model.setRudder(rudder);
    }

    @Override
    public void updateThrottle() {
        model.setThrottle(throttle);
    }

    @Override
    public void disconnect() {
        model.disconnect();
    }
}
