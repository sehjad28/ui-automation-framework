/*
 * @author Sampad Rout
 * (C) Copyright 2021 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.visual;

import com.testautomation.ui.utilities.LoadLogger;
import com.testautomation.ui.config.DeviceConfig;
import com.testautomation.ui.utilities.FileUtility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageComparator extends DeviceConfig {
    static Logger log = Logger.getLogger(LoadLogger.class);

    WebDriver driver;
    public static boolean COMPARE = false;
    public static String MODE;
    String baselineImageDirFullPath;

    public ImageComparator(WebDriver driver) {
        this.driver = driver;

        Path resourceDirectory = Paths.get("src","test","resources");
        String baselineImageDirPath = resourceDirectory.toFile().getAbsolutePath();

        baselineImageDirFullPath = baselineImageDirPath+ "/baselineImages/" + getExecutionPlatform() + "/";
    }

    private BufferedImage getBaselineImage(String imageName) throws IOException {
        LoadLogger.getInstance();

        File expectedImageFile = FileUtils.getFile(FileUtility.getFile(baselineImageDirFullPath + imageName + ".png").getAbsolutePath());
        if (expectedImageFile.exists()) {
            log.info("file exists");
        } else {
            log.info("file does not exists");
            ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
        }
        BufferedImage expectedImage = ImageIO.read(expectedImageFile);
        ImageIO.write(expectedImage, "png", expectedImageFile);
        log.info("Retrieving the baseline image " + imageName + " from " + baselineImageDirFullPath);
        return expectedImage;
    }

    private BufferedImage captureActualImage(String imageName) throws IOException {
        LoadLogger.getInstance();

        File imageFile;
        if (ImageComparator.COMPARE) {
            imageFile = new File(imageName + "_actual.png");
        } else {
            imageFile = new File(imageName + ".png");
        }

        BufferedImage image = new AShot().takeScreenshot(driver).getImage();
        if (driver instanceof IOSDriver || driver instanceof AndroidDriver) {
            DeviceViewportModel viewport = DeviceConfig.readDeviceViewportConfig().getDeviceViewport(executionPlatform);
            image = image.getSubimage(viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight());
        }
        ImageIO.write(image, "png", imageFile);
        FileUtility.copyFileToDirectory(imageFile, new File(baselineImageDirFullPath));
        log.info("Capturing the image " + imageName + " into local baselineImages directory " + baselineImageDirFullPath);
        FileUtility.forceDelete(imageFile);
        return image;
    }

    public void createDiffImageAs(ImageDiff diffImg, String imageName) throws IOException {
        LoadLogger.getInstance();

        BufferedImage diffImage = diffImg.getMarkedImage();
        File diffImageFile = new File(imageName + "_diffImage.png");
        ImageIO.write(diffImage, "png", diffImageFile);
        FileUtility.copyFileToDirectory(diffImageFile, new File(baselineImageDirFullPath));
        log.info("Saving the difference image into " + baselineImageDirFullPath);
        FileUtility.forceDelete(diffImageFile);
    }

    public boolean compare(String imageName) throws IOException {
        LoadLogger.getInstance();

        boolean imageMatchFlag = true;
        if (MODE.equalsIgnoreCase("visual")) {
            BufferedImage actualImage, expectedImage;
            ImageDiff diffImage;
            if (!ImageComparator.COMPARE) {
                captureActualImage(imageName);
            } else {
                expectedImage = getBaselineImage(imageName);
                actualImage = captureActualImage(imageName);
                diffImage = new ImageDiffer().makeDiff(expectedImage, actualImage);
                log.info("Comparing the expected baseline image with actual image");
                if (diffImage.getDiffSize() > 0) {
                    createDiffImageAs(diffImage, imageName);
                    imageMatchFlag = false;
                } else {
                    imageMatchFlag = true;
                }
            }
        }

        return imageMatchFlag;
    }

}