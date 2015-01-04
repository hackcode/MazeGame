package com.ds.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.ds.sever.ClientReg;
import com.ds.sever.ClientRegImp;
import com.ds.sever.MazeGameClass;
import com.ds.sever.MazeGameClassFactory;
import com.ds.sever.PlayerClass;
import com.ds.sever.Server;
import com.ds.sever.ServerDetail;
import com.ds.sever.mCell;

public class Client {

	static ClientReg clientRegImpStub = null;

	static MazeGameClass mazegamestate = null;
	static int pid;
	static String ipaddress;
	static int portN;
	static ClientLookupIntialization clientlookinstance = null;
	static PlayThread pt = new PlayThread();
	static Thread startplay = null;
	static Registry registry1;
	static boolean backup = false;
	static int backupserverport;
	static String backupipaddress;

	protected Client() throws RemoteException {

	}

	public static void main(String[] args) throws RemoteException,
			UnknownHostException, MalformedURLException, NotBoundException {

		int initialPortNumber;
		String initialIpAddress;
		int initialMazeSize;
		int initialTreasures;
		try {
			if (args.length == 4) {
				initialIpAddress = args[0];
				initialPortNumber = Integer.parseInt(args[1]);
				initialMazeSize = Integer.parseInt(args[3]);
				initialTreasures = Integer.parseInt(args[2]);

				initializeFirstServer(initialIpAddress, initialPortNumber,
						initialTreasures, initialMazeSize);
			} else {
				initialIpAddress = args[0];
				initialPortNumber = Integer.parseInt(args[1]);
			}

			clientlookinstance = ClientPool.initClientLookup(initialIpAddress,
					initialPortNumber);
			clientRegImpStub = clientlookinstance.getClientRegImpStub();
			ipaddress = Inet4Address.getLocalHost().getHostAddress();
			portN = initialPortNumber;

			ServerDetail.getServerDetail().setServerHostname(initialIpAddress);
			ServerDetail.getServerDetail().setServerPort(portN);
			MazeGameClass mazegamestate = clientRegImpStub
					.getMazeGameClassInstance();

			Map<String, String> registeredplayer = null;

			System.out
					.println("Please enter the player name to join the game...");
			BufferedReader name = new BufferedReader(new InputStreamReader(
					System.in));
			String playername = name.readLine().toString();
			int n = clientRegImpStub.getNO_OF_PLAYERS();
			long entrytime;
			boolean backupserver = false;
			if (n == 0) {
				System.out.println("First client");
				clientRegImpStub.setServerPort(portN);
				long startTime = clientRegImpStub.getCurrentTime();
				clientRegImpStub.setStartTime(startTime);
				entrytime = startTime;
				clientRegImpStub.setServerIpaddress(ipaddress);
				// clientRegImpStub.setServerPort(portN);
			} else if (n == 1) {
				backupserver = true;
				entrytime = clientRegImpStub.getCurrentTime();
			} else {

				entrytime = clientRegImpStub.getCurrentTime();
				if ((entrytime - clientRegImpStub.getStartTime()) > 20000) {
					System.out
							.println("Game has already started! You cannot join now. Sorry :<");
					return;
				}
			}
			if (backupserver) {
				clientRegImpStub
						.setbackServerPort(mazegamestate.getPortno() + 1);
				clientRegImpStub.setbackupServerip(ipaddress);
			}

			registeredplayer = clientRegImpStub.register(playername, ipaddress,
					backupserver);
			System.out.println("You are successfully added in the MazeGame.");
			if (n != 0) {
				System.out
						.println("You will have to wait for "
								+ (20000 - (entrytime - clientRegImpStub
										.getStartTime())) / 1000
								+ " seconds to start playing. Loading now...");
				Thread.sleep(20000 - (entrytime - clientRegImpStub
						.getStartTime()));
			} else {
				System.out
						.println("You will have to wait for 20 seconds to start playing. Loading now...");
				Thread.sleep(20000);
			}

			pid = Integer.parseInt(registeredplayer.get("id"));
			System.out.println("Client Id Given: " + pid);
			if (backupserver)
				checkHostAlive();

			startplay = new Thread(pt);
			startplay.start();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private static void checkHostAlive() {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		timer.schedule(task, 01, 2000);
	}

	static TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if (hostAvailabilityCheck()) {
					try {
						mazegamestate = clientRegImpStub
								.getMazeGameClassInstance();
						System.out
								.println("This is backup Server. No.of treasures left: "
										+ mazegamestate
												.getNO_OF_TREASURES_LEFT());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out
							.println("Main Server is down... Updating gamestate on backup server");
					backup = true;
					task.cancel();
					intialize(mazegamestate);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public static boolean hostAvailabilityCheck() throws RemoteException {
		try (Socket s = new Socket(clientRegImpStub.getServerIpaddress(),
				clientRegImpStub.getServerPort())) {
			return true;
		} catch (Exception ex) {
			/* ignore */
			return false;
		}

	}

	private static void show(MazeGameClass mz) {
		int SIZE_OF_MAZE = mz.getSIZE_OF_MAZE();
		ConcurrentHashMap<Integer, Integer> pId_pNo_hashmap = mz
				.getpId_pNo_hashmap();
		mCell[] mCells = mz.getAllCells();
		ArrayList<PlayerClass> players = mz.getPlayers();
		System.out.println();
		for (int i = 0; i < Math.pow(SIZE_OF_MAZE, 2); i++) {

			if (i % SIZE_OF_MAZE == 0) {
				System.out.print("\n");
				for (int j = 0; j < SIZE_OF_MAZE; j++)
					System.out.print("|_______________");
				System.out.print("|\n");
				for (int j = 0; j < SIZE_OF_MAZE; j++)
					System.out.print("|               ");
				System.out.print("|\n|");

			}

			if (pId_pNo_hashmap.get(mCells[i].getPlayerIdInCell()) != null) {
				System.out.format(
						"%10s",
						players.get(
								pId_pNo_hashmap.get(mCells[i]
										.getPlayerIdInCell()) - 1)
								.getPLAYER_NAME());
				if (mCells[i].getNoOfTreasuresInaCell() == 1)
					System.out.print(",T" + "\t|");
				else
					System.out.print("" + "\t|");
			} else {
				System.out.format("          ");
				if (mCells[i].getNoOfTreasuresInaCell() > 0)
					System.out.print("T" + mCells[i].getNoOfTreasuresInaCell()
							+ "\t|");
				else
					System.out.print("" + "\t|");
			}
		}
		System.out.print("\n");
		for (int j = 0; j < SIZE_OF_MAZE; j++)
			System.out.print("|_______________");
		System.out.print("|\n");
	}

	private static String getWinnerName(MazeGameClass mz) {
		int max = 0;
		String name = null;
		ArrayList<PlayerClass> players = mz.getPlayers();
		PlayerClass p;
		for (int i = 1; i <= mz.getNO_OF_PLAYERS(); i++) {
			p = players.get(i - 1);
			if (p.getTREASURES_COLLECTED() > max) {
				max = p.getTREASURES_COLLECTED();
				name = p.getPLAYER_NAME();
			}
		}
		return name;
	}

	private static int getWinnerTreasureCount(MazeGameClass mz) {
		int max = 0;
		ArrayList<PlayerClass> players = mz.getPlayers();
		PlayerClass p;
		for (int i = 1; i <= mz.getNO_OF_PLAYERS(); i++) {
			p = players.get(i - 1);
			if (p.getTREASURES_COLLECTED() > max) {
				max = p.getTREASURES_COLLECTED();
			}
		}
		return max;
	}

	public static void playgame() throws RemoteException, NotBoundException {
		clientlookinstance = ClientPool.initClientLookup();
		clientRegImpStub = clientlookinstance.getClientRegImpStub();

		MazeGameClass mz = null;
		try {
			mz = clientRegImpStub.getMazeGameClassInstance();
			if (backup) {
				System.out
						.println("Gamestates treasures left after re-intialization again: "
								+ mz.getNO_OF_TREASURES_LEFT());

				clientRegImpStub.setServerIpaddress(ipaddress);
				clientRegImpStub.setServerPort((mz.getPortno() + 1));
				clientRegImpStub.setportno(mz.getPortno() + 1);
				backup = false;

				/* setting up the next backup server */
				int c = 0;
				ArrayList<PlayerClass> p = mz.getPlayers();
				int l = p.size();
				// System.out.println("l:" + l);
				for (int i = 0; i < l; i++) {
					if (p.get(i).getPLAYER_ID() == pid) {
						c = i + 1;
						break;
					}
				}

				clientRegImpStub.setbackupServerip(p.get(c).getIpaddress());
				mz.setBackupserverIp(p.get(c).getIpaddress());
				mz.setBackupPort(mz.getPortno() + 1);
				clientRegImpStub
				.setbackServerPort(mz.getPortno() + 1);
				clientRegImpStub.setBackupId(p.get(c).getPLAYER_ID());
			}
			backupipaddress = mz.getBackupserverIp();
			backupserverport = mz.getBackupPort();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		show(mz);

		while (true) {
			System.out.println("Please enter the move...");
			BufferedReader move = new BufferedReader(new InputStreamReader(
					System.in));
			String mv = null;
			try {
				mv = move.readLine().toString();
				mz = clientRegImpStub.callMove(pid, mv);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block

				System.out.println("Wait till backserver starts...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("Wait till backserver starts..."+mz.getBackupserverIp());
				System.out.println("Wait till backserver starts..."+mz.getPortno());
				/*Registry registry = LocateRegistry.getRegistry(backupipaddress,
						backupserverport);*/
				Registry registry = LocateRegistry.getRegistry(mz.getBackupserverIp(),
						mz.getPortno());
				
				clientRegImpStub = (ClientReg) registry.lookup("ClientRegImp");
				
				if (mv != null)
					mz = clientRegImpStub.callMove(pid, mv);

				if (mz.getBackupId() == pid) {
					checkHostAlive();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			show(mz);
			if (isGameOver(mz))
				break;
		}
		System.out.println("Game is over. " + getWinnerName(mz)
				+ " has won the game with highest "
				+ getWinnerTreasureCount(mz) + " treasures!");
		return;
	}

	private static boolean isGameOver(MazeGameClass mz) {
		if (mz.getNO_OF_TREASURES_LEFT() == 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("deprecation")
	public static void intialize(MazeGameClass mazegamestate2) {
		Registry registry = null;
		ClientReg clientRegImpstub;
		MazeGameClass mazeGameClass;

		MazeGameClassFactory.setMazeGameState(mazegamestate);
		mazeGameClass = mazegamestate2;//MazeGameClassFactory.getMazeGameState();

		/* again back-up server creation logic starting */

		System.out
				.println("Gamestate treasures left after re-intialization again: "
						+ mazeGameClass.getNO_OF_TREASURES_LEFT());

		System.out.println("Game state re-intialized on port: "
				+ (mazeGameClass.getPortno() + 1));
		try {
			ClientRegImp clientRegImpobj = new ClientRegImp();

			clientRegImpstub = (ClientReg) UnicastRemoteObject.exportObject(
					clientRegImpobj, 0);
			int port = mazeGameClass.getPortno() + 1;

			registry = LocateRegistry.createRegistry(port);
			registry.bind("ClientRegImp", clientRegImpstub);

			ServerDetail.getServerDetail().setServerHostname(ipaddress);
			ServerDetail.getServerDetail().setServerPort(
					mazeGameClass.getPortno() + 1);
			backup = true;
			System.err.println("Server ready");
			if (startplay.isAlive())
				startplay.stop();

			startplay = new Thread(pt);
			startplay.start();
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			try {

				registry.unbind("ClientRegImp");
				System.err.println("Server ready");
			} catch (Exception ee) {
				System.err.println("Server exception: " + ee.toString());
				ee.printStackTrace();
			}
		}
	}

	private static void initializeFirstServer(String Ip, int port,
			int treasure, int mazesize) {
		Server obj = new Server();
		obj.treasure = treasure;
		obj.mazesize = mazesize;
		obj.initializeGame();
		obj.intializeServer(Ip, port);
	}

	public static class PlayThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				playgame();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
