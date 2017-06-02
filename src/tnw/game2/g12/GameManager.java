package tnw.game2.g12;

public class GameManager {


	GameData gamedata = new GameData();
	ActMap map = new ActMap();

	GameManager(){
		gamedata.loadMapData("S0_Map.csv");
		gamedata.loadMapHitData("S0_Hit.csv");
		map.setMapData1(gamedata.getData("MAP"), gamedata.getData("MAPHIT"));
	}

	public void update(){

	}



}
