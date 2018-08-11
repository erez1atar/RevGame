package games.e.reversi.Features;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import games.e.reversi.utility.App;


/**
 * Created by LENOVO on 15/03/2016.
 */
public class MyAccelometer implements SensorEventListener
{
    private int speedLimit;
    private SensorManager mSensorManager;
    private Sensor mLight;
    private MyAccelometerListener listener;
    private float lastX = 0;
    private float lastY = 0;
    private  float lastZ = 0;
    long lastUpdate = 0;

    public MyAccelometer(int speedLimit)
    {
        this.speedLimit = speedLimit;
        mSensorManager = (SensorManager) App.Instance.getSystemService(App.Instance.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void start()
    {
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop()
    {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Log.d("onSensorChanged", "" +event.accuracy);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float speed = Math.abs(x + y + z - lastX - lastY - lastZ)/ diffTime * 10000;

            if (speed > this.speedLimit)
            {
                if(listener != null)
                {
                    listener.onEvent();
                }
            }

        }
        lastX = x;
        lastY = y;
        lastZ = z;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            Log.d("onSensorChanged", "" + accuracy);
        }
    }

    public interface MyAccelometerListener
    {
        void onEvent();
    }

    public void setListener(MyAccelometerListener newListener)
    {
        this.listener = newListener;
    }
}
