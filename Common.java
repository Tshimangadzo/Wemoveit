package com.example.a800361.shifterapp.common;

import com.example.a800361.shifterapp.Remote.FCMClient;
import com.example.a800361.shifterapp.Remote.IFCMService;
import com.example.a800361.shifterapp.Remote.IGoogleAPI;

public class Common {


    public  static final String driver_tbl = "Drivers";
    public  static final String user_driver_tbl = "DriversInformation";
    public  static final String user_rider_tbl= "Mover";
    public  static final String pick_request_tbl = "PickupRequest";
    public  static final String token_tbl = "Tokens";

    public static final String fcmURL = "https://fcm.googleapis.com";

    public static IFCMService getFCMService(){
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }
}
