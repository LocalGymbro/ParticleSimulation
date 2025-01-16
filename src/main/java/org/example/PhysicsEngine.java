package org.example;

import com.badlogic.gdx.math.Vector2;
import java.util.List;

public class PhysicsEngine {
    private List<Particle> particles;

    public PhysicsEngine(List<Particle> particles) {
        this.particles = particles;
    }

    // Methode zur Aktualisierung der Physik
    public void update(float deltaTime) {
        for (Particle particle : particles) {
            particle.update(deltaTime); // Wieso geht das nicht?
        }
        handleCollisions();
    }

    // Beispielmethode zur Anwendung von Kräften
  //  private void applyForces() {
  //      Vector2 gravity = new Vector2(0, -9.81f); // Schwerkraft
    //    for (Particle particle : particles) {
    //        particle.applyForce(gravity.scl(particle.getMasse()));
    //    }
   // }

    private void handleCollisions() {
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                if (isColliding(p1, p2)) {
                    resolveCollision(p1, p2);
                }
            }
        }
    }

    private boolean isColliding(Particle p1, Particle p2) {
        float distance = p1.getPosition().dst(p2.getPosition());
        return distance < (5 + 5); // Beispielradius
    }

    private void resolveCollision(Particle p1, Particle p2) {
        Vector2 normal = p2.getPosition().cpy().sub(p1.getPosition()).nor();
        float relativeVelocity = p2.getProzedualeBewegung.dot(normal) - p1.getProzedualeBewegung.dot(normal);

        if (relativeVelocity < 0) {
            float restitution = 0.8f; // Rückprallkoeffizienten
            float impulse = (-(1 + restitution) * relativeVelocity) / (1 / p1.getMasse() + 1 / p2.getMasse());
            Vector2 impulseVector = normal.scl(impulse);
            p1.applyForce(impulseVector.scl(-1));
            p2.applyForce(impulseVector);
        }
    }
}