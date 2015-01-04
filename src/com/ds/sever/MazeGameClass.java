/*
 * Players are numbered from 0 to NO_OF_PLAYERS-1. It is their respective id
 * Cells are numbered from 0 to (SIZE_OF_MAZE*SIZE_OF_MAZE-1)
 * */

package com.ds.sever;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public final class MazeGameClass implements Serializable {
	private static final long serialVersionUID = 356185318404742910L;
	private int NO_OF_TREASURES, SIZE_OF_MAZE, NO_OF_PLAYERS;
	String backupserverIp = null;
	String serverIp = null;
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	int portno;
	int backupPort;
	int backupId;
	private int NO_OF_TREASURES_LEFT;
	private mCell[] mCells;
	private ArrayList<PlayerClass> players;

	MazeGameClass() {

	}

	public int getBackupId() {
		return backupId;
	}

	public void setBackupId(int backupId) {
		this.backupId = backupId;
	}

	public int getPortno() {
		return portno;
	}

	public void setPortno(int portno) {
		this.portno = portno;
	}

	public String getBackupserverIp() {
		return backupserverIp;
	}

	public void setBackupserverIp(String backupserverIp) {
		this.backupserverIp = backupserverIp;
	}

	public int getBackupPort() {
		return backupPort;
	}

	public void setBackupPort(int backupPort) {
		this.backupPort = backupPort;
	}

	public int getNO_OF_PLAYERS() {
		return NO_OF_PLAYERS;
	}

	public int getNO_OF_TREASURES() {
		return NO_OF_TREASURES;
	}

	public void setNO_OF_TREASURES(int nO_OF_TREASURES) {
		NO_OF_TREASURES = nO_OF_TREASURES;
	}

	public int getSIZE_OF_MAZE() {
		return SIZE_OF_MAZE;
	}

	public void setSIZE_OF_MAZE(int sIZE_OF_MAZE) {
		SIZE_OF_MAZE = sIZE_OF_MAZE;
	}

	public ConcurrentHashMap<Integer, Integer> getpId_pNo_hashmap() {
		return pId_pNo_hashmap;
	}

	public void setpId_pNo_hashmap(
			ConcurrentHashMap<Integer, Integer> pId_pNo_hashmap) {
		this.pId_pNo_hashmap = pId_pNo_hashmap;
	}

	public int getNO_OF_TREASURES_LEFT() {
		return NO_OF_TREASURES_LEFT;
	}

	public void setNO_OF_TREASURES_LEFT(int nO_OF_TREASURES_LEFT) {
		NO_OF_TREASURES_LEFT = nO_OF_TREASURES_LEFT;
	}

	public mCell[] getmCells() {
		return mCells;
	}

	public void setmCells(mCell[] mCells) {
		this.mCells = mCells;
	}

	public ArrayList<PlayerClass> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<PlayerClass> players) {
		this.players = players;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setNO_OF_PLAYERS(int nO_OF_PLAYERS) {
		NO_OF_PLAYERS = nO_OF_PLAYERS;
	}

	public ConcurrentHashMap<Integer, Integer> pId_pNo_hashmap = new ConcurrentHashMap<Integer, Integer>();

	public ConcurrentHashMap<Integer, Integer> getHashMap() {
		return pId_pNo_hashmap;
	}

	public void setpId_pNo_hashmap(HashMap<Long, Integer> pId_pNo_hashmap) {
		pId_pNo_hashmap = pId_pNo_hashmap;
	}

	public void intialize(int m, int n) {
		NO_OF_TREASURES = m;
		SIZE_OF_MAZE = n;
		NO_OF_PLAYERS = 0;
		NO_OF_TREASURES_LEFT = m;
		players = new ArrayList<PlayerClass>();
		mCells = new mCell[(int) Math.pow(SIZE_OF_MAZE, 2)];
	}

	public int addPlayerInGame(String pName, int pId, String ipaddress,
			boolean backupserver) {

		NO_OF_PLAYERS++;
		pId_pNo_hashmap.put(pId, NO_OF_PLAYERS);
		initializePlayerObject(pName, pId, ipaddress, backupserver);
		return setPlayerIntoRandomCell(pId);
	}

	public void initializeCellObjects() {
		for (int i = 0; i < mCells.length; i++)
			mCells[i] = new mCell();
	}

	public void initializePlayerObject(String pName, int pId, String ipaddress,
			boolean backupserver) {

		PlayerClass p = new PlayerClass();
		// System.out.print("Reached here");
		p.setPLAYER_NAME(pName);
		p.setIpaddress(ipaddress);
		p.setBackupServer(backupserver);
		players.add(p);
	}

	public void setCellObjectNumbers() {
		for (int i = 0; i < mCells.length; i++)
			mCells[i].setCellNo(i);
	}

	public void fillCellsWithTreasures() {

		Random randomGenerator = new Random();
		int randomIntForCell;
		int randomIntForTreasures;
		int mCount = 0;

		while (NO_OF_TREASURES - mCount != 0) {
			do {
				randomIntForCell = randomGenerator.nextInt((int) Math.pow(
						SIZE_OF_MAZE, 2));
			} while (mCells[randomIntForCell].isTreasureKept());

			do {
				randomIntForTreasures = randomGenerator.nextInt(5);
				if (NO_OF_TREASURES - randomIntForTreasures - mCount > 0
						|| randomIntForTreasures == 0)
					continue;
			} while (NO_OF_TREASURES - randomIntForTreasures - mCount < 0);
			mCount = randomIntForTreasures + mCount;
			mCells[randomIntForCell]
					.setNoOfTreasuresInaCell(randomIntForTreasures);
			mCells[randomIntForCell].setTreasureKept(true);
		}
	}

	public void showStatusOfGame() {
		for (int i = 0; i < (int) Math.pow(SIZE_OF_MAZE, 2); i++)
			System.out.println("Cell_No: " + mCells[i].getCellNo() + "  "
					+ "No_of_treasures: " + mCells[i].getNoOfTreasuresInaCell()
					+ "  " + "Is_occupied: " + mCells[i].isOccupied() + "  "
					+ "Is_treasure_kept: " + mCells[i].isTreasureKept() + "  "
					+ "Player_No: " + mCells[i].getPlayerIdInCell() + "  "
					+ "No_of_treasures_left: " + NO_OF_TREASURES_LEFT);
		System.out.print("\n");
	}

	public mCell[] getAllCells() {
		return mCells;
	}

	public int setPlayerIntoRandomCell(int pId) {
		Random randomNoGenerator = new Random();
		int randomIntForCell;

		while (true) {
			randomIntForCell = randomNoGenerator.nextInt((int) Math.pow(
					SIZE_OF_MAZE, 2));
			if (mCells[randomIntForCell].isOccupied()
					|| mCells[randomIntForCell].isTreasureKept())
				continue;
			else {

				int pNo = pId_pNo_hashmap.get(pId);
				players.get(pNo - 1).setPLAYER_ID(pId);
				players.get(pNo - 1).setPLAYER_CELL_NO(randomIntForCell);
				players.get(pNo - 1).setTREASURES_COLLECTED(
						mCells[randomIntForCell].getNoOfTreasuresInaCell());
				NO_OF_TREASURES_LEFT = NO_OF_TREASURES_LEFT
						- mCells[randomIntForCell].getNoOfTreasuresInaCell();
				mCells[randomIntForCell].setOccupied(true);
				mCells[randomIntForCell].setNoOfTreasuresInaCell(0);
				mCells[randomIntForCell].setTreasureKept(false);
				mCells[randomIntForCell].setPlayerInCell(pId);
				break;
			}
		}
		return randomIntForCell;
	}

	public MazeGameClass move(int pId, String direction) {

		int pNo = pId_pNo_hashmap.get(pId);
		int oldLocation = players.get(pNo - 1).getPLAYER_CELL_NO();
		int newLocation;
		switch (direction.toUpperCase()) {
		case "A":
			if (oldLocation % SIZE_OF_MAZE != 0) {
				newLocation = oldLocation - 1;
				if (!mCells[newLocation].isOccupied()) {
					NO_OF_TREASURES_LEFT = NO_OF_TREASURES_LEFT
							- mCells[newLocation].getNoOfTreasuresInaCell();
					mCells[oldLocation].setOccupied(false);
					mCells[newLocation].setOccupied(true);
					mCells[oldLocation].setTreasureKept(false);
					mCells[newLocation].setTreasureKept(false);
					mCells[oldLocation].setPlayerInCell(0);
					players.get(pNo - 1).setPLAYER_CELL_NO(newLocation);
					checkLocToGetTreasureAndUpdatePlayer(oldLocation,
							newLocation, pId);
					mCells[newLocation].setPlayerInCell(pId);

				}
			}
			break;
		case "W":
			if (oldLocation > SIZE_OF_MAZE - 1) {
				newLocation = oldLocation - SIZE_OF_MAZE;
				if (!mCells[newLocation].isOccupied()) {
					NO_OF_TREASURES_LEFT = NO_OF_TREASURES_LEFT
							- mCells[newLocation].getNoOfTreasuresInaCell();
					mCells[oldLocation].setOccupied(false);
					mCells[newLocation].setOccupied(true);
					mCells[oldLocation].setTreasureKept(false);
					mCells[newLocation].setTreasureKept(false);
					mCells[oldLocation].setPlayerInCell(0);
					players.get(pNo - 1).setPLAYER_CELL_NO(newLocation);
					checkLocToGetTreasureAndUpdatePlayer(oldLocation,
							newLocation, pId);
					mCells[newLocation].setPlayerInCell(pId);

				}
			}
			break;
		case "S":
			if (oldLocation < (SIZE_OF_MAZE * SIZE_OF_MAZE - SIZE_OF_MAZE)) {
				newLocation = oldLocation + SIZE_OF_MAZE;
				if (!mCells[newLocation].isOccupied()) {
					NO_OF_TREASURES_LEFT = NO_OF_TREASURES_LEFT
							- mCells[newLocation].getNoOfTreasuresInaCell();
					mCells[oldLocation].setOccupied(false);
					mCells[newLocation].setOccupied(true);
					mCells[oldLocation].setTreasureKept(false);
					mCells[newLocation].setTreasureKept(false);
					mCells[oldLocation].setPlayerInCell(0);
					players.get(pNo - 1).setPLAYER_CELL_NO(newLocation);
					checkLocToGetTreasureAndUpdatePlayer(oldLocation,
							newLocation, pId);
					mCells[newLocation].setPlayerInCell(pId);

				}
			}
			break;
		case "D":
			if (oldLocation % SIZE_OF_MAZE != SIZE_OF_MAZE - 1) {
				newLocation = oldLocation + 1;
				if (!mCells[newLocation].isOccupied()) {
					NO_OF_TREASURES_LEFT = NO_OF_TREASURES_LEFT
							- mCells[newLocation].getNoOfTreasuresInaCell();
					mCells[oldLocation].setOccupied(false);
					mCells[newLocation].setOccupied(true);
					mCells[oldLocation].setTreasureKept(false);
					mCells[newLocation].setTreasureKept(false);
					mCells[oldLocation].setPlayerInCell(0);
					players.get(pNo - 1).setPLAYER_CELL_NO(newLocation);
					checkLocToGetTreasureAndUpdatePlayer(oldLocation,
							newLocation, pId);
					mCells[newLocation].setPlayerInCell(pId);
				}
			}
			break;
		case "N":
			break;
		default:
			break;
		}
		return this;
	}

	public void checkLocToGetTreasureAndUpdatePlayer(int oldLoc, int newLoc,
			int pId) {
		int pNo = pId_pNo_hashmap.get(pId);
		players.get(pNo - 1).setTREASURES_COLLECTED(
				players.get(pNo - 1).getTREASURES_COLLECTED()
						+ mCells[newLoc].getNoOfTreasuresInaCell());
		mCells[newLoc].setNoOfTreasuresInaCell(0);
		mCells[newLoc].setTreasureKept(false);
		mCells[oldLoc].setNoOfTreasuresInaCell(0);
		mCells[oldLoc].setTreasureKept(false);
	}

	public int getNoOfTreasuresLeftInaGame() {
		return NO_OF_TREASURES_LEFT;
	}

	public void showAllPlayerStatus() {
		for (int i = 1; i <= NO_OF_PLAYERS; i++) {
			players.get(i - 1).showPlayerStatus();
		}
		System.out.print("\n");

	}

	public boolean isPlayerOccupySameCell(int loc) {

		if (mCells[loc].isOccupied())
			return true;
		else
			return false;
	}

	public void showGameInMatrixFormat() {

		System.out.println();
		for (int i = 0; i < Math.pow(SIZE_OF_MAZE, 2); i++) {

			if (i % SIZE_OF_MAZE == 0) {
				System.out.print("\n");
				for (int j = 0; j < SIZE_OF_MAZE; j++)
					System.out.print("|_______________");
				System.out.print("|\n");
				for (int j = 0; j < SIZE_OF_MAZE; j++)
					System.out.print("|               ");
				System.out.print("|\n|");

			}

			if (pId_pNo_hashmap.get(mCells[i].getPlayerIdInCell()) != null) {
				System.out.format(
						"%10s",
						players.get(
								pId_pNo_hashmap.get(mCells[i]
										.getPlayerIdInCell()) - 1)
								.getPLAYER_NAME());
				if (mCells[i].getNoOfTreasuresInaCell() == 1)
					System.out.print(",T" + "\t|");
				else
					System.out.print("" + "\t|");
			} else {
				System.out.format("          ");
				if (mCells[i].getNoOfTreasuresInaCell() > 0)
					System.out.print("T" + mCells[i].getNoOfTreasuresInaCell()
							+ "\t|");
				else
					System.out.print("" + "\t|");
			}
		}
		System.out.print("\n");
		for (int j = 0; j < SIZE_OF_MAZE; j++)
			System.out.print("|_______________");
		System.out.print("|\n");
	}

	public MazeGameClass returnGameInstance() {
		return this;
	}

	public String getPlayerWithMaxTreasuresInGame() {
		int max = 0;
		String name = null;
		for (int i = 1; i <= NO_OF_PLAYERS; i++) {
			if (players.get(i - 1).getTREASURES_COLLECTED() > max) {
				max = players.get(i - 1).getTREASURES_COLLECTED();
				name = players.get(i - 1).getPLAYER_NAME();
			}
		}
		return name;
	}

	public void removePlayer(int pId) {

		int pNo = pId_pNo_hashmap.get(pId);
		int Location = players.get(pNo - 1).getPLAYER_CELL_NO();
		mCells[Location].setOccupied(false);
		mCells[Location].setPlayerInCell(0);
		pId_pNo_hashmap.remove(pId);
		players.remove(pNo - 1);
		NO_OF_PLAYERS--;
	}

}
