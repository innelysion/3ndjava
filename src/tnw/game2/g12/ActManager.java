package tnw.game2.g12;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ActManager {

	enum Scene {
		TITLE, MAP, BATTLE, MENU, GAMEOVER;
	}

	// For management
	Scene showing;
	GameData gamedata;

	// Game objects
	ActMap map;

	ActManager() {

		gamedata = new GameData();
		map = new ActMap();
		map.setMapData(gamedata.getMapData("mapdata/S1_Hit.csv"), "HITMAP");
		map.setMapData(gamedata.getMapData("mapdata/S1_Map.csv"), "BACKGROUND");

		showing = Scene.MAP;
	}

	void updateMain() {
		map.move();
	}

	void drawMain(Graphics2D g, JFrame w) {
		g.setFont(GS.DEFAULT_FONT);
		map.draw(g, w);
	}

}
