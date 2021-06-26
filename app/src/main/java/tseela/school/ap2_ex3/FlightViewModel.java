package tseela.school.ap2_ex3;

public class FlightViewModel implements IFlightViewModel {
    private static final int SEEK_BAR_TICK_SCALAR = 100;

    private IFlightModel model;
    public String ip, port;
    public int rudder, throttle;

    public FlightViewModel() {
        model = new FlightModel();
    }

    @Override
    public boolean connect() {
        int portNum;
        try {
            portNum = Integer.parseInt(port);
            return model.connect(ip, portNum);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setAileron(double aileron) {
        model.setAileron(aileron);
    }

    @Override
    public void setElevator(double elevator) {
        model.setElevator(elevator);
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
