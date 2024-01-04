package com.example.aquacount;

import com.example.aquacount.models.CoordinateEntity;
import com.example.aquacount.models.LoginResponse;
import com.example.aquacount.models.MeasurementEntity;
import com.example.aquacount.models.MeasurementUpdateEntity;
import com.example.aquacount.models.RouteEntity;
import com.example.aquacount.request.MeasurementRequest;
import com.example.aquacount.request.UserRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("register")

    //on below line we are creating a method to post our data.
    Call<Void> createPost(@Body MeasurementRequest measurementRequest);

    @POST("login")

    Call<LoginResponse> createLoginPost(@Body UserRequest userRequest);

    @GET("update/{counterId}")

    Call<List<MeasurementUpdateEntity>> getAllMeasurements(@Path("counterId") long counterId);

    @PUT("updateValue/{measurementId}")

    Call<Void> updateMeasurementValue(@Path("measurementId") Long measurementId, @Body Long newValue);


    @GET("clocks/{routeid}")
    Call<List<CoordinateEntity>> getCoordinatesByRouteId(@Path("routeid")Long routeId);

    @GET("routes/{counterid}")
    Call<List<RouteEntity>> getRouteFromCounterid(@Path("counterid")Long counterId);

    @GET("clocks/getClockids/{routeid2}")
    Call<List<Long>> getClocksByRouteId(@Path("routeid2")Long routeId);
}
