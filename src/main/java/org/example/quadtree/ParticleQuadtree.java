package org.example.quadtree;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.example.Particle;
import org.example.ParticleSystem;

import java.util.ArrayList;

public class ParticleQuadtree implements Quadtree {
    private ArrayList<Particle> particleList = new ArrayList<>();

    private SubdividedQuadtree parent;

    private Rectangle bounds;

    private static Capacity capacity = new Capacity(1, 3);

    public ParticleQuadtree(SubdividedQuadtree parent, Rectangle bounds) {
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

    public void subdivide(){
        var quad = new SubdividedQuadtree(this.parent, this.getRect());

        var x = this.getRect().getX();
        var y = this.getRect().getY();
        var w = this.getRect().width;
        var h = this.getRect().height;

        var neQuad = new ParticleQuadtree(quad, new Rectangle(x, y, w/2, h/2));
        var nwQuad = new ParticleQuadtree(quad, new Rectangle(x+w/2, y, w/2, h/2));
        var seQuad = new ParticleQuadtree(quad, new Rectangle(x, y+h/2, w/2, h/2));
        var swQuad = new ParticleQuadtree(quad, new Rectangle(x+w/2, y+h/2, w/2, h/2));

        neQuad.setParticles(this.getParticlesInQuad(neQuad.getRect()));
        nwQuad.setParticles(this.getParticlesInQuad(nwQuad.getRect()));
        seQuad.setParticles(this.getParticlesInQuad(seQuad.getRect()));
        swQuad.setParticles(this.getParticlesInQuad(swQuad.getRect()));

        quad.setNortheast(neQuad);
        quad.setNorthwest(nwQuad);
        quad.setNortheast(seQuad);
        quad.setSouthwest(swQuad);

        this.parent.swapTree(this, quad);
    }

    public ArrayList<Particle> getParticles() {
        return this.particleList;
    }

    public void getParticles(ArrayList<Particle> particles) {
        particles.addAll(this.particleList);
    }

    @Override
    public int getParticleCount() {
        return particleList.size();
    }

    @Override
    public QuadtreeBase getBase() {
        return parent.getBase();
    }

    @Override
    public void update() {
        if (this.getParticleCount() > capacity.maximum) {
            this.subdivide();
        }
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particleList = particles;
    }

    private ArrayList<Particle> getParticlesInQuad(Rectangle rect) {
        Vector2 topL = new Vector2();
        rect.getPosition(topL);

        Vector2 bottomR = new Vector2(topL.x+rect.width, topL.y+rect.height);

        ArrayList<Particle> temp = new ArrayList<>();

        for (var p : this.getParticles()) {
            var pos = p.getPosition();
            if (pos.x < topL.x || pos.x > bottomR.x || pos.y < topL.y || pos.y > bottomR.y) continue;
            temp.add(p);
        }

        return temp;
    }

    public void insert(Particle particle) {
        this.particleList.add(particle);
    }
}
