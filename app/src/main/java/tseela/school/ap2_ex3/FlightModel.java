package tseela.school.ap2_ex3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FlightModel implements IFlightModel{
    private Client fg;
    private ExecutorService executorService;
    private boolean isConnected;

    public FlightModel() {
        fg = new Client();
        isConnected = false;
    }

    @Override
    public boolean connect(String ip, int port) {
        disconnect();
        executorService = Executors.newFixedThreadPool(1);
        Future<Boolean> status = executorService.submit(()-> {
            return fg.connect(ip, port);
        });
        try {
            isConnected = status.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void setAileron(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                fg.send("set /controls/flight/aileron " + value + "\r\n");
            });
        }
    }

    @Override
    public void setElevator(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                fg.send("set /controls/flight/elevator " + value + "\r\n");
            });
        }
    }

    @Override
    public void setRudder(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                fg.send("set /controls/flight/rudder " + value + "\r\n");
            });
        }
    }

    @Override
    public void setThrottle(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                fg.send("set /controls/flight/current-engine/throttle " + value + "\r\n");
            });
        }
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            executorService.shutdown();
            fg.disconnect();
            isConnected = false;
        }
    }
}