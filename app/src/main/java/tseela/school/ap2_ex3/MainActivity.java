package tseela.school.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etIP, etPort;
    private Button btnConnect;
    private Joystick joystick;
    private SeekBar sbThrottle, sbRudder;

    private IFlightViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new FlightViewModel();

        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(this);
        joystick = findViewById(R.id.joystick);
        joystick.onChange = (double aileron, double elevator)->{
            viewModel.setAileron(aileron);
            viewModel.setElevator(elevator);
        };
        sbThrottle = findViewById(R.id.sbThrottle);
        sbThrottle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.updateThrottle();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        sbRudder = findViewById(R.id.sbRudder);
        sbRudder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.updateRudder();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnConnect) {
            if (!viewModel.connect()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Unable to Connect", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}