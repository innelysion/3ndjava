package tnw.game2.g12;

public class GameManager {


	GameData gamedata = new GameData();
	Act_Map map = new Act_Map();

	GameManager(){
		gamedata.loadMapData("D:\\javaIO\\S0_Hit.csv");
		map.setMapData(gamedata.getData("MAP"));
	}

	public void update(){

	}



}
