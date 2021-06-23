package tseela.school.ap2_ex3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlightModel implements IFlightModel{
    private Socket fg;
    private PrintWriter out = null;
    private ExecutorService executorService;
    private boolean isConnected;

    public FlightModel() {
        isConnected = false;
    }

    @Override
    public boolean connect(String ip, int port) {
        disconnect();
        executorService = Executors.newFixedThreadPool(1);
        try {
            fg = new Socket(ip, port);
            out = new PrintWriter(fg.getOutputStream(), true);
            isConnected = true;
        } catch (IOException e) {
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
                out.print("set /controls/flight/aileron " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void setElevator(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                out.print("set /controls/flight/elevator " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void setRudder(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                out.print("set /controls/flight/rudder " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void setThrottle(double value) {
        if (isConnected()) {
            executorService.submit(()->{
                out.print("set /controls/flight/current-engine/throttle " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            executorService.shutdown();
            try {
                fg.close();
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            isConnected = false;
        }
    }
}