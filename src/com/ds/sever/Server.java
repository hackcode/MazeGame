package com.ds.sever;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server {

	ArrayList<MazeClientIF> mazeClients;
	Registry registry = null;
	ClientReg clientRegImpstub;
	MazeGameClass mazeGameClass;
	public int treasure;
	public int mazesize;

	public Server() {

		mazeClients = new ArrayList<MazeClientIF>();
	}

	public void initializeGame() {
		mazeGameClass = MazeGameClassFactory.getMazeGameState();
		mazeGameClass.intialize(treasure, mazesize);
		mazeGameClass.initializeCellObjects();
		mazeGameClass.setCellObjectNumbers();
		mazeGameClass.fillCellsWithTreasures();
		System.out.println("Game state intialized");
	}

	public void intializeServer(String Ip, int portNumber) {

		try {
			mazeGameClass.setPortno(portNumber);
			ClientRegImp clientRegImpobj = new ClientRegImp();

			clientRegImpstub = (ClientReg) UnicastRemoteObject.exportObject(
					clientRegImpobj, 0);
			registry = LocateRegistry.createRegistry(portNumber);
			registry.bind("ClientRegImp", clientRegImpstub);

			System.err.println("Server ready");
		} catch (Exception e) {

			try {

				registry.unbind("ClientRegImp");
				System.err.println("Server ready");
			} catch (Exception ee) {

			}
		}
	}
}
