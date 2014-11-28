package com.famelive

import com.pubnub.api.Callback
import com.pubnub.api.PubnubError


class CustomPubnubCallback extends Callback {

    public void successCallback(String channel1, Object response) {
//        System.out.println(response.toString());
    }

    public void errorCallback(String channel1, PubnubError error) {
//        System.out.println(error.toString());
    }
}
