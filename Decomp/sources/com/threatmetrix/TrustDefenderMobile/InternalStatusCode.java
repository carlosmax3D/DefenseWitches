package com.threatmetrix.TrustDefenderMobile;

public enum InternalStatusCode {
    THM_NotYet("Not Yet", "Profile request has successfully started but is not completed"),
    THM_OK("Okay", "Completed, No errors"),
    THM_Connection_Error("Connection Error", "There has been a connection issue, profiling aborted"),
    THM_HostNotFound_Error("Host Not Found", "Unable to resolve the host name"),
    THM_NetworkTimeout_Error("Network Timeout", "Communications layer timed out"),
    THM_Internal_Error("Internal Error", "Internal Error, profiling incomplete or interrupted"),
    THM_HostVerification_Error("Host Verification Error", "Certificate verification failure! Potential MITM attack"),
    THM_Interrupted_Error("Interrupted", "Request was cancelled"),
    THM_InvalidOrgID("Invalid ORG ID", "Request contained an invalid org id"),
    THM_ConfigurationError("Configuration Error", "Failed to retrieve configuration from server"),
    THM_PartialProfile("Partial Profile", "Connection error, only partial profile completed");
    
    private final String desc;
    private final String label;

    private InternalStatusCode(String str, String str2) {
        this.label = str;
        this.desc = str2;
    }

    public final String getDesc() {
        return this.desc;
    }

    public final String toString() {
        return this.label;
    }
}
