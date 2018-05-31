package com.collegelasalle.felix.accelerometerexample;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private int backgroundColor = Color.BLACK;
    private float gravity[] = new float[3];
    private final float FILTER = 0.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gravity[0] = FILTER * gravity[0] + (1 - FILTER) * event.values[0];
        gravity[1] = FILTER * gravity[1] + (1 - FILTER) * event.values[1];
        gravity[2] = FILTER * gravity[2] + (1 - FILTER) * event.values[2];

        float accelerationX = Math.abs(event.values[0] - gravity[0]);
        float accelerationY = Math.abs(event.values[1] - gravity[1]);
        float accelerationZ = Math.abs(event.values[2] - gravity[2]);

        text1.setText(Float.toString(Math.round(accelerationX)));
        text2.setText(Float.toString(Math.round(accelerationY)));
        text3.setText(Float.toString(Math.round(accelerationZ)));

        float largestChange = 3;
        if (accelerationX > largestChange) {
            largestChange = accelerationX;
            backgroundColor = Color.GREEN;
        }
        if (accelerationY > largestChange) {
            largestChange = accelerationY;
            backgroundColor = Color.BLUE;
        }
        if (accelerationZ > largestChange) {
            backgroundColor = Color.RED;
        }
        findViewById(android.R.id.content).setBackgroundColor(backgroundColor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
