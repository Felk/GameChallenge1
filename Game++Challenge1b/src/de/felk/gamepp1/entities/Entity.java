package de.felk.gamepp1.entities;

public abstract class Entity {

	private Vector position;
	private Sprite sprite;
	private String name;
	
	public Entity(String name, Vector position, Sprite sprite) {
		this.name = name;
		this.position = position;
		this.sprite = sprite;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public void setPosition(Vector position) {
		this.position = position.clone();
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void render() {
		sprite.render(position);
	}
	
	public void update() {
	}
	
	public abstract Entity clone();

	public String getName() {
		return name;
	}
	
}
