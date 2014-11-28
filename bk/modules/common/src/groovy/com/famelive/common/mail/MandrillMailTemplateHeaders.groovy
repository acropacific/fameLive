package com.famelive.common.mail

//Reference: http://help.mandrill.com/entries/21688056-Using-SMTP-Headers-to-customize-your-messages#implementation

public enum MandrillMailTemplateHeaders {

    X_MC_Track("X-MC-Track"),
    X_MC_Autotext("X-MC-Autotext"),
    X_MC_AutoHtml("X-MC-AutoHtml"),
    X_MC_Template("X-MC-Template"),
    X_MC_MergeVars("X-MC-MergeVars"),
    X_MC_GoogleAnalytics("X-MC-GoogleAnalytics"),
    X_MC_GoogleAnalyticsCampaign("X-MC-GoogleAnalyticsCampaign"),
    X_MC_Metadata("X-MC-Metadata"),
    X_MC_URLStripQS("X-MC-URLStripQS"),
    X_MC_PreserveRecipients("X-MC-PreserveRecipients"),
    X_MC_InlineCSS("X-MC-InlineCSS"),
    X_MC_TrackingDomain("X-MC-TrackingDomain"),
    X_MC_SigningDomain("X-MC-SigningDomain"),
    X_MC_Subaccount("X-MC-Subaccount"),
    X_MC_ViewContentLink("X-MC-ViewContentLink"),
    X_MC_BccAddress("X-MC-BccAddress"),
    X_MC_Important("X-MC-Important"),
    X_MC_IpPool("X-MC-IpPool"),
    X_MC_ReturnPathDomain("X-MC-ReturnPathDomain"),
    X_MC_SendAt("X-MC-SendAt")

    String value;

    MandrillMailTemplateHeaders(String value) {
        this.value = value;
    }

}