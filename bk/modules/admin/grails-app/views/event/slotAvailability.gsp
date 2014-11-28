<%@ page import="com.famelive.admin.util.AdminDateTimeUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name='layout' content='admin'/>
    <title>Check Slot Availability</title>
</head>

<body>
<g:form controller="event" action="checkSlotAvailability" class="form-horizontal">
    <div class="form-body">
        <h3 class="form-section">Check Slot Availability</h3>

        <div class="form-group">
            <label class="control-label col-md-3">Start Date Time</label>

            <div class="col-md-3">
                <div class="input-group input-medium date date-picker" data-date-format="dd/mm/yyyy"
                     data-date-start-date="+0d">
                    <g:textField name="startDate" class="form-control" readonly="readonly"
                                 value="${AdminDateTimeUtils.defaultStartDate}"/>
                    <span class="input-group-btn">
                        <button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
                    </span>
                </div>
            </div>

            <div class="col-md-3">
                <div class="input-group">
                    <g:textField name="startTime" class="form-control timepicker timepicker-24-no-seconds"
                                 readonly="readonly"/>
                    <span class="input-group-btn">
                        <button class="btn default" type="button"><i class="fa fa-clock-o"></i></button>
                    </span>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-md-3">Duration</label>

            <div class="col-md-4">
                <g:select from="${checkSlotV0?.duration}" optionKey="" name="duration" class="form-control"/>
            </div>
        </div>


        <div class="form-group">
            <div class="col-md-7">
                <div class="pull-right">
                    <g:submitButton name="Check" class="btn green"/>
                </div>
            </div>
        </div>
    </div>
</g:form>
<g:if test="${flash.message}">
    <h3>${flash.message}</h3>
</g:if>
</body>
</html>