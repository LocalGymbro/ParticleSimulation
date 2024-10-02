package org.example;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.example.data.SpatialGrid;

public interface PhysicsObject {
	void zeichne(ShapeRenderer zeichner);
	void setPosition(Vector2 position);
	void addPosition(Vector2 position);
	void bewege(Vector2 bewegung);
	void wendeProzedualeBewegungAn(Vector2 bewegung);
	Vector2 getProzedualeBewegung(float deltaTime, SpatialGrid grid);

	float getMasse();
	Vector2 getPosition();
	String getGeschwindigkeitsLabel();
}
