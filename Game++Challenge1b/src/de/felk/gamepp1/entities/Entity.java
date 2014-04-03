package de.felk.gamepp1.entities;

public abstract class Entity {

	private Vector position;
	private Sprite sprite;
	
	public Entity(Vector position, Sprite sprite) {
		this.position = position;
		this.sprite = sprite;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public void render() {
		sprite.render(position);
	}
	
	public void update() {
	}
	
}
