package tnw.game2.g12;

import java.awt.Font;
import java.awt.Insets;

public class GameSetting {

	static int WINDOW_SIZE_X;
	static int WINDOW_SIZE_Y;
	static int TIMERSTAGE;
	static double FRAME_TIME;
	static boolean MOUSE_CONTROLING;
	static boolean SCREEN_FREEZING;
	static boolean GAMEOVERING;
	static int DANMAKU_LIMIT, VFX_LIMIT;
	static Font DEFAULT_FONT = new Font("Default", Font.BOLD, 16);
	static Insets INSETS;

	// for mobile screen
	static double Xx, Yy;

	GameSetting() {

		TIMERSTAGE = -200;
		FRAME_TIME = 0.017;
		MOUSE_CONTROLING = false;
		WINDOW_SIZE_X = 960;
		WINDOW_SIZE_Y = 540;
		GAMEOVERING = false;
		DANMAKU_LIMIT = 5000;
		VFX_LIMIT = 5000;

	}

}
