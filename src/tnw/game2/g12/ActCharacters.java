package tnw.game2.g12;

import java.util.Random;
import java.util.Stack;

public abstract class ActCharacters {

	public Stack<Node> path = new Stack<Node>();

	private ActMap map;
	private AStarFindPath findpath = new AStarFindPath();
	private int[][] mapHitDataForFindPath;

	protected int animeIndex;
	protected int animeCnt;
	protected int facing;
	protected int mapPosX;
	protected int mapPosY;
	protected boolean findingPath;
	protected int offsetSize;
	protected int flag;;
	private int sizeWidth;
	private int sizeHeight;
	private boolean isMoving;
	private boolean isFollowingPath;
	private boolean isJumping;
	private boolean isFreezing;
	private int moveSpeed;
	private int moveSpeedOblique;
	private double force;
	private double jumpPower;
	private double smoothJumpPower;
	private double g;
	private int randomMoveCnt;
	private int randomMoveDir;
	private int tryToFollowTimes;

	protected int sizeW;
	protected int sizeH;
	protected int X;
	protected int Y;

	ActCharacters() {
		flag = 0;
		mapPosX = 0;
		mapPosY = 0;
		animeIndex = 3;
		facing = 4;
		moveSpeed = 4;
		moveSpeedOblique = 3;
		randomMoveCnt = 0;
		randomMoveDir = 0;
		isMoving = false;
		isJumping = false;
		isFreezing = false;
		isFollowingPath = false;
		animeCnt = 0;
		sizeWidth = 0;
		sizeHeight = 0;

		force = 0;
		smoothJumpPower = 4;
		jumpPower = 10;
		g = 0.5;

		findingPath = false;
		tryToFollowTimes = 0;

		sizeW = sizeWidth;
		sizeH = sizeHeight;
		X = mapPosX;
		Y = mapPosY;
	}

	public void update(ActMap m) {
		// Get map data
		map = m;
		// For read only
		sizeW = sizeWidth;
		sizeH = sizeHeight;
		X = mapPosX;
		Y = mapPosY;

		// Auto follow path
		if (isFollowingPath && !path.isEmpty()) {
			isMoving = true;
			int targetX = path.get(path.size() - 1).y;
			int targetY = path.get(path.size() - 1).x;

			if (mapPosX > targetX) {
				moveLeft();
				tryToFollowTimes++;
			}
			if (mapPosX < targetX) {
				moveRight();
				tryToFollowTimes++;
			}
			if (mapPosY > targetY) {
				moveUp();
				tryToFollowTimes++;
			}
			if (mapPosY < targetY) {
				moveDown();
				tryToFollowTimes++;
			}

			if (tryToFollowTimes > 10) {
				mapPosX = targetX;
				mapPosY = targetY;
			}
			if (mapPosX == targetX && mapPosY == targetY) {
				path.pop();
				tryToFollowTimes = 0;
			}

			if (path.isEmpty()) {
				isFollowingPath = false;
				isMoving = false;
			}
		}
	}

	// *START*Get & Set
	public void setSize(int w, int h) {
		sizeWidth = w;
		sizeHeight = h;
	}

	public void setMoveSpd(int i) {
		moveSpeed = i;
	}

	public void setMoveSpdObl(int i) {
		moveSpeedOblique = i;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean b) {
		isMoving = b;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean b) {
		isJumping = b;
	}

	public void freeze() {
		isFreezing = true;
	}

	public void defreeze() {
		isFreezing = false;
	}

	public boolean isFreezing() {
		return isFreezing;
	}

	public boolean isFollowingPath() {
		return isFollowingPath;
	}

	public void setFollowingPath(boolean b) {
		isFollowingPath = b;
	}

	// *END*

