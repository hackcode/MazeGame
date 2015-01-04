package com.ds.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientPool {
	static ClientLookupIntialization clientLookupIntialization;

	public static ClientLookupIntialization initClientLookup(String Ip, int port)
			throws RemoteException, NotBoundException {
		clientLookupIntialization = new ClientLookupIntialization(Ip, port);
		return clientLookupIntialization;
	}

	public static ClientLookupIntialization initClientLookup()
			throws RemoteException, NotBoundException {
		clientLookupIntialization = new ClientLookupIntialization();
		return clientLookupIntialization;
	}

	public static ClientLookupIntialization getClientLookupIntialization() {
		return clientLookupIntialization;
	}
}
