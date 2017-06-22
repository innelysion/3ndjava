package tnw.game2.g12;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ActActor extends ActCharacters {

	private KomaImage img;
	private String name;
	private int id;
	private int animeIndex;
	private int animeCnt;
	private int moveMode;
	private int offsetSize;
	private int timer;

	ActActor(int who) {
		super();
		switch (who) {
		case 1:
			img = new KomaImage("image/humo.png", 3, 5);
			name = "主人公";
			id = 1;
			moveMode = 1;
			mapPosX = 820;
			mapPosY = 790;
			setMoveSpd(8);
			setMoveSpdObl(6);
			setSize(40, 48);
			offsetSize = 8;
			break;
		case 2:
			img = new KomaImage("image/mon01.png", 3, 5);
			name = "自走イノシシ神";
			id = 2;
			moveMode = 2;
			mapPosX = 1600;
			mapPosY = 450;
			setMoveSpd(2);
			setMoveSpdObl(1);
			setSize(80, 64);
			break;
		case 3:
			img = new KomaImage("image/mon09.png", 3, 4);
			name = "道に迷う人";
			id = 3;
			moveMode = 3;
			mapPosX = 256;
			mapPosY = 800;
			setSize(40, 48);
			setMoveSpd(4);
			setMoveSpdObl(1);
			offsetSize = 16;
			break;
		}
	}

	public void update(ActMap m) {
		super.update(m);

		// Main timer for actions
		timer = timer > 9999 ? 0 : timer + 1;

		if (id == 3) {
			if (flag == 0) {
				flag = 1;
				moveTo(432, 208);
			}
			if (mapPosX == 432 && mapPosY == 208 && flag == 1) {
				flag = 2;
				moveTo(1536, 560);
			}
			if (mapPosX == 1536 && mapPosY == 560 && flag == 2) {
				flag = 3;
				moveTo(2272, 1072);
			}
			if (mapPosX == 2272 && mapPosY == 1072 && flag == 3) {
				flag = 4;
//				moveTo(432, 208);
			}
		}

		// Movement mode
		if (!isFreezing()) {
			animation();
			switch (moveMode) {
			case 1:
				move();
				break;
			case 2:
				randomMove();
				break;
			case 3:
				break;
			}
		}
	}

	// Draw image of character
	public void draw(Graphics2D g, JFrame w, double cameraX, double cameraY) {
		img.drawKoma(g, w, animeIndex, mapPosX - cameraX, mapPosY - offsetSize - cameraY, 1.0f);
	}

	// *START*Get & Set
	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	// Move and jump
	private void move() {
		// Check moving or not
		if (Input.DIR8 != 0) {
			setMoving(true);
		} else {
			setMoving(false);
		}
		// RPG or ACT movement process
		if (!GS.ACT_GAMEMODE) {
			
			// RPG mode DIR8 movement process
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
		} else {
			// Jumping or not
			if ((!Input.keyPr.UP && getForce() > 0) || isHitUp() || getForce() < 0) {
				setJumping(true);
			}
			// ACT mode DIR8 movement process
			switch (Input.DIR8) {
			case 1:
				moveLeft();
				break;
			case 2:
				jump();
				moveLeft();
				break;
			case 3:
				jump();
				break;
			case 4:
				jump();
				moveRight();
				break;
			case 5:
				moveRight();
				break;
			case 6:
				moveRight();
				break;
			case 7:
				break;
			case 8:
				moveLeft();
				break;
			}
			gravity();
		}
	}

	// Walking animation
	private void animation() {
		animeCnt = animeCnt > 1000 ? 0 : animeCnt + 1;
		if (animeCnt % 10 == 0 && isMoving()) {
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

}
