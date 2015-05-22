package no.westerdals.student.vegeiv13.pg4600.assignment2.kimjfx.utils;

import javafxports.android.FXActivity;

import java.io.File;

public class OSHandler {
    private OSHandler() {
    }

    public static final boolean IS_ANDROID = "android".equals(System.getProperty("javafx.platform")) || "Dalvik".equals(System.getProperty("java.vm.name"));
    public static final boolean IS_DESKTOP = !IS_ANDROID;

    public static File getWorkingDirectory() {
        if(IS_ANDROID) {
            FXActivity instance = FXActivity.getInstance();
            return new File(instance.getFilesDir(), "kimjfx.db");
        } else if(IS_DESKTOP) {
            return new File("kimjfx.db");
        }
        throw new RuntimeException("Could not set up working directory");
    }
}
