package tseela.school.ap2_ex3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlightModel implements IFlightModel{
    private Socket fg;
    private OutputStream fgOutputStream;
    private PrintWriter out = null;
    private ExecutorService executorService;

    public FlightModel(String ip, int port) {
        executorService = Executors.newFixedThreadPool(1);
        try {
            fg = new Socket(ip, port);
            fgOutputStream = fg.getOutputStream();
            out = new PrintWriter(fgOutputStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAileron(double value) {
        if (out != null) {
            executorService.submit(()->{
                out.print("set /controls/flight/aileron " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void setElevator(double value) {
        if (out != null) {
            executorService.submit(()->{
                out.print("set /controls/flight/elevator " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void setRudder(double value) {
        if (out != null) {
            executorService.submit(()->{
                out.print("set /controls/flight/rudder " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void setThrottle(double value) {
        if (out != null) {
            executorService.submit(()->{
                out.print("set /controls/flight/current-engine/throttle " + value + "\r\n");
                out.flush();
            });
        }
    }

    @Override
    public void close() {
        executorService.shutdown();
        try {
            fg.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
