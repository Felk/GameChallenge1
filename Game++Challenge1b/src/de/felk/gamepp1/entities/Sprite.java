package de.felk.gamepp1.entities;

import de.felk.gamepp1.RenderEngine;

public class Sprite {

	public Sprite() {
		
	}
	
	public void render(Space space) {
		
		RenderEngine.disableTexture();
		RenderEngine.disableLighting();
		RenderEngine.renderQuad(space.getPosition().getX(), space.getPosition().getY(), 10, 10);
		
	}
	
}
