package tnw.game2.g12;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

class GameMain {

	// Initialize jframe window and graphic
	JFrame window = new JFrame("RPG/ACT MAP");
	Insets insets;
	BufferStrategy buffer;

	// Initialize main loop timer task
	Timer timer = new Timer();
	MainLoop mainloop = new MainLoop();

	// Initialize input
	Input input = new Input();

	// Initialize game manager
	ActManager manager = new ActManager();

	// Main Constructor
	GameMain() {

		// Setup jframe window and graphic
		window.setIgnoreRepaint(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(Color.BLACK);
		window.setResizable(false);
		window.setVisible(true);
		insets = window.getInsets();
		window.setSize(GS.WINSIZE_W + insets.left + insets.right, GS.WINSIZE_H + insets.top + insets.bottom);
		window.setLocationRelativeTo(null);
		window.createBufferStrategy(2);
		buffer = window.getBufferStrategy();

		// Setup input
		window.addMouseListener(input);
		window.addMouseMotionListener(input);
		window.addKeyListener(input);
		window.addMouseWheelListener(input);

		// Start timer task
		timer.schedule(mainloop, 17, 17);
	}

	// For restart
	void dispose() {
		mainloop.cancel();
		timer.cancel();
		timer.purge();
		buffer.dispose();
		window.dispose();
		System.gc();
	}

	// All game logic running in here
	class MainLoop extends TimerTask {

		public void run() {
			Graphics2D graphic;
			try {
				graphic = (Graphics2D) buffer.getDrawGraphics();
			} catch (Exception e) {
				buffer = window.getBufferStrategy();
				graphic = (Graphics2D) buffer.getDrawGraphics();
			}
			// main update
			input.update(window);
			manager.updateMain();

			if (buffer.contentsLost() == false) {
				graphic.translate(insets.left, insets.top);
				graphic.clearRect(0, 0, GS.WINSIZE_W, GS.WINSIZE_H);

				// main draw
				manager.drawMain(graphic, window);

				buffer.show();
				graphic.dispose();
			}
		}
	}
}

// The true main class
public class ActGameMain {

	public static void main(String[] args) {
		// Start a new game
		GameMain game = new GameMain();
	}
}
