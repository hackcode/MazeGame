package com.ds.sever;

public class MazeGameClassFactory {
	private static MazeGameClass mazeGameState;

	public static MazeGameClass getMazeGameState() {

		if (mazeGameState == null) {
			mazeGameState = new MazeGameClass();

		}
		return mazeGameState;
	}

	public static void setMazeGameState(MazeGameClass mz) {
		mazeGameState = mz;
	}
}
