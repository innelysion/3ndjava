package tnw.game2.g12;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameData {

	private ArrayList<String> inputData = new ArrayList<String>();
	private ArrayList<String> inputHitData = new ArrayList<String>();
	private ArrayList<ArrayList<Integer>> gameMapData = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> gameMapHitData = new ArrayList<ArrayList<Integer>>();

	GameData() {

	}

	public void update() {

	}

//	public ArrayList<ArrayList<Integer>> getMapData() {
//		return gameMapData;
//
//	}

	public void loadMapData(String file) {
		readFileByLines(file, inputData);
		for (int i = 0; i < inputData.size(); i++) {
			gameMapData.add(convertMapdata(inputData.get(i), ','));
		}
	}

	public void loadMapHitData(String file) {
		readFileByLines(file, inputHitData);
		for (int i = 0; i < inputHitData.size(); i++) {
			gameMapHitData.add(convertMapdata(inputHitData.get(i), ','));
		}
	}

	public ArrayList<ArrayList<Integer>> getData(String key) {
		switch (key) {
		case "MAP":
			return gameMapData;
		case "MAPHIT":
			return gameMapHitData;
		}
		return null;

	}

	// @SuppressWarnings("unchecked")
	// public <T> T getData(String key) {
	// T re = null;
	// Class<T> c = null;
	// switch (key) {
	// case "MAP1":
	// c = (Class<T>) gameMapData.getClass();
	// re = c.cast(gameMapData);
	// break;
	// case "MAPHIT1":
	// c = (Class<T>) gameMapHitData.getClass();
	// re = c.cast(gameMapHitData);
	// break;
	// }
	// if (re != null)
	//
	// {
	// return re;
	// }
	// System.out.println("■WARNING : FAILED TO GET DATA,【null】 RETURNED■");
	// return null;
	// }

	public void drawMsg(Graphics2D g, JFrame wind) {

	}

	public void readFileByLines(String file, ArrayList<String> strlist) {

		try {

			FileReader fr = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fr);

			String tempdata;
			while ((tempdata = bReader.readLine()) != null) {
				strlist.add(tempdata);
			}

			bReader.close();

		} catch (IOException e) {
			System.out.println("text file read FAILED!");
		}
	}

	private ArrayList<Integer> convertMapdata(String tempdata, char ch) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String str = "";

		for (char i : tempdata.toCharArray()) {
			if (i == ch) {
				list.add(Integer.parseInt(str));
				str = "";
				continue;
			}
			str += i;
		}
		list.add(Integer.parseInt(str));

		return list;
	}

}

interface GameTools {

	// 画像ファイルを読み込む

	public default BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			File f1 = new File(filename);
			image = ImageIO.read(f1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	// コマ画像を描画
	public default void drawKoma(Graphics2D g, JFrame window, KomaImage image, int imageIndex, double dX, double dY,
			float opacity) {

		// 一コマの幅をゲット
		int blockW = image.file.getWidth() / image.widthBlock;
		// 一コマの高さをゲット
		int blockH = image.file.getHeight() / image.heightBlock;
		// 描画したいコマの左上端座標をゲット
		int indexX = (imageIndex % image.widthBlock == 0)// if
				? blockW * (image.widthBlock - 1)// do
				: blockW * ((imageIndex % image.widthBlock) - 1);// else do
		int indexY = (imageIndex % image.widthBlock == 0)// if
				? blockH * (imageIndex / image.widthBlock - 1)// do
				: blockH * (imageIndex / image.widthBlock);// else do
		// 不透明度
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));

		// 描画
		g.drawImage(image.file,
				// 画面に出したいところ
				(int) (dX), // 左上端X座標
				(int) (dY), // 左上端Y座標
				(int) (dX + blockW), // 右下端X座標
				(int) (dY + blockH), // 右下端Y座標
				// 画像ファイルのどこを使う
				(int) (indexX), // 左上端X座標
				(int) (indexY), // 左上端Y座標
				(int) (indexX + blockW), // 右下端X座標
				(int) (indexY + blockH), // 右下端Y座標
				window);

		// 不透明度リセット

		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));

	}

}


class KomaImage implements GameTools {

	public BufferedImage file;
	public int widthBlock;
	public int heightBlock;

	KomaImage(String fileName, int wBlock, int hBlock) {
		file = loadImage(fileName);
		widthBlock = wBlock;
		heightBlock = hBlock;
	}

}