	// Find path (using A*) to target position
	public void moveTo(int x, int y) {
		// Clear exist path
		path.clear();

		// Create map node data when find path first time
		if (mapHitDataForFindPath == null) {
			mapHitDataForFindPath = new int[map.mapSizeH][map.mapSizeW];
			for (int i = 0; i < map.mapSizeH; i++) {
				for (int j = 0; j < map.mapSizeW; j++) {
					mapHitDataForFindPath[i][j] = map.getHitValue(j, i) == GS.MAP_HITVALUE ? 1 : 0;
				}
			}

			// Soucre data fit character's size (shift 16x16 map block two
			// times)
			for (int i = 0; i < map.mapSizeH; i++) {
				for (int j = 0; j < map.mapSizeW; j++) {
					if (i > 1 && j > 1 && mapHitDataForFindPath[i][j] == 1) {
						mapHitDataForFindPath[i - 1][j] = 1;
						mapHitDataForFindPath[i][j - 1] = 1;
						mapHitDataForFindPath[i - 1][j - 1] = 1;
						mapHitDataForFindPath[i - 2][j] = 1;
						mapHitDataForFindPath[i][j - 2] = 1;
						mapHitDataForFindPath[i - 2][j - 2] = 1;
					}
				}
			}
		}
		// Create start point (character's position) and end point
		Node start = new Node((mapPosY + offsetSize) / 16, mapPosX / 16);
		Node end = new Node(y / 16, x / 16);

		// Use A* to get a path node stack
		path = findpath.search(mapHitDataForFindPath, start, end);

		// Set following path and resize it
		isFollowingPath = true;
		for (Node n : path) {
			n.x *= 16;
			n.y *= 16;
		}
	}

	// Random dir4 movement
	public void randomMove() {
		// Get random movement dirction and time interval
		randomMoveCnt++;
		if (randomMoveCnt % (new Random().nextInt(200) + 1) == 0) {
			randomMoveDir = new Random().nextInt(4);
		}
		isMoving = true;
		// Movement
		switch (randomMoveDir) {
		case 0:
			moveLeft();
			break;
		case 1:
			moveUp();
			break;
		case 2:
			moveRight();
			break;
		case 3:
			moveDown();
			break;
		}
	}

	// *START*Hit checks
	public boolean isHitLeft() {
		for (int i = mapPosY + 2; i <= mapPosY + sizeHeight - 2; i += 4) {
			if (isHitMap(mapPosX - 1, i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHitUp() {
		for (int i = mapPosX + 2; i <= mapPosX + sizeWidth - 2; i += 4) {
			if (isHitMap(i, mapPosY - 1)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHitRight() {
		for (int i = mapPosY + 2; i <= mapPosY + sizeHeight - 2; i += 4) {
			if (isHitMap(mapPosX + sizeWidth + 1, i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isHitDown() {
		for (int i = mapPosX + 2; i <= mapPosX + sizeWidth - 2; i += 4) {
			if (isHitMap(i, mapPosY + sizeHeight + 1)) {
				return true;
			}
		}
		return false;
	}
	// *END*

	// *START*Dir8 movement and facing
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
	// *END*

	// Get Y-Axis(for gravity & jumping process) force value
	public double getForce() {
		return force;
	}

	// Gravity and jumping process
	protected void gravity() {
		force -= g;
		if (force > 0) {
			for (int i = 0; i < (int) force; i++) {
				if (isHitUp()) {
					force = 0;
				} else {
					mapPosY -= 1;
				}
			}
		} else if (force < 0) {
			for (int i = 0; i > (int) force; i--) {
				if (isHitDown()) {
					isJumping = false;
					force = 0;
				} else {
					mapPosY += 1;
				}
			}
		}
	}

	// Jump Action
	protected void jump() {
		if (!isJumping) {
			if (force < smoothJumpPower) {
				force += smoothJumpPower;
			} else {
				force++;
			}
			if (force > jumpPower) {
				isJumping = true;
			}
		}
	}

	// Map impassable aera check
	protected boolean isHitMap(int x, int y) {
		if (x / 16 >= map.mapSizeW || y / 16 >= map.mapSizeH || x < 0 || y < 0) {
			return true;
		} else {
			return map.getHitValue(x / 16, y / 16) == GS.MAP_HITVALUE;
		}
	}

}
