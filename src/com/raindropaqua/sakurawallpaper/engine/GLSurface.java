package com.raindropaqua.sakurawallpaper.engine;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import android.view.SurfaceHolder;

public class GLSurface extends Thread {
	private boolean destroy = false;
	private boolean pause = false;

	private SurfaceHolder holder;
	private EGL10 egl;
	private EGLContext eglContext = null;
	private EGLDisplay eglDisplay = null;
	private EGLSurface eglSurface = null;
	private EGLConfig eglConfig = null;
	private GL10 gl10 = null;
	
	private int windowWidth = -1;
	private int windowHeight = -1;

	public GLSurface(SurfaceHolder holder) {
		this.holder = holder;
	}

	private boolean initialize() {
		egl = (EGL10) EGLContext.getEGL();

		eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

		{
			int[] version = { -1, -1 };
			if (!egl.eglInitialize(eglDisplay, version)) {
				Log.e("EGL", "init error");
				return false;
			}
		}
		{
			EGLConfig[] configs = new EGLConfig[1];
			int[] num = new int[1];
			int[] spec = { EGL10.EGL_NONE };

			if (!egl.eglChooseConfig(eglDisplay, spec, configs, 1, num)) {
				Log.e("EGL", "config error");
			}
			eglConfig = configs[0];
		}
		{
			eglContext = egl.eglCreateContext(eglDisplay, eglConfig,
					EGL10.EGL_NO_CONTEXT, null);
			if (eglContext == EGL10.EGL_NO_CONTEXT) {
				Log.e("EGL", "context error");
				return false;
			}
		}
		{
			egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE,
					EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
			eglSurface = egl.eglCreateWindowSurface(eglDisplay, eglConfig,
					holder, null);
			if (eglSurface == EGL10.EGL_NO_SURFACE) {
				Log.e("EGL", "surface error");
				return false;
			}
		}
		{
			gl10 = (GL10)eglContext.getGL();
		}
		{
			if (!egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
				Log.e("EGL", "context relation error");
				return false;
			}
		}
		return true;
	}

	private void dispose() {
		if (eglSurface != null) {
			egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE,
					EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
			egl.eglDestroySurface(eglDisplay, eglSurface);
			eglSurface = null;
		}
		if (eglContext != null) {
			egl.eglDestroyContext(eglDisplay, eglContext);
			eglContext = null;
		}
		if (eglDisplay != null) {
			egl.eglTerminate(eglDisplay);
			eglDisplay = null;
		}
	}

	@Override
	public void run() {
		GLRenderer render = new GLRenderer();
		initialize();
		render.onSurfaceChanged(gl10, windowWidth, windowHeight);
		while (!destroy) {
			if (!pause) {
				render.setWindowWidth(windowWidth);
				render.setWindowHeight(windowHeight);
				gl10.glViewport(0, 0, windowWidth, windowHeight);
				render.onDrawFrame(gl10);
				egl.eglSwapBuffers(eglDisplay, eglSurface);
			} else {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					;
				}
			}
		}
		dispose();
	}

	public void onDestroy() {
		synchronized (this) {
			destroy = true;
		}
		try {
			join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void onResume() {
		pause = false;
	}
	
	public void onPause() {
		pause = true;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windwoHeight) {
		this.windowHeight = windwoHeight;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}
}
