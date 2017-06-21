package tnw.game2.g12;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//データIOと変換の管理クラス
public class GameData {

	// Get list format map data
	public ArrayList<ArrayList<Integer>> getMapData(String file) {
		ArrayList<String> tempStrList = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
		int s;
		// データの容器リストをクリア
		tempStrList.clear();
		data.clear();
		// ファイルを読み込む
		readFileByLines(file, tempStrList);

		// 行ごとにコンマ抜き
		s = tempStrList.size();
		for (int i = 0; i < s; i++) {
			data.add(convertMapdata(tempStrList.get(i), ','));
		}

		return data;
	}

	// Read text files by lines and put it into parameter "strlist"
	private void readFileByLines(String filepath, ArrayList<String> strlist) {

		InputStream is = getClass().getResourceAsStream(filepath);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String tempstr;
		try {
			while ((tempstr = br.readLine()) != null) {
				strlist.add(tempstr);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("text file read FAILED!");
		}
	}

	// Cut the string witch division by a special char into numeric list
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

// 画像データを分割して複数の矩形ブロックとして処理と描画
class KomaImage {

	private BufferedImage file; // 画像ファイル
	private int widthBlock; // 横分割数
	private int heightBlock; // 縦分割数

	// 標準的な初期化
	KomaImage(String fileName, int wBlock, int hBlock) {
		setNewImage(fileName);
		setNewBlock(wBlock, hBlock);
	}

	// 新しい画像と分割数をセット
	public void setNew(String fileName, int wBlock, int hBlock) {
		setNewImage(fileName);
		setNewBlock(wBlock, hBlock);
	}

	// 新しい画像だけをセット
	public void setNewImage(String fileName) {
		file = loadImage(fileName);
	}

	// 新しいブロック分割だけをセット
	public void setNewBlock(int wBlock, int hBlock) {
		widthBlock = wBlock <= 0 ? 1 : wBlock;
		heightBlock = hBlock <= 0 ? 1 : hBlock;
	}

	// 矩形ブロック処理と描画
	// 画像の左上の矩形ブロックにblockIndexを0から数える
	// 横4ブロック縦2ブロックの画像の場合の例:
	// [0][1][2][3]
	// [4][5][6][7]
	public void drawKoma(Graphics2D g, JFrame window, int blockIndex, double dX, double dY, float opacity) {

		if (file != null) {
			// indexの値を制限
			blockIndex = blockIndex < 0 ? 0 : blockIndex + 1;
			// 一コマの幅をゲット
			int blockW = file.getWidth() / widthBlock;
			// 一コマの高さをゲット
			int blockH = file.getHeight() / heightBlock;
			// 描画したいコマの左上端座標をゲット
			int indexX = (blockIndex % widthBlock == 0) ? blockW * (widthBlock - 1)
					: blockW * ((blockIndex % widthBlock) - 1);
			int indexY = (blockIndex % widthBlock == 0) ? blockH * (blockIndex / widthBlock - 1)
					: blockH * (blockIndex / widthBlock);
			// 不透明度
			g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)));

			// 描画
			g.drawImage(file,
					// 画面に描画する場所
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

	// 画像ファイルを読み込む
	private BufferedImage loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			System.out.println("■WARNING : FAILED TO LOAD IMAGE■");
		}
		return image;
	}

}