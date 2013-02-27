package com.raindropaqua.sakurawallpaper.casts;

import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.raindropaqua.sakurawallpaper.engine.GLUtil;

public class Sakura implements Base {

	private float[] vertices ={
			// x,y,z
			0.0f, 0.1f, 0.0f,
			0.0f, 0.0f, 0.0f,
			0.1f, 0.1f, 0.0f,
			0.1f, 0.0f, 0.0f,
	};
    private float[] colors = new float[] {     
			// r.g.b.a
			1.0f, 0.9f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 0.3f,
			1.0f, 0.5f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 0.3f,
    };
    private final Random r = new Random();
    
	private float posX = -1.0f;
	private float posY =  1.0f;
	private float posZ =  0.0f;
	
	private float speedX = 0;
	private float speedY = 0;
		
	private float angle = 0.0f;
	private float anglePlus = 0.0f;
	
	private float rotX = 0; 
	private float rotY = 0; 
	private float rotZ = 0; 

	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;

	public Sakura() {
		posX = r.nextFloat() * -1 - 1.5f;
		posY = r.nextFloat()      + 1.5f;
		
		speedX = r.nextFloat() / 200 + 0.005f;
		speedY = r.nextFloat() / 200 + 0.005f;
		
		rotX = r.nextFloat();
		rotY = r.nextFloat();
		rotZ = r.nextFloat();
		
		anglePlus = r.nextFloat();
		
		float scale = r.nextFloat(); 
		scaleX = scale;
		scaleY = scale;
		scaleZ = scale;
	}	

	@Override
	public boolean onStandby(GL10 gl) {
		// polygon
		FloatBuffer fb = GLUtil.makeFloatBuffer(vertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb);
		// color
		FloatBuffer colorBuff = GLUtil.makeFloatBuffer(colors);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuff);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		return true;
	}

	@Override
	public boolean onUpdate() {
		angle += anglePlus;
		posX += speedX;
		posY -= speedY;

		if (posX >= 1.1f) {
			posX = -1.1f;
		}
		if (posY <= -1.1f) {
			posY = 1.1f;
		}
		return true;
	}

	@Override
	public void onDraw(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(posX, posY, posZ);
		gl.glRotatef(angle, rotX, rotY, rotZ);
		gl.glScalef(scaleX, scaleY, scaleZ);

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
