package de.felk.gamepp1.entities;

public abstract class Entity {

	private Space space;
	private Sprite sprite;
	
	public Entity(Space space, Sprite sprite) {
		this.space = space;
		this.sprite = sprite;
	}
	
	public void render() {
		sprite.render(space);
	}
	
}
