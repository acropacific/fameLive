package CtoSBWCheck_fla {
    import flash.events.*;
    import flash.display.*;
    import flash.net.*;
    import adobe.utils.*;
    import flash.accessibility.*;
    import flash.errors.*;
    import flash.external.*;
    import flash.filters.*;
    import flash.geom.*;
    import flash.media.*;
    import flash.printing.*;
    import flash.system.*;
    import flash.text.*;
    import flash.ui.*;
    import flash.utils.*;
    import flash.xml.*;

    public dynamic class MainTimeline extends MovieClip {

        public var i;
        public var payload;
        public var stats;
        public var res;
        public var nc:NetConnection;
        public var deltaUp;
        public var timePassed;
        public var now;
        public var deltaTime;
        public var kbitUp;

        public function MainTimeline(){
            addFrameScript(0, frame1);
        }
        function frame1(){
            nc = new NetConnection();
            nc.addEventListener(NetStatusEvent.NET_STATUS, ncOnStatus);
            nc.connect("rtmp://localhost/bwcheck", null);
            payload = new Array();
            i = 0;
            while (i < 1200) {
                payload[i] = Math.random();
                i++;
            };
            res = new Object();
            res.latency = 0;
            res.cumLatency = 1;
            res.bwTime = 0;
            res.count = 0;
            res.sent = 0;
            res.kbitUp = 0;
            res.deltaUp = 0;
            res.deltaTime = 0;
            res.pakSent = new Array();
            res.pakRecv = new Array();
            res.beginningValues = {};
            res.onResult = function (_arg1:Object){
                trace("ClientBWResult: ");
                now = (new Date().getTime() / 1);
                if (res.sent == 0){
                    res.beginningValues = _arg1;
                    res.beginningValues.time = now;
                    var _local2 = res.sent++;
                    res.pakSent[_local2] = now;
                    nc.call("onClientBWCheck", new Responder(res.onResult), now);
                } else {
                    res.pakRecv[res.count] = now;
                    trace(("Packet interval = " + ((res.pakRecv[res.count] - res.pakSent[res.count]) * 1)));
                    res.count++;
                    timePassed = (now - res.beginningValues.time);
                    if (res.count == 1){
                        res.latency = Math.min(timePassed, 800);
                        res.latency = Math.max(res.latency, 10);
                        res.overhead = (_arg1.cOutBytes - res.beginningValues.cOutBytes);
                        trace(("overhead: " + res.overhead));
                        _local2 = res.sent++;
                        res.pakSent[_local2] = now;
                        nc.call("onClientBWCheck", new Responder(res.onResult), now, payload);
                    };
                    trace(((((((("count: " + res.count) + " sent: ") + res.sent) + " timePassed: ") + timePassed) + " latency: ") + res.latency));
                    if ((((res.count >= 1)) && ((timePassed < 1000)))){
                        _local2 = res.sent++;
                        res.pakSent[_local2] = now;
                        res.cumLatency++;
                        nc.call("onClientBWCheck", new Responder(res.onResult), now, payload);
                    } else {
                        if (res.sent == res.count){
                            if (res.latency >= 100){
                                if ((res.pakRecv[1] - res.pakRecv[0]) > 1000){
                                    res.latency = 100;
                                };
                            };
                            payload = null;
                            System.gc();
                            stats = _arg1;
                            deltaUp = (((stats.cOutBytes - res.beginningValues.cOutBytes) * 8) / 1000);
                            deltaTime = (((now - res.beginningValues.time) - (res.latency * res.cumLatency)) / 1000);
                            if (deltaTime <= 0){
                                deltaTime = ((now - res.beginningValues.time) / 1000);
                            };
                            kbitUp = Math.round((deltaUp / deltaTime));
                            trace(((((((((("onBWDone: kbitUp = " + kbitUp) + ", deltaUp= ") + deltaUp) + ", deltaTime = ") + deltaTime) + ", latency = ") + res.latency) + " KBytes ") + ((stats.cOutBytes - res.beginningValues.cOutBytes) / 0x0400)));
                        };
                    };
                };
            };
        }
        public function ncOnStatus(_arg1:NetStatusEvent){
            trace(_arg1.info.code);
            if (_arg1.info.code == "NetConnection.Connect.Success"){
                doClientBWCheck();
            };
        }
        public function doClientBWCheck(){
            nc.call("onClientBWCheck", new Responder(res.onResult), null);
            trace("testing CtoS performance...");
        }

    }
}//package CtoSBWCheck_fla 
