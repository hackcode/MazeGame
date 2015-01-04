/*
 * Remote interface to manage Player Registration 
 */

package com.ds.sever;

import java.rmi.Remote;

public interface UniqueId extends Remote {
	static int playerId = 0;

	public int getPlayerID();
}