package com.ds.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.ds.sever.ClientReg;
import com.ds.sever.ServerDetail;

public class ClientLookupIntialization {
	private ClientReg clientRegImpStub;

	public ClientReg getClientRegImpStub() {
		return clientRegImpStub;
	}

	public void setClientRegImpStub(ClientReg clientRegImpStub) {
		this.clientRegImpStub = clientRegImpStub;
	}

	Registry registry;

	public ClientLookupIntialization(String Ip, int port)
			throws RemoteException, NotBoundException {

		try {

			registry = LocateRegistry.getRegistry(Ip, port);

			clientRegImpStub = (ClientReg) registry.lookup("ClientRegImp");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ClientLookupIntialization() throws RemoteException,
			NotBoundException {

		try {

			registry = LocateRegistry.getRegistry(ServerDetail
					.getServerDetail().getServerHostname(), ServerDetail
					.getServerDetail().getServerPort());

			clientRegImpStub = (ClientReg) registry.lookup("ClientRegImp");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
