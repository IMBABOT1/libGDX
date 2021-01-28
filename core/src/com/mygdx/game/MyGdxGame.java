package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	static class Rock {
		Texture texture;
		float x, y;
		float vx, vy;
		float r;
		float scale;
		float halfSize;
		float angle;

		public Rock() {
			texture = new Texture("asteroid64.png");
			if (texture.getWidth() != texture.getHeight()) throw new RuntimeException("square Error");
			halfSize = texture.getWidth() / 2;
			x = 200;
			y = 600;
			vx = 500;
			vy = 0;
			scale = 1.0f;
			angle = 0.0f;
			r = 24 * scale;
		}

		public void render(SpriteBatch batch) {
			batch.draw(texture, x - halfSize, y - halfSize, halfSize, halfSize, 64, 64, scale, scale,angle, 0, 0, 64, 64, false, false);
		}

		public void update(float dt) {
			vy -= 500 * dt;
			x += vx * dt;
			y += vy * dt;
			vx *= 0.995f;//сила трения воздуха
			vy *= 0.995f;//сила трения воздуха
			if (y - r < GROUND_HEIGHT && vy < 0) {
				vx *= 0.94f;//сила трения земли
				y = GROUND_HEIGHT + r;//удоряется о землю
				vy *= -0.6;// скорость отбрасывания от земли
			}
			if (x + r > 1280) {
				x = 1280 - r;
				vx *= -1;
			}else {
				angle -= vx * dt;
			}
			if (x - r < 0) {
				x = 0 + r;
				vx *= -1;
			}
			if (Gdx.input.justTouched()) {
				vx = Gdx.input.getX() - x;
				vy = (720 - Gdx.input.getY()) - y;
			}
		}
	}

	Texture textureGround;
	SpriteBatch batch;
	Rock rock;
	private static final int GROUND_HEIGHT = 100;


	@Override
	public void create() {
		batch = new SpriteBatch();
		rock = new Rock();
		textureGround = new Texture("ground.png");
	}


	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0.8f, 0.8f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(textureGround, 0, 0);
		rock.render(batch);
		batch.end();
	}

	public void update(float dt) {
		rock.update(dt);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}

