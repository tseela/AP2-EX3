package tseela.school.ap2_ex3;

public class FlightViewModel {
    private static final int SEEK_BAR_TICK_SCALAR = 100;

    private IFlightModel model;
    public String ip, port;
    public int rudder, throttle;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getRudder() {
        return rudder;
    }

    public void setRudder(int rudder) {
        this.rudder = rudder;
        model.setRudder((double)rudder / SEEK_BAR_TICK_SCALAR);
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
        model.setThrottle((double)throttle / SEEK_BAR_TICK_SCALAR);
    }

    public FlightViewModel() {
        model = new FlightModel();
    }

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

    public void setAileron(double aileron) {
        model.setAileron(aileron);
    }

    public void setElevator(double elevator) {
        model.setElevator(elevator);
    }

    public void disconnect() {
        model.disconnect();
    }
}
