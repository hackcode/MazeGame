package com.ds.sever;

import java.io.Serializable;

public class PlayerClass implements Serializable {
	private static final long serialVersionUID = 156185318404742910L;
	private String PLAYER_NAME;
	private int PLAYER_ID;
	private int PLAYER_CELL_NO;
	private int TREASURES_COLLECTED;
	private String ipaddress;
	boolean backupServer = false;

	public boolean isBackupServer() {
		return backupServer;
	}

	public void setBackupServer(boolean backupServer) {
		this.backupServer = backupServer;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public PlayerClass() {
		this.PLAYER_NAME = "";
		this.PLAYER_ID = 0;
		this.PLAYER_CELL_NO = 0;
		this.TREASURES_COLLECTED = 0;
	}

	public PlayerClass(String pName, int pId, int cellNO, int treasuresCollected) {
		this.PLAYER_NAME = pName;
		this.PLAYER_ID = pId;
		this.PLAYER_CELL_NO = cellNO;
		this.TREASURES_COLLECTED = treasuresCollected;
	}

	public int getPLAYER_ID() {
		return PLAYER_ID;
	}

	public void setPLAYER_ID(int pLAYER_ID) {
		PLAYER_ID = pLAYER_ID;
	}

	public int getPLAYER_CELL_NO() {
		return PLAYER_CELL_NO;
	}

	public void setPLAYER_CELL_NO(int pLAYER_CELL_NO) {
		PLAYER_CELL_NO = pLAYER_CELL_NO;
	}

	public int getTREASURES_COLLECTED() {
		return TREASURES_COLLECTED;
	}

	public void setTREASURES_COLLECTED(int tREASURES_COLLECTED) {
		TREASURES_COLLECTED = tREASURES_COLLECTED;
	}

	public void showPlayerStatus() {

		System.out.println("PlayerName: " + PLAYER_NAME + "  " + "Player_id: "
				+ PLAYER_ID + "  " + "Player_cell_no: " + PLAYER_CELL_NO + "  "
				+ "Treasures: " + TREASURES_COLLECTED);
	}

	public String getPLAYER_NAME() {
		return PLAYER_NAME;
	}

	public void setPLAYER_NAME(String pLAYER_NAME) {
		PLAYER_NAME = pLAYER_NAME;
	}
}
