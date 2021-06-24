package tseela.school.ap2_ex3;

public class FlightViewModel implements IFlightViewModel {
    private static final int SEEK_BAR_TICK_SCALAR = 100;

    private IFlightModel model;
    private String ip;
    private int port;
    private int aileron, elevator, rudder, throttle;

    public FlightViewModel() {
        model = new FlightModel();
    }

    @Override
    public boolean connect() {
        return model.connect(ip, port);
    }

    @Override
    public void updateAileron() {
        model.setAileron((double)aileron / SEEK_BAR_TICK_SCALAR);
    }

    @Override
    public void updateElevator() {
        model.setElevator((double)elevator / SEEK_BAR_TICK_SCALAR);
    }

    @Override
    public void updateRudder() {
        model.setRudder((double)rudder / SEEK_BAR_TICK_SCALAR);
    }

    @Override
    public void updateThrottle() {
        model.setThrottle((double)throttle / SEEK_BAR_TICK_SCALAR);
    }

    @Override
    public void disconnect() {
        model.disconnect();
    }
}
