package com.ds.sever;

import java.io.Serializable;

public class ServerDetail implements Serializable {
	private static final long serialVersionId = 1l;
	private static ServerDetail serverDetail;

	public static ServerDetail getServerDetail() {

		if (serverDetail == null) {

			serverDetail = new ServerDetail();

		}
		return serverDetail;
	}

	private String serverIpaddress = "localhost";
	private int serverPort = 4597;

	public String getServerHostname() {
		return serverIpaddress;
	}

	public void setServerHostname(String serverHostname) {
		this.serverIpaddress = serverHostname;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
}
