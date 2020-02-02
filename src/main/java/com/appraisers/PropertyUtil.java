package com.appraisers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtil {
    public static String driveMainEmail;

    @Value("${gdrive.main.email}")
    public void setDriveMainEmail(String driveMainEmail){
        PropertyUtil.driveMainEmail = driveMainEmail;
    }
}
