package bwchecker_fla {
    import flash.utils.*;
    import adobe.utils.*;
    import flash.accessibility.*;
    import flash.display.*;
    import flash.errors.*;
    import flash.events.*;
    import flash.external.*;
    import flash.filters.*;
    import flash.geom.*;
    import flash.media.*;
    import flash.net.*;
    import flash.printing.*;
    import flash.system.*;
    import flash.text.*;
    import flash.ui.*;
    import flash.xml.*;
    import fl.controls.*;

    public dynamic class MainTimeline extends MovieClip {

        public var counter:Number;
        public var bwList:List;
        public var goButton:Button;
        public var bwInterval:Number;
        public var nc:NetConnection;
        public var playerVersion:TextField;
        public var connectStr:TextInput;

        public function MainTimeline(){
            addFrameScript(0, frame1);
            __setProp_goButton_Scene1_Layer1_1();
        }
        public function ncOnBWDone(_arg1:Number, _arg2:Number, _arg3:Number, _arg4:Number){
            var _local5:Number;
            var _local6:String;
            trace(((((((("onBWDone: kbitDown:" + _arg1) + " deltaDown:") + _arg2) + " deltaTime:") + _arg3) + " latency:") + _arg4));
            _local5 = _arg1;
            bwInterval = setInterval(doBWCheck, 5000);
            _local6 = (((("download speed: " + _arg1) + "Kbps  latency: ") + _arg4) + "ms");
            bwList.replaceItemAt({label:_local6}, (bwList.length - 1));
        }
        function frame1(){
            bwInterval = 0;
            nc = null;
            counter = 0;
            playerVersion.text = (Capabilities.version + " (Flash-AS3)");
            connectStr.text = "rtmp://localhost/bwcheck";
            goButton.addEventListener(MouseEvent.CLICK, doGo);
        }
        function __setProp_goButton_Scene1_Layer1_1(){
            try {
                goButton["componentInspectorSetting"] = true;
            } catch(e:Error) {
            };
            goButton.emphasized = false;
            goButton.enabled = true;
            goButton.label = "Start";
            goButton.labelPlacement = "right";
            goButton.selected = false;
            goButton.toggle = false;
            goButton.visible = true;
            try {
                goButton["componentInspectorSetting"] = false;
            } catch(e:Error) {
            };
        }
        public function ncOnStatus(_arg1:NetStatusEvent){
            trace(((("Level: " + _arg1.info.level) + " Code: ") + _arg1.info.code));
            if (_arg1.info.code == "NetConnection.Connect.Success"){
                trace(("--- connected to: " + this.uri));
                bwList.replaceItemAt({label:"testing performance..."}, (bwList.length - 1));
            };
        }
        public function doBWCheck(){
            clearInterval(bwInterval);
            counter = 0;
            nc.call("checkBandwidth", null);
            bwList.dataProvider.addItem({label:"testing performance..."});
        }
        public function doGo(_arg1:MouseEvent){
            var _local2:Object;
            if (nc == null){
                nc = new NetConnection();
                nc.addEventListener(NetStatusEvent.NET_STATUS, ncOnStatus);
                _local2 = new Object();
                nc.client = _local2;
                _local2.onBWDone = ncOnBWDone;
                _local2.onBWCheck = ncOnBWCheck;
                bwList.addItem({label:"connecting..."});
                nc.connect(connectStr.text, true);
                goButton.label = "Stop";
            } else {
                nc.close();
                nc = null;
                goButton.label = "Start";
            };
        }
        public function ncOnBWCheck(_arg1){
            trace("onBWCheck");
            return (++counter);
        }

    }
}//package bwchecker_fla 
