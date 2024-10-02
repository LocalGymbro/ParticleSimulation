package org.example;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

public class MainOld {
	public static void main (String[] arg) {
		// Konfiguration erstellen
		Lwjgl3ApplicationConfiguration konfiguration = new Lwjgl3ApplicationConfiguration();

		Vector2 fensterGroesse = new Vector2(1900, 960);

		// Fenstertitel zu 'Sonnensystem' aendern
		konfiguration.setTitle("Meilich-Sim");
		// Fenstergroesse wird gesetzt
		konfiguration.setWindowedMode((int)fensterGroesse.x, (int)fensterGroesse.y);
		// VSync aktivieren
		konfiguration.useVsync(true);
		// Maximale FPS setzen
		konfiguration.setForegroundFPS(60);

		Vector2 mittelpunkt = new Vector2(fensterGroesse);
		mittelpunkt.scl(0.5f);

		ArrayList<PhysicsObject> objs = new ArrayList<>();
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
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
 			objs.add(new Particle(pos, 7, col, vel, 900000.0f));
		}

		SchwerkraftFeld feld = new SchwerkraftFeld(50000000.0f);
		objs.add(feld);

		for (int i = 0; i < objs.size(); i++) {
			PhysicsObject o = objs.get(i);
			if (o instanceof Particle) {
				PhysicsObject[] others = objs.stream().filter(e -> e != o).toList().toArray(new PhysicsObject[0]);
				((Particle) o).setAndereObjekte(others);
			}
		}

		// Sonnensystem mit Planeten erstellen
		//ParticleSystem particleSystem = new ParticleSystem(
		//		fensterGroesse,
		//		objs.toArray(new PhysicsObject[0])
		//		//feld
		//);

		// Applikation/Fenster erstellen
		//new Lwjgl3Application(particleSystem, konfiguration);
	}
}