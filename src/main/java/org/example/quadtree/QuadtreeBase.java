package org.example.quadtree;

import com.badlogic.gdx.math.Rectangle;
import org.example.Particle;

import java.security.KeyPair;
import java.util.*;

public class QuadtreeBase implements Quadtree {
    private Quadtree quad;

    public QuadtreeBase(Quadtree quad) {
        this.quad = quad;
    }

    @Override
    public Quadtree getParent() {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }

    @Override
    public void getParticles(ArrayList<Particle> particles) {

    }

    @Override
    public int getParticleCount() {
        return 0;
    }

    @Override
    public QuadtreeBase getBase() {
        return this;
    }

    @Override
    public void update() {
        quad.update();
    }
}
