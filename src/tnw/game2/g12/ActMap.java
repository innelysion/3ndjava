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
	private double offsetX = 4;
	private double offsetY = 24;
	private double scrollSpd = 8;

	double scrollTargetX = 4;
	double scrollTargetY = 24;
	int mapSizeX = 0;
	int mapSizeY = 0;

	ActMap() {
		mapImage1 = new KomaImage("image/mapbg01.png", 40, 125);
		mapImage2 = new KomaImage("image/mapbg02.png", 40, 81);
	}

	void setMapData(ArrayList<ArrayList<Integer>> data, String key) {
		switch (key) {
		case "BACKGROUND":
			gameMapData1 = data;
			break;
		case "HITMAP":
			gameMapHitData1 = data;
			break;
		}

		mapSizeX = data.get(0).size();
		mapSizeY = data.size();
	}

	void move(ActPlayer player) {

		// Set map scroll
		// scrollTargetX = Input.keyPr.RIGHT ? scrollTargetX - scrollSpd
		// : Input.keyPr.LEFT ? scrollTargetX + scrollSpd : scrollTargetX;
		// scrollTargetY = Input.keyPr.DOWN ? scrollTargetY - scrollSpd
		// : Input.keyPr.UP ? scrollTargetY + scrollSpd : scrollTargetY;

		// Value limit
		scrollTargetX = scrollTargetX >= 4 ? 4 : scrollTargetX;
		scrollTargetY = scrollTargetY >= 24 ? 24 : scrollTargetY;
		scrollTargetX = scrollTargetX <= -mapSizeX * 16 + GS.WINSIZE_W - 2 ? -mapSizeX * 16 + GS.WINSIZE_W - 2
				: scrollTargetX;
		scrollTargetY = scrollTargetY <= -mapSizeY * 16 + GS.WINSIZE_H - 2 ? -mapSizeY * 16 + GS.WINSIZE_H - 2
				: scrollTargetY;

		// Set final scroll position
		offsetX += (scrollTargetX - offsetX) / 2;
		offsetY += (scrollTargetY - offsetY) / 2;

	}

	void draw(Graphics2D g, JFrame w) {


		int mh = gameMapData1.size();
		for (int i = 0; i < mh; i++) {
			int mw = gameMapData1.get(i).size();
			for (int j = 0; j < mw; j++) {
				if (!(offsetX + 16 * j < -16) && !(offsetY + 16 * i < -16) && !(offsetX + 16 * j > GS.WINSIZE_W)
						&& !(offsetY + 16 * i > GS.WINSIZE_H)) {
					int data = gameMapData1.get(i).get(j) - (gameMapData1.get(i).get(j) / 255) * 216;
					mapImage1.drawKoma(g, w, data, offsetX + 16 * j, offsetY + 16 * i, 1.0f);
				}
			}
		}

		g.setColor(Color.WHITE);
		int mhh = gameMapHitData1.size();
		for (int i = 0; i < mhh; i++) {
			int mhw = gameMapHitData1.get(i).size();
			for (int j = 0; j < mhw; j++) {
				if (gameMapHitData1.get(i).get(j) != 255) {
					g.drawString("H", (int) offsetX + 16 * j, (int) offsetY + (16 * (i + 1)));
				}
			}
		}
		g.drawString("mapScrollX : " + Double.toString(scrollTargetX), 50, 130);
		g.drawString("mapScrollY : " + Double.toString(scrollTargetY), 50, 150);

	}

}
