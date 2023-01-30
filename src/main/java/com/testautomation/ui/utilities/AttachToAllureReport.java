package com.testautomation.ui.utilities;

import com.testautomation.ui.config.DeviceConfig;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AttachToAllureReport {

    public static void attachScreenshot(String filename) throws IOException {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String baselineImageDirPath = resourceDirectory.toFile().getAbsolutePath();
        String baselineImageDirFullPath = baselineImageDirPath + "/baselineImages/" + DeviceConfig.getExecutionPlatform() + "/";
        Allure.addAttachment("expected " + filename, FileUtils.openInputStream(new File(baselineImageDirFullPath + filename + ".png")));
        Allure.addAttachment("actual " + filename, FileUtils.openInputStream(new File(baselineImageDirFullPath + filename + "_actual" + ".png")));
        Allure.addAttachment("diff " + filename, FileUtils.openInputStream(new File(baselineImageDirFullPath + filename + "_diffImage" + ".png")));
    }
}