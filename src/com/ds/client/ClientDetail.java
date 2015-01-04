package com.ds.client;

public class ClientDetail {
	String name;
	String id;
	int numTreasures;
	int cellNo;

	public ClientDetail(String name, String id) {
		this.setName(name);
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumTreasures() {
		return numTreasures;
	}

	public void setNumTreasures(int numTreasures) {
		this.numTreasures = numTreasures;
	}

	public int getCellNo() {
		return cellNo;
	}

	public void setCellNo(int cellNo) {
		this.cellNo = cellNo;
	}
}
