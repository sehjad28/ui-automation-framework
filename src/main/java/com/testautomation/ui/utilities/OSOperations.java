package com.testautomation.ui.utilities;

public class OSOperations {
	private static String OSName = getOSName();

	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	public static String getArchSafePath(String path) {
		if (OSName.indexOf("win") >= 0) {
			path = path.replace("/", "\\");
		} 
		else if (OSName.indexOf("linux") >= 0) {
			path = path.replace("//", "/");
			path = path.replace("\\", "/");
		}
		return path;
	}
}