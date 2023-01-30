/*
 * @author Vasanth Soundararajan
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.db.model;

public class MongoConnectionData {
	private String hostname = null;
	private String username= null;
	private String password = null;
	private String port = null;

	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}
