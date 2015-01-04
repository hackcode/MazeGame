package com.ds.sever;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MazeServerIF extends Remote {

	void registerClient(MazeClientIF mc) throws RemoteException;

	String broadMesage(String msg) throws RemoteException;

	void individualMesage(MazeClientIF mc, String msg) throws RemoteException;

}
