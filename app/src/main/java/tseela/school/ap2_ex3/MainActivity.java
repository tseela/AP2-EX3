package tseela.school.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import tseela.school.ap2_ex3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnConnect;
    private Joystick joystick;

    private FlightViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new FlightViewModel();
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);

        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(this);
        joystick = findViewById(R.id.joystick);
        joystick.onChange = (double aileron, double elevator)->{
            viewModel.setAileron(aileron);
            viewModel.setElevator(elevator);
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnConnect) {
            if (viewModel.connect()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Unable to Connect", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}