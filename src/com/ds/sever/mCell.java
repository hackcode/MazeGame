package com.ds.sever;

import java.io.Serializable;

public class mCell implements Serializable {
	private static final long serialVersionUID = -356185318404742910L;
	private int noOfTreasuresInaCell;
	private int cellNo;
	private boolean isOccupied;
	private boolean isTreasureKept;
	private int playerId;

	public mCell() {
		this.noOfTreasuresInaCell = 0;
		this.cellNo = 0;
		this.isOccupied = false;
		this.isTreasureKept = false;
		this.playerId = 0;
	}

	public mCell(int no_of_treasures, int cell_no, boolean occupied,
			boolean treasureKept, int pId) {
		this.noOfTreasuresInaCell = no_of_treasures;
		this.cellNo = cell_no;
		this.isOccupied = occupied;
		this.isTreasureKept = treasureKept;
		this.playerId = pId;
	}

	public int getNoOfTreasuresInaCell() {
		return noOfTreasuresInaCell;
	}

	public int getCellNo() {
		return cellNo;
	}

	public void setCellNo(int cellNo) {
		this.cellNo = cellNo;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public boolean isTreasureKept() {
		return isTreasureKept;
	}

	public void setTreasureKept(boolean treasureKept) {
		this.isTreasureKept = treasureKept;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public void setNoOfTreasuresInaCell(int noOfTreasuresInaCell) {
		this.noOfTreasuresInaCell = noOfTreasuresInaCell;
	}

	public void setPlayerInCell(int p) {
		this.playerId = p;
	}

	public int getPlayerIdInCell() {
		return playerId;
	}
}
