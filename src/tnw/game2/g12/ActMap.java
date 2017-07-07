package tnw.game2.g12;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ActMap {

	private ArrayList<ArrayList<Integer>> gameMapData;
	private ArrayList<ArrayList<Integer>> gameMapHitData;
	private KomaImage mapImage;
	private int mapSizeWidth;
	private int mapSizeHeight;

	// Read only
	int mapSizeW;
	int mapSizeH;

	ActMap() {
		mapImage = new KomaImage("image/mapbg01.png", 40, 125);
		//mapImage = new KomaImage("image/mapbg02.png", 40, 81);
		mapSizeWidth = 0;
		mapSizeHeight = 0;
		mapSizeW = 0;
		mapSizeH = 0;
	}

	public void update(){
	}

	public void setMapData(ArrayList<ArrayList<Integer>> data, String key) {
		switch (key) {
		case "BACKGROUND":
			gameMapData = data;
			mapSizeWidth = gameMapData.get(0).size();
			mapSizeHeight = gameMapData.size();
			mapSizeW = mapSizeWidth;
			mapSizeH = mapSizeHeight;
			break;
		case "HITMAP":
			gameMapHitData = data;
			break;
		}
	}

	public int getHitValue(int x, int y){
		return gameMapHitData.get(y).get(x);
	}

	public void draw(Graphics2D g, JFrame w, double dX, double dY) {

		for (int i = 0; i < mapSizeHeight; i++) {
			for (int j = 0; j < mapSizeWidth; j++) {
				if (!(dX + 16 * j < -16) && !(dY + 16 * i < -16) && !(dX + 16 * j > GS.WINSIZE_W)
						&& !(dY + 16 * i > GS.WINSIZE_H)) {
					int data = gameMapData.get(i).get(j) - (gameMapData.get(i).get(j) / 255) * 216;
					mapImage.drawKoma(g, w, data, (int) dX + 16 * j, (int) dY + 16 * i, 1.0f);
				}
			}
		}

//		g.setFont(GS.DEFAULT_FONT);
//		g.setColor(Color.WHITE);
//		for (int i = 0; i < mapSizeH; i++) {
//			for (int j = 0; j < mapSizeW; j++) {
//				if (gameMapHitData.get(i).get(j) != 255) {
//					g.drawString("H", (int) dX + 16 * j, (int) dY + (16 * (i + 1)));
//				}
//			}
//		}

	}

}
