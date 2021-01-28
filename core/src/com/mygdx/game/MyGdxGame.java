package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	static class Rock{
		Texture texture;
		float x, y;
		float vx, vy;
		float r;

		public Rock(){
			texture = new Texture("asteroid64.png");
			x = 200;
			y = 600;
			vx = 500;
			vy = 0;
			r = 26;
		}

		public void render(SpriteBatch batch){
			batch.draw(texture, x - 32, y - 32);
		}

		public void update(float dt){
			vy -= 500 * dt;
			x += vx * dt;
			y += vy * dt;
			vx *= 0.995f;//сила трения воздуха
			vy *= 0.995f;//сила трения воздуха
			if (y - r < GROUND_HEIGHT && vy < 0){
				vx *= 0.94f;//сила трения земли
				y = GROUND_HEIGHT + r;//удоряется о землю
				vy *= -0.6;// скорость отбрасывания от земли
			}
			if (x + r > 1280){
				x = 1280 - r;
				vx *= -1;
			}
			if (x - r < 0){
				x = 0 + r;
				vx *= -1;
			}
		}
	}

	Texture textureGround;
	SpriteBatch batch;
	Rock rock;
	private static final int GROUND_HEIGHT = 100;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		rock = new Rock();
		textureGround = new Texture("ground.png");
	}



	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(textureGround, 0, 0);
		rock.render(batch);
		batch.end();
	}

	public void update(float dt){
		rock.update(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
