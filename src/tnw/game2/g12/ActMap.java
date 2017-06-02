package tnw.game2.g12;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;


public class ActMap implements GameTools {

	private ArrayList<ArrayList<Integer>> gameMapData1;
	private ArrayList<ArrayList<Integer>> gameMapHitData1;
	private KomaImage mapImage1;
	private KomaImage mapImage2;
	private int offsetX = 0;
	private int offsetY = 0;
	private int scrollTargetX = 0;
	private int scrollTargetY = 0;
	private int scrollSpd = 2;


	ActMap() {
		mapImage1 =  new KomaImage("mapbg01.png" , 40, 125);
		mapImage2 =  new KomaImage("mapbg02.png" , 40, 125);
	}

	void setMapData1(ArrayList<ArrayList<Integer>> data, ArrayList<ArrayList<Integer>> hitdata) {
		gameMapData1 = data;
		gameMapHitData1 = hitdata;
	}

	void drawMap(Graphics2D g, JFrame wind) {

		scrollSpd = GameInput.K_SHIFT ? 8 : 2;
		scrollTargetX = GameInput.RIGHT ? scrollTargetX - scrollSpd : GameInput.LEFT ? scrollTargetX + scrollSpd : scrollTargetX;
		scrollTargetY = GameInput.DOWN ? scrollTargetY - scrollSpd : GameInput.UP ? scrollTargetY + scrollSpd : scrollTargetY;

		offsetX += (scrollTargetX - offsetX) / 2;
		offsetY += (scrollTargetY - offsetY) / 2;


		g.setFont(GameSetting.DEFAULT_FONT);
		g.setColor(Color.GRAY);
		int mh = gameMapData1.size();
		for (int i = 0; i < mh; i++) {
			int mw = gameMapData1.get(i).size();
			for (int j = 0; j < mw; j++) {
				int data = gameMapData1.get(i).get(j) - (gameMapData1.get(i).get(j) / 255) * 216;
				drawKoma(g, wind, mapImage1, data + 1, offsetX + 16 * j, offsetY + 16 * i, 1.0f);
			}
		}

		int mhh = gameMapHitData1.size();
		for (int i = 0; i < mhh; i++) {
			int mhw = gameMapHitData1.get(i).size();
			for (int j = 0; j < mhw; j++) {
				if (gameMapHitData1.get(i).get(j) != 255) {
					int data = gameMapHitData1.get(i).get(j) - (gameMapHitData1.get(i).get(j) / 255) * 216;
					g.drawString("H", offsetX + 16 * j, offsetY + (16 * (i + 1)));
				}
			}
		}

	}


}

