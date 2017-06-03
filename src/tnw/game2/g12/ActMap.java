package tnw.game2.g12;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;


public class ActMap {

	private ArrayList<ArrayList<Integer>> gameMapData1;
	private ArrayList<ArrayList<Integer>> gameMapHitData1;
	private KomaImage mapImage1;
	private KomaImage mapImage2;
	private double offsetX = 0;
	private double offsetY = 0;
	private double scrollTargetX = 0;
	private double scrollTargetY = 0;
	private double scrollSpd = 8;


	ActMap() {
		mapImage1 =  new KomaImage("image/mapbg01.png" , 40, 125);
		mapImage2 =  new KomaImage("image/mapbg02.png" , 40, 81);
	}

	void setMapData(ArrayList<ArrayList<Integer>> data, String key) {
		switch (key){
		case "BACKGROUND":
			gameMapData1 = data;
			break;
		case "HITMAP":
			gameMapHitData1 = data;
			break;
		}
	}
	
	void move(){

		scrollTargetX = Input.keyPr.RIGHT ? scrollTargetX - scrollSpd : Input.keyPr.LEFT ? scrollTargetX + scrollSpd : scrollTargetX;
		scrollTargetY = Input.keyPr.DOWN ? scrollTargetY - scrollSpd : Input.keyPr.UP ? scrollTargetY + scrollSpd : scrollTargetY;
		offsetX += (scrollTargetX - offsetX) / 8;
		offsetY += (scrollTargetY - offsetY) / 8;
	}

	void draw(Graphics2D g, JFrame w) {

		int mh = gameMapData1.size();
		for (int i = 0; i < mh; i++) {
			int mw = gameMapData1.get(i).size();
			for (int j = 0; j < mw; j++) {
				int data = gameMapData1.get(i).get(j) - (gameMapData1.get(i).get(j) / 255) * 216;
				mapImage2.drawKoma(g, w, data + 1, offsetX + 16 * j, offsetY + 16 * i, 1.0f);
			}
		}

		g.setColor(Color.WHITE);
		int mhh = gameMapHitData1.size();
		for (int i = 0; i < mhh; i++) {
			int mhw = gameMapHitData1.get(i).size();
			for (int j = 0; j < mhw; j++) {
				if (gameMapHitData1.get(i).get(j) != 255) {
					g.drawString("H", (int)offsetX + 16 * j, (int)offsetY + (16 * (i + 1)));
				}
			}
		}

	}


}

