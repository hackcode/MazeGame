package com.ds.sever;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MazeClientIF extends Remote {

	void getMessage() throws RemoteException;

}
