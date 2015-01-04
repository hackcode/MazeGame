package com.ds.sever;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.ds.sever.MazeGameClass;

public interface ClientReg extends Remote {
	public Map<String, String> register(String name, String ipaddress,
			boolean backupserver) throws RemoteException;

	public MazeGameClass getMazeGameClassInstance() throws RemoteException;

	public MazeGameClass callMove(int pid, String direction)
			throws RemoteException;

	public void showGameInMatrixFormat() throws RemoteException;

	public long getStartTime() throws RemoteException;

	public void setStartTime(long startTime) throws RemoteException;

	public long getCurrentTime() throws RemoteException;

	public int getNO_OF_PLAYERS() throws RemoteException;

	public String getServerIpaddress() throws RemoteException;

	public int getServerPort() throws RemoteException;

	public void setServerIpaddress(String ipaddress) throws RemoteException;

	public void setServerPort(int serverPort) throws RemoteException;

	public void setbackServerPort(int serverPort) throws RemoteException;

	public void setbackupServerip(String serverPort) throws RemoteException;

	public void setportno(int i) throws RemoteException;

	public void setBackupId(int player_ID) throws RemoteException;
}
