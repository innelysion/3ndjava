package tnw.game2.g12;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


//◆MAIN◆//
public class Act_GameMain {


	GameSetting setting = new GameSetting();
	GameInput input = new GameInput();
	GameManager manager = new GameManager();

	JFrame wind = new JFrame("2ndSessionProject");
	BufferStrategy offimage;

	Timer TM = new Timer();
	timer_TSK MAINLOOP = new timer_TSK();

	// -----------------------------
	// 初期化用の関数
	// ・window生成
	// ・window大きさの指定
	// ・windowの場所
	// -----------------------------

	Act_GameMain() {

		// Setup javaframe window & graphics2D buffer
		wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		wind.setBackground(new Color(0, 0, 0));// 色指定
		wind.setResizable(false);// ｻｲｽﾞ変更不可
		wind.setVisible(true);// 表示or非表示

		GameSetting.INSETS = wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		wind.setSize(GameSetting.WINDOW_SIZE_X + GameSetting.INSETS.left + GameSetting.INSETS.right,
				GameSetting.WINDOW_SIZE_Y + GameSetting.INSETS.top + GameSetting.INSETS.bottom);
		wind.setLocationRelativeTo(null);// 中央に表示


		try {
			Thread.sleep(10);
			wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		} catch (Exception e) {
			GameInput.K_ESC_R = true;// もしﾀﾞﾌﾞﾙﾊﾞｯﾌｧ生成失敗すると強制再起動
		}
		offimage = wind.getBufferStrategy();

		// For input class
		wind.addMouseListener(input);
		wind.addMouseMotionListener(input);
		wind.addKeyListener(input);
		wind.addMouseWheelListener(input);

		// Setup timer task
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

		TM.schedule(MAINLOOP, 17, 17);

	}// GameMain end

	// ◆メーン処理繰り返しタスク◆//
	class timer_TSK extends TimerTask {

		public void run() {
			if (GameInput.K_ESC_R) {

				this.cancel();

			} else {

				// Game data update
				mainUpdate();

				// Graphics for new frame
				Graphics g2 = offimage.getDrawGraphics();
				Graphics2D g = (Graphics2D) g2;

				if (offimage.contentsLost() == false) {

					// Clear the prev frame
					g.translate(GameSetting.INSETS.left, GameSetting.INSETS.top);
					g.clearRect(0, 0, GameSetting.WINDOW_SIZE_X, GameSetting.WINDOW_SIZE_Y);

					// Main Graphics Update
					mainDraw(g);

					// Dispose last frame graphics
					offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
					g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

				}


			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end

		private void mainDraw(Graphics2D g) {

			// MAP描画
			manager.map.drawMap(g, wind);

		}

		private void mainUpdate() {

			// input update
			input.update(wind);
			// manager update
			manager.update();
			// stage main

			// All Update Here

		}

	}// timer task class end

	// ◆ここからプログラムが動く◆//
	public static void main(String[] args) throws Throwable {

		Act_GameMain Game = new Act_GameMain();
		do {
			Thread.sleep(250);
			if (GameInput.K_ESC_R) {
				Game.input = null;

				if (Game.offimage != null) {
					Game.offimage.dispose();
				}
				Game.offimage = null;
				Game.wind.dispose();
				Game.wind = null;
				Game.MAINLOOP.cancel();
				Game.MAINLOOP = null;
				Game.TM.cancel();
				Game.TM.purge();
				Game.TM = null;

				Game.finalize();
				Game = null;

				Game = new Act_GameMain();
			}
			Thread.sleep(50);
		} while (Game != null);

	}
}
