<div id="${confirmDialogId}" class="modal fade bs-modal-lg" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog modal_txtcnter modal-full">
        <div class="row " style="vertical-align: middle; position: fixed;
        top: 10px;
        left: 40px;
        /*bottom: 10px;*/
        min-width: 200px;
        width: 95%;
        height: 550px;
        /*background: whitesmoke;*/
        ">
            <button type="button" class="btn red" style="background: red;float: right;" data-dismiss="modal"
                    aria-hidden="true">Close</button>
            <iframe scrolling="auto" id="${confirmDialogId}_iframe" allowtransparency="true" onload=""
                    src=""
                    style="position: relative;
                    top: 0;
                    bottom: 0;
                    left: 0;
                    right: 0;
                    height: 100%;
                    width: 100%;">

            </iframe>
        </div>

        %{--<div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>

                <h3 class="modal-title">Web Page</h3>
            </div>

            <div class="modal-body">
                <div class="row " style="vertical-align: middle; position: fixed; width: 100%; height: 100%;
                top: 80px;
                left: 40px;
                bottom: 25px;
                min-width: 200px;
                min-height: 500px;
                width: 95%;
                ">
                    <iframe scrolling="auto" id="${confirmDialogId}_iframe" allowtransparency="true" onload="" src="http://google.co.in"
                            style="position: relative;
                            top: 0;
                            bottom: 0;
                            left: 0;
                            right: 0;
                            height: 100%;
                            width: 100%;">

                    </iframe>
                </div>

                <div class="modal-footer">
                    <div class="row">
                        <button type="button" data-dismiss="modal" class="btn red">Cancel</button>
                        <button type="button" data-dismiss="modal" class="btn green">Close</button>
                    </div>
                </div>
            </div>
        </div>--}%
    </div>
</div>