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
	
	ActCamera camera;

	ActManager() {

		gamedata = new GameData();

		map = new ActMap();
		map.setMapData(gamedata.getMapData("mapdata/S0_Hit.csv"), "HITMAP");
		map.setMapData(gamedata.getMapData("mapdata/S0_Map.csv"), "BACKGROUND");

		player = new ActPlayer();

		camera = new ActCamera();
		showing = Scene.MAP;
	}

	public void updateMain() {
		player.update(map);
		map.update();
		
		camera.update(this);
	}

	public void drawMain(Graphics2D g, JFrame w) {
		g.setFont(GS.DEFAULT_FONT);
		camera.draw(g, w);
	}

}
