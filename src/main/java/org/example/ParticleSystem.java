package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;

import java.util.Arrays;
import java.util.Optional;

public class ParticleSystem extends ApplicationAdapter {
	private ShapeRenderer formZeichner;
	private BitmapFont schriftart;
	//private SpriteBatch batch;
	private PhysicsObject[] objekte;
	private float deltaTime;
	private long last;
	private Vector2 fensterDimensionen;
	private SchwerkraftFeld feld;

	private VfxManager vfxManager;
	private BloomEffect bloomEffect;

	public ParticleSystem(Vector2 fenterDimensionen, PhysicsObject[] planeten) {
		this.objekte = planeten;
		this.fensterDimensionen = fenterDimensionen;
		this.feld = null;
	}

	@Override
	public void create(){
		this.formZeichner = new ShapeRenderer();
		this.deltaTime = 0.016f;
		this.last = System.currentTimeMillis();
		this.schriftart = new BitmapFont();
		//this.batch = new SpriteBatch();
		Optional<PhysicsObject> o = Arrays.stream(this.objekte).filter(e -> e instanceof SchwerkraftFeld).findAny();
		if (o.isEmpty()) throw new RuntimeException();
		this.feld = (SchwerkraftFeld) o.get();

		this.vfxManager = new VfxManager(Pixmap.Format.RGBA8888);

		this.bloomEffect = new BloomEffect();
		this.bloomEffect.setBloomIntensity(1.1f);
		this.bloomEffect.setBlurAmount(5.0f);
		this.bloomEffect.setThreshold(0.0f);
		this.vfxManager.addEffect(bloomEffect);
	}

	// Wird einmal pro frame aufgerufen
	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		//long begin = System.currentTimeMillis();
		formZeichner.begin(ShapeRenderer.ShapeType.Filled);

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			this.feld.setPosition(new Vector2(Gdx.input.getX(), -Gdx.input.getY()+this.fensterDimensionen.y));
			this.feld.attract();
			System.out.println("Attracting: " + this.feld.getMasse() + " [" + this.feld.getGeschwindigkeitsLabel() + "]");
		} else {
			this.feld.clear();
			System.out.println("Field Cleared: " + this.feld.getMasse());
		}

		for (int i = 0; i < this.objekte.length; i++) {
			if (objekte[i] instanceof Particle) {
				objekte[i].wendeProzedualeBewegungAn(objekte[i].getProzedualeBewegung(deltaTime));
				Vector2 oldPos = new Vector2(objekte[i].getPosition());
				Vector2 newPos = new Vector2(
						(oldPos.x + this.fensterDimensionen.x) % this.fensterDimensionen.x,
						(oldPos.y + this.fensterDimensionen.y) % this.fensterDimensionen.y
				);
			/*newPos = new Vector2(
					this.fensterDimensionen.x-newPos.x % this.fensterDimensionen.x,
					this.fensterDimensionen.y-newPos.y % this.fensterDimensionen.y
			);*/
				objekte[i].setPosition(newPos);
			}
		}

		// Zeichne Objekte
		for (int i = 0; i < this.objekte.length; i++) {
			PhysicsObject objekt = objekte[i];
			objekt.zeichne(this.formZeichner);
		}

		formZeichner.end();

		// Label zeichnen
		/*this.batch.begin();
		for (int i = 0; i < this.objekte.length; i++) {
			Vector2 pos = this.objekte[i].getPosition();
			this.schriftart.draw(this.batch,
				String.format("x: %.2f | y: %.2f\n%s", pos.x, pos.y,this.objekte[i].getGeschwindigkeitsLabel()),
				pos.x, pos.y);
		}
		this.batch.end();*/

		long end = System.currentTimeMillis();
		this.deltaTime = ((float)(end-last))/1000.0f;
		this.last = end;
	}
}