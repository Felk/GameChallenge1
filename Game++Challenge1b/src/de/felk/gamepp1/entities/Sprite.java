package de.felk.gamepp1.entities;

import de.felk.gamepp1.Game;
import de.felk.gamepp1.RenderEngine;
import de.felk.gamepp1.RenderHelper;

public class Sprite {
	
	private boolean flipped = false;
	
	public Sprite() {
	}
	
	public void render(Vector position) {
		
		RenderEngine.disableTexture();
		RenderEngine.disableLighting();
		//RenderEngine.renderQuad(position.getX(), position.getY(), 50, 50);
		RenderHelper.drawFilledCircle(position.getX(), position.getY(), Game.AAABBC);
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}
	
}
