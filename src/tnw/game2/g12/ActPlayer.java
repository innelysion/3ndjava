package tnw.game2.g12;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ActPlayer {

	private KomaImage img;

	private int animeIndex;
	private int animeCnt;
	private int facing;
	private int mapPosX;
	private int mapPosY;
	private int moveSpeed;
	private int moveSpeedOblique;

	private boolean freeCamera;

	// read only
	int X;
	int Y;

	ActPlayer() {
		img = new KomaImage("image/humo.png", 3, 5);
		mapPosX = 250;
		mapPosY = 250;
		animeIndex = 0;
		facing = 4;
		moveSpeed = 4;
		moveSpeedOblique = 3;
		animeCnt = 0;
		freeCamera = false;
	}

	public void update() {
		if (Input.keyRe.Z) {
			freeCamera = !freeCamera;
		}

		if (!freeCamera) {
			move();
		} else {
			facing = 3;
			mapPosX++;
		}
		animation();
		X = mapPosX;
		Y = mapPosY;
	}

	public void draw(Graphics2D g, JFrame w, double dX, double dY) {
		img.drawKoma(g, w, animeIndex, dX, dY, 1.0f);
	}

	private void animation() {
		animeCnt = animeCnt > 1000 ? 0 : animeCnt + 1;
		if (animeCnt % 10 == 0) {
			switch (facing) {
			case 1:
				animeIndex = animeIndex < 6 || animeIndex >= 8 ? 6 : animeIndex + 1;
				break;
			case 2:
				animeIndex = animeIndex < 0 || animeIndex >= 2 ? 0 : animeIndex + 1;
				break;
			case 3:
				animeIndex = animeIndex < 9 || animeIndex >= 11 ? 9 : animeIndex + 1;
				break;
			case 4:
				animeIndex = animeIndex < 3 || animeIndex >= 5 ? 3 : animeIndex + 1;
				break;
			}
		}
	}

	private void move() {
		if (Input.keyPr.SHIFT) {
			moveSpeed = 6;
			moveSpeedOblique = 4;
		} else {
			moveSpeed = 4;
			moveSpeedOblique = 3;
		}

		switch (Input.DIR8) {
		case 1:
			mapPosX -= moveSpeed;
			facing = 1;
			break;
		case 2:
			mapPosX -= moveSpeedOblique;
			mapPosY -= moveSpeedOblique;
			facing = 1;
			break;
		case 3:
			mapPosY -= moveSpeed;
			facing = 2;
			break;
		case 4:
			mapPosY -= moveSpeedOblique;
			mapPosX += moveSpeedOblique;
			facing = 3;
			break;
		case 5:
			mapPosX += moveSpeed;
			facing = 3;
			break;
		case 6:
			mapPosX += moveSpeedOblique;
			mapPosY += moveSpeedOblique;
			facing = 3;
			break;
		case 7:
			mapPosY += moveSpeed;
			facing = 4;
			break;
		case 8:
			mapPosX -= moveSpeedOblique;
			mapPosY += moveSpeedOblique;
			facing = 1;
			break;
		}
	}

}
