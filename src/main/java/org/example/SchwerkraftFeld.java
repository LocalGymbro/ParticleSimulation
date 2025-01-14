package org.example;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class SchwerkraftFeld implements PhysicsObject {
    private float mass;
    private float kraft;

    private Vector2 pos;

    public SchwerkraftFeld(float kraft) {
        this.mass = 0;
        this.kraft = kraft;
        this.pos = new Vector2(0f,0f);
    }

    @Override
    public void zeichne(ShapeRenderer zeichner) {
        zeichner.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
        zeichner.circle(this.pos.x, this.pos.y, 12);
    }

    @Override
    public void setPosition(Vector2 position) {
        this.pos = position;
    }

    @Override
    public void addPosition(Vector2 position) {

    }

    @Override
    public void bewege(Vector2 bewegung) {

    }

    @Override
    public void wendeProzedualeBewegungAn(Vector2 bewegung) {

    }

    @Override
    public Vector2 getProzedualeBewegung(float deltaTime) {
        return null;
    }

    @Override
    public float getMasse() {
        return mass;
    }

    public void attract() {
        this.mass = kraft;
    }

    public void repel() {
        this.mass = -kraft;
    }

    public void clear() {
        this.mass = 0;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    @Override
    public String getGeschwindigkeitsLabel() {
        return String.format("x: %.2f | y: %.2f", this.pos.x, this.pos.y);
    }
}
