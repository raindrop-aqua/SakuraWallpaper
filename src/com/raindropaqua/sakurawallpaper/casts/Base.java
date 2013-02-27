package com.raindropaqua.sakurawallpaper.casts;

import javax.microedition.khronos.opengles.GL10;

public interface Base {
	public boolean onStandby(GL10 gl);
	public boolean onUpdate();
	public void onDraw(GL10 gl);
}
