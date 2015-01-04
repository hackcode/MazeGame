package com.ds.sever;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class ClientMoveImp extends UnicastRemoteObject implements ClientMoveReg {

	private static final long serialVersionUID = 45079690522518514L;

	MazeGameClass mazegamestate;

	static int maxTreasure = 0;

	public ClientMoveImp() throws RemoteException {
		mazegamestate = MazeGameClassFactory.getMazeGameState();
	}

	@Override
	public Map<String, Object> moveClient(String id, String move)
			throws RemoteException {
		// TODO Auto-generated method stub

		return null;
	}
}