package org.example.quadtree;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.example.Particle;

import java.util.ArrayList;

public interface Quadtree {
    Quadtree getParent();
    Rectangle getRect();
    void getParticles(ArrayList<Particle> particles);
    int getParticleCount();
    QuadtreeBase getBase();
    void update();
}

