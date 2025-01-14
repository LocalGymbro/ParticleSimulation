package org.example.quadtree;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.example.Particle;

import java.util.ArrayList;

public class SubdividedQuadtree implements Quadtree {
    private Quadtree northeast = null;
    private Quadtree northwest = null;
    private Quadtree southeast = null;
    private Quadtree southwest = null;

    private SubdividedQuadtree parent;

    private Rectangle bounds;

    private static Capacity capacity = new Capacity(4, 12);

    public SubdividedQuadtree(SubdividedQuadtree parent, Rectangle bounds) {
        this.parent = parent;
        this.bounds = bounds;
    }

    @Override
    public Quadtree getParent() {
        return parent;
    }

    @Override
    public Rectangle getRect() {
        return bounds;
    }

    @Override
    public void getParticles(ArrayList<Particle> particles) {
        northwest.getParticles(particles);
        northeast.getParticles(particles);
        southwest.getParticles(particles);
        southeast.getParticles(particles);
    }

    @Override
    public int getParticleCount() {
        return northwest.getParticleCount() + northeast.getParticleCount() + southwest.getParticleCount() + southeast.getParticleCount();
    }

    @Override
    public QuadtreeBase getBase() {
        return parent.getBase();
    }

    @Override
    public void update() {
        var pCount = this.getParticleCount();
        if (pCount < capacity.minimum) {
            this.unSubdivide();
            return;
        }
        if (pCount > capacity.maximum) {
            this.northeast.update();
            this.northwest.update();
            this.southeast.update();
            this.southwest.update();
        }
    }
    // Hello

    public void unSubdivide() {
        var x = this.northwest.getRect().getX();
        var y = this.northwest.getRect().getY();
        var width = this.northwest.getRect().getHeight();
        var height = this.northwest.getRect().getWidth();

        var quad = new ParticleQuadtree(parent, new Rectangle(x, y, width*2, height*2));

        ArrayList<Particle> particles = new ArrayList<>();
        this.getParticles(particles);

        quad.setParticles(particles);

        parent.swapTree(this, quad);
    }

    public void swapTree(Quadtree og, Quadtree n) {
        if (northeast.equals(og)) setNortheast(n);
        else if (northwest.equals(og)) setNorthwest(n);
        else if (southeast.equals(og)) setSoutheast(n);
        else if (southwest.equals(og)) setSouthwest(n);
    }

    public Quadtree getNortheast() {
        return northeast;
    }

    public void setNortheast(Quadtree northeast) {
        this.northeast = northeast;
    }

    public Quadtree getNorthwest() {
        return northwest;
    }

    public void setNorthwest(Quadtree northwest) {
        this.northwest = northwest;
    }

    public Quadtree getSoutheast() {
        return southeast;
    }

    public void setSoutheast(Quadtree southeast) {
        this.southeast = southeast;
    }

    public Quadtree getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Quadtree southwest) {
        this.southwest = southwest;
    }
}

