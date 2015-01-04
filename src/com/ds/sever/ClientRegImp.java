/*
 * Remote interface to manage Player Registration 
 */

package com.ds.sever;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRegImp implements ClientReg {
	MazeGameClass mazegame = MazeGameClassFactory.getMazeGameState();
	ConcurrentHashMap<Integer, Integer> pId_pNo_hashmap;
	long startTime;

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;

	}

	public Map<String, String> register(String name, String ipaddress,
			boolean backupserver) throws RemoteException {

		HashMap<String, String> registration = new HashMap<String, String>();
		int uniqueid = generateUniqueId();
		pId_pNo_hashmap = mazegame.getHashMap();
		int cutrrentplayercount = mazegame.getNO_OF_PLAYERS();
		cutrrentplayercount++;
		pId_pNo_hashmap.put(uniqueid, cutrrentplayercount);
		mazegame.addPlayerInGame(name, uniqueid, ipaddress, backupserver);
		registration.put("id", "" + uniqueid);
		return registration;
	}

	public static int generateUniqueId() {
		UUID idOne = UUID.randomUUID();
		String str = "" + idOne;
		int uid = str.hashCode();
		String filterStr = "" + uid;
		str = filterStr.replaceAll("-", "");
		return Integer.parseInt(str);
	}

	public MazeGameClass getMazeGameClassInstance() throws RemoteException {
		// TODO Auto-generated method stub
		return mazegame;
	}

	@Override
	public MazeGameClass callMove(int pid, String direction) {
		// TODO Auto-generated method stub
		MazeGameClass mz = mazegame.move(pid, direction);
		return mz;
	}

	@Override
	public int getNO_OF_PLAYERS() {
		return mazegame.getNO_OF_PLAYERS();
	}

	@Override
	public void showGameInMatrixFormat() {
		mazegame.showGameInMatrixFormat();
	}

	@Override
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	@Override
	public String getServerIpaddress()

	{
		return ServerDetail.getServerDetail().getServerHostname();

	}

	public int getServerPort()

	{
		return ServerDetail.getServerDetail().getServerPort();

	}

	@Override
	public void setServerIpaddress(String ipaddress) {
		ServerDetail.getServerDetail().setServerHostname(ipaddress);
	}

	@Override
	public void setServerPort(int port) {
		ServerDetail.getServerDetail().setServerPort(port);
	}

	@Override
	public void setbackServerPort(int serverPort) throws RemoteException {
		// TODO Auto-generated method stub
		mazegame.setBackupPort(serverPort);
	}

	@Override
	public void setbackupServerip(String backupserverIp) throws RemoteException {
		// TODO Auto-generated method stub
		mazegame.setBackupserverIp(backupserverIp);
	}

	public void setportno(int i) throws RemoteException {
		mazegame.setPortno(i);
	}

	public void setBackupId(int player_ID) {
		mazegame.setBackupId(player_ID);
	}
}