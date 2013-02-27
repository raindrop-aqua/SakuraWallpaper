package com.raindropaqua.sakurawallpaper;

import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import com.raindropaqua.sakurawallpaper.engine.GLSurface;

public class Main extends WallpaperService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
	public Engine onCreateEngine() {
		return new GLEngine();
	}

	public class GLEngine extends Engine {

		private GLSurface gl = null;

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
			gl = new GLSurface(getSurfaceHolder());
			gl.start();
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			Log.i("Event", String.format("SurfaceChanged:%d x %d", width, height));
			super.onSurfaceChanged(holder, format, width, height);

			gl.setWindowWidth(width);
			gl.setWindowHeight(height);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			Log.i("Event", "SurfaceDestroyed");
			super.onSurfaceDestroyed(holder);
			if (gl != null) {
				gl.onDestroy();
				gl = null;
			}
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			Log.i("Event", "VisibilityChanged:" + visible);
			super.onVisibilityChanged(visible);

			if (visible) {
				gl.onResume();
			} else {
				gl.onPause();
			}
		}
	}
}
