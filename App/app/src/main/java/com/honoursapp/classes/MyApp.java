package com.honoursapp.classes;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaymentConfiguration.init(getApplicationContext(), "pk_test_51IGn26DqlajuIPssAxNemNyw4lRme4XaGO9aOe14IBrvB29WCbuya3H9eogUxcxXBLCimCVhpZEXwbjzOcCLqq1o00mOjdrlI6");
    }
}

