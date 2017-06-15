package tnw.game2.g12;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;

public class ActPlayer {

	private KomaImage img;
	private ActMap map;

	private int animeIndex;
	private int animeCnt;
	private int facing;
	private int mapPosX;
	private int mapPosY;
	private int moveSpeed;
	private int moveSpeedOblique;
	private int randomMoveCnt;
	private int RandomMoveDir;
	private boolean isMoving;

	private boolean freeCamera;

	int sizeW;
	int sizeH;
	int X;
	int Y;

	ActPlayer() {
		img = new KomaImage("image/humo.png", 3, 5);
		mapPosX = 250;
		mapPosY = 250;
		animeIndex = 3;
		facing = 4;
		moveSpeed = 4;
		moveSpeedOblique = 3;
		randomMoveCnt = 0;
		RandomMoveDir = 0;
		isMoving = false;
		animeCnt = 0;
		freeCamera = false;
		sizeW = 40;
		sizeH = 56;
	}

	public void update(ActMap m) {
		map = m;

		if (Input.keyRe.Z) {
			freeCamera = !freeCamera;
		}

		if (!freeCamera) {
			move();
		} else {
			isMoving = true;
			randomMove();
		}
		animation();
		X = mapPosX;
		Y = mapPosY;
	}

	public void draw(Graphics2D g, JFrame w, double dX, double dY) {
		img.drawKoma(g, w, animeIndex, dX, dY, 1.0f);
	}

	private void move() {
		if (Input.keyPr.SHIFT) {
			moveSpeed = 6;
			moveSpeedOblique = 4;
		} else {
			moveSpeed = 4;
			moveSpeedOblique = 3;
		}

		if (Input.DIR8 != 0) {
			isMoving = true;
		} else {
			isMoving = false;
		}

		switch (Input.DIR8) {
		case 1:
			moveLeft();
			break;
		case 2:
			moveUpperLeft();
			break;
		case 3:
			moveUp();
			break;
		case 4:
			moveUpperRight();
			break;
		case 5:
			moveRight();
			break;
		case 6:
			moveLowerRight();
			break;
		case 7:
			moveDown();
			break;
		case 8:
			moveLowerLeft();
			break;
		}
	}

	private void animation() {
		animeCnt = animeCnt > 1000 ? 0 : animeCnt + 1;
		if (animeCnt % 10 == 0 && isMoving) {
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

	public void randomMove() {
		randomMoveCnt++;
		if (randomMoveCnt % 30 == 0) {
			RandomMoveDir = new Random().nextInt(4) + 1;
		}
		switch (RandomMoveDir) {
		case 1:
			moveLeft();
			break;
		case 2:
			moveUp();
			break;
		case 3:
			moveRight();
			break;
		case 4:
			moveDown();
			break;
		}
	}

	public boolean isHitLeft() {
		for (int i = mapPosY + 2; i <= mapPosY + sizeH - 2; i += 4) {
			if (isHitMap(mapPosX - 1, i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHitUp() {
		for (int i = mapPosX + 2; i <= mapPosX + sizeW - 2; i += 4) {
			if (isHitMap(i, mapPosY - 1)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHitRight() {
		for (int i = mapPosY + 2; i <= mapPosY + sizeH - 2; i += 4) {
			if (isHitMap(mapPosX + sizeW + 1, i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHitDown() {
		for (int i = mapPosX + 2; i <= mapPosX + sizeW - 2; i += 4) {
			if (isHitMap(i, mapPosY + sizeH + 1)) {
				return true;
			}
		}
		return false;
	}

	public void moveLeft() {
		facing = 1;
		for (int i = 0; i < moveSpeed; i++) {
			mapPosX -= !isHitLeft() ? 1 : 0;
		}
	}

	public void moveUpperLeft() {
		facing = 1;
		for (int i = 0; i < moveSpeedOblique; i++) {
			mapPosX -= !isHitLeft() ? 1 : 0;
			mapPosY -= !isHitUp() ? 1 : 0;
		}
	}

	public void moveUp() {
		facing = 2;
		for (int i = 0; i < moveSpeed; i++) {
			mapPosY -= !isHitUp() ? 1 : 0;
		}
	}

	public void moveUpperRight() {
		facing = 3;
		for (int i = 0; i < moveSpeedOblique; i++) {
			mapPosX += !isHitRight() ? 1 : 0;
			mapPosY -= !isHitUp() ? 1 : 0;
		}
	}

	public void moveRight() {
		facing = 3;
		for (int i = 0; i < moveSpeed; i++) {
			mapPosX += !isHitRight() ? 1 : 0;
		}
	}

	public void moveLowerRight() {
		facing = 3;
		for (int i = 0; i < moveSpeedOblique; i++) {
			mapPosX += !isHitRight() ? 1 : 0;
			mapPosY += !isHitDown() ? 1 : 0;
		}
	}

	public void moveDown() {
		facing = 4;
		for (int i = 0; i < moveSpeed; i++) {
			mapPosY += !isHitDown() ? 1 : 0;
		}
	}

	public void moveLowerLeft() {
		facing = 1;
		for (int i = 0; i < moveSpeedOblique; i++) {
			mapPosX -= !isHitLeft() ? 1 : 0;
			mapPosY += !isHitDown() ? 1 : 0;
		}
	}

	private boolean isHitMap(int x, int y) {
		if (x / 16 >= map.mapSizeW || y / 16 >= map.mapSizeH || x < 0 || y < 0) {
			return true;
		} else {
			return map.isHit(x / 16, y / 16);
		}
	}

}
