package tnw.game2.g12;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class ActCamera {

	private ActManager m;
	private ActCharacters focusingOnChara;

	private double focusX, focusY, focusTargetX, focusTargetY;
	private double mapX, mapY;

	private int smoothValue;
	boolean isSmoothMoving;

	private float pathflash;
	private boolean flash;

	ActCamera() {
		focusX = 0;
		focusY = 0;
		mapX = 0;
		mapY = 0;
		focusTargetX = 0;
		focusTargetY = 0;
		smoothValue = 16;
		pathflash = 0f;
		flash = false;
		isSmoothMoving = true;
	}

	public void update(ActManager mgr) {
		// Get manager data
		m = mgr;

		// Flash path
		if (flash) {
			pathflash -= 0.01f;
			if (pathflash <= 0f) {
				pathflash = 0f;
				flash = false;
			}
		} else {
			pathflash += 0.01f;
			if (pathflash >= 0.6f) {
				pathflash = 0.6f;
				flash = true;
			}
		}

		// Free camera of focus on target
		if (focusingOnChara == null) {
			smoothValue = 8;
			focusTargetX -= Input.keyPr.LEFT ? 16 : 0;
			focusTargetX += Input.keyPr.RIGHT ? 16 : 0;
			focusTargetY -= Input.keyPr.UP ? 16 : 0;
			focusTargetY += Input.keyPr.DOWN ? 16 : 0;
		} else {
			smoothValue = 32;
			focusTargetX = focusingOnChara.X - GS.WINSIZE_W / 2 + m.player.sizeW / 2;
			focusTargetY = focusingOnChara.Y - GS.WINSIZE_H / 2 + m.player.sizeH / 2;
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
		mapX = -focusX;
		mapY = -focusY;
	}

	// Set camera to free mode and focus a position
	public void freePosFocus(double x, double y) {
		focusingOnChara = null;
		focusTargetX = x;
		focusTargetY = y;
	}

	// *START*Get & Set

	public boolean isFree() {
		return focusingOnChara == null;
	}

	public void setFocusChara(ActCharacters chara) {
		focusingOnChara = chara;
	}

	public ActActor getFocusChara() {
		return (ActActor) focusingOnChara;
	}
	// *END*

	// Draw game objects
	public void draw(Graphics2D g, JFrame w) {
		m.map.draw(g, w, mapX, mapY);
		m.player.draw(g, w, focusX, focusY);
		m.npc1.draw(g, w, focusX, focusY);
		m.npc2.draw(g, w, focusX, focusY);

		// Debug
		g.setColor(Color.BLUE);
		g.drawRect(20, 20, 300, 160);
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)));
		g.fillRect(20, 20, 300, 160);
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pathflash)));
		g.setColor(Color.WHITE);
		if (m.npc2.path != null) {
			for (Node n : m.npc2.path) {
				g.fillRect((int) (n.y - focusX), (int) (n.x - focusY), 16, 16);
			}
		}
		g.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)));
		g.setColor(Color.WHITE);
		g.drawString("G12張瀚夫", 30, 40);
		g.drawString("Camera : " + (int) focusX + "," + (int) focusY, 30, 60);
		g.drawString("【Zｷｰ】カメラの目標点を切り替え", 30, 120);
		g.drawString("カメラの目標点 : " + ((ActActor) focusingOnChara).getName(), 30, 140);
		g.drawString(((ActActor) focusingOnChara).getName() + "の座標 : " + ((ActActor) focusingOnChara).X + "," + ((ActActor) focusingOnChara).Y, 30, 160);
		// g.drawString("Press 'X' to switch control mode", 30, 160);
		// g.drawString("PlayerMode : " + (GS.ACT_GAMEMODE ? "ACT" : "RPG"), 30,
		// 180);

		// g.drawString("Hit Left : " + m.player.isHitLeft(), 30, 220);
		// g.drawString("Hit Up : " + m.player.isHitUp(), 30, 240);
		// g.drawString("Hit Right : " + m.player.isHitRight(), 30, 260);
		// g.drawString("Hit Down : " + m.player.isHitDown(), 30, 280);
		// g.drawString("Force : " + (int) m.player.getForce(), 30, 300);

	}
}
