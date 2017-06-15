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

	int mapSizeW;
	int mapSizeH;

	ActMap() {
		mapImage1 = new KomaImage("image/mapbg01.png", 40, 125);
		mapImage2 = new KomaImage("image/mapbg02.png", 40, 81);
		mapSizeW = 0;
		mapSizeH = 0;
	}

	public void setMapData(ArrayList<ArrayList<Integer>> data, String key) {
		switch (key) {
		case "BACKGROUND":
			gameMapData1 = data;
			break;
		case "HITMAP":
			gameMapHitData1 = data;
			break;
		}

	}

	public void update() {
		mapSizeW = gameMapData1.get(0).size();
		mapSizeH = gameMapData1.size();
	}
	
	public boolean isHit(int x, int y){
		return gameMapHitData1.get(y).get(x) == 2049;
	}
	

	public void draw(Graphics2D g, JFrame w, double dX, double dY) {
		
		ArrayList<ArrayList<Integer>> usingMapData = gameMapData1;
		ArrayList<ArrayList<Integer>> usingMapHitData = gameMapHitData1;
		KomaImage usingMapImage = mapImage1;

		for (int i = 0; i < mapSizeH; i++) {
			for (int j = 0; j < mapSizeW; j++) {
				if (!(dX + 16 * j < -16) && !(dY + 16 * i < -16) && !(dX + 16 * j > GS.WINSIZE_W)
						&& !(dY + 16 * i > GS.WINSIZE_H)) {
					int data = usingMapData.get(i).get(j) - (usingMapData.get(i).get(j) / 255) * 216;
					usingMapImage.drawKoma(g, w, data, (int) dX + 16 * j, (int) dY + 16 * i, 1.0f);
				}
			}
		}
		
//		g.setFont(GS.DEFAULT_FONT);
//		g.setColor(Color.WHITE);
//		for (int i = 0; i < mapSizeH; i++) {
//			for (int j = 0; j < mapSizeW; j++) {
//				if (usingMapHitData.get(i).get(j) != 255) {
//					g.drawString("H", (int) dX + 16 * j, (int) dY + (16 * (i + 1)));
//				}
//			}
//		}

	}

}
