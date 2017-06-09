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
	ActPlayer player;

	ActManager() {

		gamedata = new GameData();

		map = new ActMap();
		map.setMapData(gamedata.getMapData("mapdata/S0_Hit.csv"), "HITMAP");
		map.setMapData(gamedata.getMapData("mapdata/S0_Map.csv"), "BACKGROUND");

		player = new ActPlayer();

		showing = Scene.MAP;
	}

	void updateMain() {
		player.update(map);
		map.move(player);
	}

	void drawMain(Graphics2D g, JFrame w) {
		g.setFont(GS.DEFAULT_FONT);
		map.draw(g, w);
		player.draw(g, w);
	}

}
