package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class ParticleSimulation extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private List<Particle> particles;
    private PhysicsEngine physicsEngine;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        particles = new ArrayList<>();

        physicsEngine = new PhysicsEngine(particles);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        physicsEngine.update(deltaTime);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Particle particle : particles) {
            particle.zeichne(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
