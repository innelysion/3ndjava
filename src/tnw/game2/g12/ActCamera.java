package tnw.game2.g12;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ActCamera {

	private ActManager m;

	private double focusX, focusY, focusTargetX, focusTargetY;
	private double playerX, playerY;
	private double mapX, mapY;

	private int smoothValue;
	boolean isSmoothMoving;
	boolean freeMode;

	ActCamera() {
		focusX = 0;
		focusY = 0;
		playerX = 0;
		playerY = 0;
		mapX = 0;
		mapY = 0;
		focusTargetX = 0;
		focusTargetY = 0;
		smoothValue = 16;
		isSmoothMoving = true;
		freeMode = false;
	}

	public void update(ActManager mgr) {
		// Get manager data
		m = mgr;

		// Switch camera mode
		if (Input.keyRe.Z) {
			freeMode = !freeMode;
		}

		// Free camera of focus on player
		if (freeMode) {
			smoothValue = 8;
			focusTargetX -= Input.keyPr.LEFT ? 16 : 0;
			focusTargetX += Input.keyPr.RIGHT ? 16 : 0;
			focusTargetY -= Input.keyPr.UP ? 16 : 0;
			focusTargetY += Input.keyPr.DOWN ? 16 : 0;
		} else {
			smoothValue = 32;
			focusTargetX = m.player.X - GS.WINSIZE_W / 2 + m.player.sizeW / 2;
			focusTargetY = m.player.Y - GS.WINSIZE_H / 2 + m.player.sizeH / 2;
		}

		// Set limit to stay in map area
		focusTargetX = focusTargetX <= 0 ? 0 : focusTargetX;
		focusTargetX = focusTargetX >= m.map.mapSizeW * 16 - GS.WINSIZE_W ? m.map.mapSizeW * 16 - GS.WINSIZE_W
				: focusTargetX;
		focusTargetY = focusTargetY <= 0 ? 0 : focusTargetY;
		focusTargetY = focusTargetY >= m.map.mapSizeH * 16 - GS.WINSIZE_H ? m.map.mapSizeH * 16 - GS.WINSIZE_H
				: focusTargetY;

		// Smooth the camera moving
		if (isSmoothMoving && (Math.abs(focusTargetX - focusX) > 0.5 || Math.abs(focusTargetY - focusY) > 0.5)) {
			focusX += (focusTargetX - focusX) / smoothValue;
			focusY += (focusTargetY - focusY) / smoothValue;
		} else {
			focusX = focusTargetX;
			focusY = focusTargetY;
		}

		// Set final position for draw
		playerX = m.player.X - focusX;
		playerY = m.player.Y - focusY;
		mapX = -focusX;
		mapY = -focusY;
	}

	// Set camera to free mode and focus a position
	public void focus(double x, double y) {
		freeMode = true;
		focusTargetX = x;
		focusTargetY = y;
	}

	// Draw game objects
	public void draw(Graphics2D g, JFrame w) {
		m.map.draw(g, w, mapX, mapY);
		m.player.draw(g, w, playerX, playerY);

		// Debug
		g.setColor(Color.BLUE);
		g.drawRect(20, 20, 250, 240);
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)));
		g.fillRect(20, 20, 250, 240);
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));
		g.setColor(Color.WHITE);
		g.drawString("Camera : " + (int) focusX + "," + (int) focusY, 30, 40);
		g.drawString("CameraTarget : " + (int) focusTargetX + "," + (int) focusTargetY, 30, 60);
		g.drawString("PlayerMapPos : " + m.player.X + "," + m.player.Y, 30, 80);
		g.drawString("Press 'Z' to switch camera", 30, 120);
		g.drawString("CameraMode : " + (freeMode ? "Free" : "Focus Player"), 30, 140);
		g.drawString("Hit Left : " + m.player.isHitLeft(), 30, 180);
		g.drawString("Hit Up : " + m.player.isHitUp(), 30, 200);
		g.drawString("Hit Right : " + m.player.isHitRight(), 30, 220);
		g.drawString("Hit Down : " + m.player.isHitDown(), 30, 240);
	}
}
