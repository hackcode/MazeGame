package com.ds.sever;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

	public interface ClientMoveReg extends Remote{
		public Map<String,Object> moveClient( String id, String move ) throws RemoteException;
	}


