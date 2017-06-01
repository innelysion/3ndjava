package tnw.game2.g12;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Act_Map {

	ArrayList<ArrayList<Integer>> gameMapData;

	Act_Map() {

	}

	void setMapData(ArrayList<ArrayList<Integer>> data) {
		gameMapData = data;
	}

	void drawMap(Graphics2D g, JFrame wind) {
		g.setFont(GameSetting.DEFAULT_FONT);
		g.setColor(Color.MAGENTA);

		for (int i = 0; i < gameMapData.size(); i++) {
			for (int j = 0; j < gameMapData.get(i).size(); j++) {
				if (gameMapData.get(i).get(j) != 255) {
					g.drawString("H", j * 10, i * 10);
				}
			}
		}
	}
}
