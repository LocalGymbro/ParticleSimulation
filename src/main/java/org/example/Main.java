package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.example.data.SpatialGrid;

import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main (String[] arg) {
		// Konfiguration erstellen
		Lwjgl3ApplicationConfiguration konfiguration = new Lwjgl3ApplicationConfiguration();

		Vector2 fensterGroesse = new Vector2(1900, 1000);

		// Fenstertitel zu 'Sonnensystem' aendern
		konfiguration.setTitle("Particle Simulation");
		// Fenstergroesse wird gesetzt
		konfiguration.setWindowedMode((int)fensterGroesse.x, (int)fensterGroesse.y);
		// VSync aktivieren
		konfiguration.useVsync(true);
		// Maximale FPS setzen
		konfiguration.setForegroundFPS(60);
		konfiguration.setBackBufferConfig(8,8,8,8,16,0,8);

		Vector2 mittelpunkt = new Vector2(fensterGroesse);
		mittelpunkt.scl(0.5f);

		ArrayList<PhysicsObject> objs = new ArrayList<>();
		Random rnd = new Random();
		for (int i = 0; i < 1000; i++) {
			Vector2 pos = new Vector2(rnd.nextFloat(fensterGroesse.x), rnd.nextFloat(fensterGroesse.y));
			Vector3 col = new Vector3(
					rnd.nextFloat(1.0f),
					rnd.nextFloat(1.0f),
					rnd.nextFloat(1.0f)
			);
			col = col.nor();
			Vector2 vel = new Vector2(
					rnd.nextFloat(80.0f),
					rnd.nextFloat(80.0f)
			);
 			objs.add(new Particle(pos, 7, col, vel, 0.0f));
		}

		SchwerkraftFeld feld = new SchwerkraftFeld(50000000.0f);
		objs.add(feld);

		SpatialGrid grid = new SpatialGrid(100, fensterGroesse);

		for (int i = 0; i < objs.size(); i++) {
			PhysicsObject o = objs.get(i);
			if (o instanceof Particle p) {
				grid.addParticleToGrid(p);
			}
		}

		// Sonnensystem mit Planeten erstellen
		ParticleSystem particleSystem = new ParticleSystem(
				fensterGroesse,
				objs.toArray(new PhysicsObject[0]),
				grid
		);

		// Applikation/Fenster erstellen
		new Lwjgl3Application(particleSystem, konfiguration);
	}
}