package tnw.game2.g12;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ActPlayer {

	private KomaImage img;

	private int playerAnime;
	private int playerFoward;
	int playerScreenPosX;
	int playerScreenPosY;
	int playerMapPosX;
	int playerMapPosY;
	int playerMoveSpeed;
	int cameraLimitSize;

	int counterAnime;

	ActPlayer() {
		img = new KomaImage("image/humo.png", 3, 5);
		playerScreenPosX = GS.WINSIZE_W / 2;
		playerScreenPosY = GS.WINSIZE_H / 2;
		playerMapPosX = 500;
		playerMapPosY = 500;
		playerAnime = 0;
		playerFoward = 4;
		playerMoveSpeed = 4;
		cameraLimitSize = 150;

		counterAnime = 0;
	}

	void draw(Graphics2D g, JFrame w) {
		img.drawKoma(g, w, playerAnime, playerScreenPosX, playerScreenPosY, 1.0f);
		g.drawString("screenX : " + Integer.toString(playerScreenPosX), 50, 50);
		g.drawString("screenY : " + Integer.toString(playerScreenPosY), 50, 70);
		g.drawString("mapX : " + Integer.toString(playerMapPosX), 50, 90);
		g.drawString("mapY : " + Integer.toString(playerMapPosY), 50, 110);
	}

	private boolean checkHit(int dir) {
		switch (dir){
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		}
		return false;
	}

	public void update(ActMap map) {

		counterAnime = counterAnime > 1000 ? 0 : counterAnime + 1;

		switch (Input.DIR8) {
		case 1:
			if (checkHit(1)) {
				playerMapPosX -= playerMoveSpeed;
			}
			playerFoward = 1;
			break;
		case 2:
			if (checkHit(2)) {
				playerMapPosX -= playerMoveSpeed;
				playerMapPosY -= playerMoveSpeed;
			}
			playerFoward = 1;
			break;
		case 3:
			if (checkHit(3)) {
				playerMapPosY -= playerMoveSpeed;
			}
			playerFoward = 2;
			break;
		case 4:
			if (checkHit(4)) {
				playerMapPosY -= playerMoveSpeed;
				playerMapPosX += playerMoveSpeed;
			}
			playerFoward = 3;
			break;
		case 5:
			if (checkHit(5)) {
				playerMapPosX += playerMoveSpeed;
			}
			playerFoward = 3;
			break;
		case 6:
			if (checkHit(6)) {
				playerMapPosX += playerMoveSpeed;
				playerMapPosY += playerMoveSpeed;
			}
			playerFoward = 3;
			break;
		case 7:
			if (checkHit(7)) {
				playerMapPosY += playerMoveSpeed;
			}
			playerFoward = 4;
			break;
		case 8:
			if (checkHit(8)) {
				playerMapPosX -= playerMoveSpeed;
				playerMapPosY += playerMoveSpeed;
			}
			playerFoward = 1;
			break;
		}

		if (counterAnime % 10 == 0) {
			switch (playerFoward) {
			case 1:
				playerAnime = playerAnime < 6 || playerAnime >= 8 ? 6 : playerAnime + 1;
				break;
			case 2:
				playerAnime = playerAnime < 0 || playerAnime >= 2 ? 0 : playerAnime + 1;
				break;
			case 3:
				playerAnime = playerAnime < 9 || playerAnime >= 11 ? 9 : playerAnime + 1;
				break;
			case 4:
				playerAnime = playerAnime < 3 || playerAnime >= 5 ? 3 : playerAnime + 1;
				break;
			}
		}

		// Fix map to screen position
		if (playerMapPosX <= GS.WINSIZE_W / 2) {
			playerScreenPosX = playerMapPosX;
		}
		if (playerMapPosY <= GS.WINSIZE_H / 2) {
			playerScreenPosY = playerMapPosY;
		}
		if (playerMapPosX >= map.mapSizeX * 16 - GS.WINSIZE_W / 2) {
			playerScreenPosX = GS.WINSIZE_W / 2 + playerMapPosX - (map.mapSizeX * 16 - GS.WINSIZE_W / 2);
		}
		if (playerMapPosY >= map.mapSizeY * 16 - GS.WINSIZE_H / 2) {
			playerScreenPosY = GS.WINSIZE_H / 2 + playerMapPosY - (map.mapSizeY * 16 - GS.WINSIZE_H / 2);
		}

		map.scrollTargetX = -playerMapPosX + GS.WINSIZE_W / 2;
		map.scrollTargetY = -playerMapPosY + GS.WINSIZE_H / 2;
	}
}
