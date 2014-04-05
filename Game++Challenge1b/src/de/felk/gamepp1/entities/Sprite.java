package de.felk.gamepp1.entities;

import de.felk.gamepp1.RenderHelper;

public class Sprite {

	private boolean flipped = false;
	private Color color;

	public Sprite(Color color) {
		this.color = color.clone();
	}

	public void render(Vector position) {

		RenderHelper.setColor(color);
		//RenderHelper.renderQuad(position.getX() - Game.DEBUG_SIZE, position.getY() - Game.DEBUG_SIZE, 2 * Game.DEBUG_SIZE, 2 * Game.DEBUG_SIZE);
		//RenderHelper.renderCircle(position.getX(), position.getY(), Game.DEBUG_SIZE, false);
	}

	public void render2(Vector position, float mass) {
		render(position);
		RenderHelper.setColor(color);
		//RenderHelper.renderCircle(position.getX(), position.getY(), Game.DEBUG_SIZE * mass, false);
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	@Override
	public Sprite clone() {
		return new Sprite(color.clone());
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color.clone();
	}

}
