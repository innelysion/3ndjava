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
	ActActor player;
	ActActor npc1;
	ActActor npc2;
	ActActor npc3;
	ActCamera camera;

	private int timerMgr;

	ActManager() {

		timerMgr = 0;

		gamedata = new GameData();

		map = new ActMap();
		map.setMapData(gamedata.getMapData("mapdata/S0_Hit.csv"), "HITMAP");
		map.setMapData(gamedata.getMapData("mapdata/S0_Map.csv"), "BACKGROUND");

		player = new ActActor(1);
		npc1 = new ActActor(2);
		npc2 = new ActActor(3);

		camera = new ActCamera();
		camera.setFocusChara(player);
		showing = Scene.MAP;

	}

	public void updateMain() {
		// Main timer
		timerMgr = timerMgr > 9999 ? 0 : timerMgr + 1;

		// Switch camera focus target
		if (Input.keyRe.Z) {
			switch (camera.getFocusChara().getId()){
			case 1:
				player.freeze();
				camera.setFocusChara(npc2);
				break;
			case 3:
				camera.setFocusChara(npc1);
				break;
			case 2:
				player.defreeze();
				camera.setFocusChara(player);
				break;
			}
		}

		// Follow player
		if (npc2.flag == 4 && timerMgr % 30 == 0 && npc2.X % 16 == 0 && npc2.Y % 16 == 0){
			npc2.moveTo((int)(player.X / 16) * 16, (int)(player.Y / 16) * 16);
		}

		// Game objects update
		player.update(map);
		npc1.update(map);
		//npc2.update(map);
		camera.update(this);
	}

	public void drawMain(Graphics2D g, JFrame w) {
		g.setFont(GS.DEFAULT_FONT);
		camera.draw(g, w);
	}

}
