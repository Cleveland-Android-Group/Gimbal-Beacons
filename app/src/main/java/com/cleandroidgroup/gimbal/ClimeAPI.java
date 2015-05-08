package com.cleandroidgroup.gimbal;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by daveshah on 5/8/15.
 */
public interface ClimeAPI {

    @POST("/widgets/{sensorId}")
    Void sendSensorData(@Path("sensorId")String sensorId, @Body SensorReading reading);

}
