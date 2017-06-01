package tnw.game2.g12;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GameData {

	ArrayList<String> inputData = new ArrayList<String>();
	ArrayList<ArrayList<Integer>> gameMapData = new ArrayList<ArrayList<Integer>>();

	GameData() {



	}

	public void update() {

	}

	public ArrayList<ArrayList<Integer>> getMapData(){
		return gameMapData;

	}

	public void loadMapData(String file){
		readFileByLines(file, inputData);
		for (int i = 0; i < inputData.size(); i++){
			gameMapData.add(convertMapdata(inputData.get(i), ','));
		}
	}

    @SuppressWarnings("unchecked")
	public <T> T getData(String key) {
        T re = null;
        Class<T> c = null;
        switch (key){
        case "MAP":
        	c = (Class<T>) gameMapData.getClass();
            re = c.cast(gameMapData);
        	break;
        }
        if (re != null) {
            return re;
        }
        return null;
    }

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