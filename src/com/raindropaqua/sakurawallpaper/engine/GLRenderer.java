package com.raindropaqua.sakurawallpaper.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

import com.raindropaqua.sakurawallpaper.utils.Stage;

public class GLRenderer implements Renderer {

	private Stage stage = new Stage();
    private int windowWidth = -1;
    private int windowHeight = -1;
    
	@Override
	public void onDrawFrame(GL10 gl) {
        gl.glClearColor(0.2f,0.2f,0.2f,1.0f);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        stage.onUpdate();
		stage.onDraw(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
	    stage.onStandby(gl);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}
}
