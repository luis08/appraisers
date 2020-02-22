package com.appraisers.app.gbuckets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GDrivePropertyUtil {

    public static String gdriveTokenUrl;
    public static String gdriveApiRefreshToken;
    public static String gdriveApiClientId;
    public static String gdriveClientSecret;

    @Value("${gdrive.token.url}")
    public void setGdriveTokenUrl(String gdriveTokenUrl) {
        GDrivePropertyUtil.gdriveTokenUrl = gdriveTokenUrl;
    }

    @Value("${gdrive.api.refreshtoken}")
    public void setGdriveApiRefreshToken(String gdriveApiRefreshToken) {
        GDrivePropertyUtil.gdriveApiRefreshToken = gdriveApiRefreshToken;
    }

    @Value("${gdrive.api.client-id}")
    public void setGdriveApiClientId(String gdriveApiClientId) {
        GDrivePropertyUtil.gdriveApiClientId = gdriveApiClientId;
    }

    @Value("${gdrive.api.client-secret}")
    public void setGdriveClientSecret(String gdriveClientSecret) {
        GDrivePropertyUtil.gdriveClientSecret = gdriveClientSecret;
    }
}
