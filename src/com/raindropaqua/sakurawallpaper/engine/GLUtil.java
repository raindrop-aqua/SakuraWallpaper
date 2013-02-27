package com.raindropaqua.sakurawallpaper.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLUtil {

	public static FloatBuffer makeFloatBuffer(float[] vertex) {
		ByteBuffer bb = ByteBuffer.allocateDirect(vertex.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(vertex);
		fb.position(0);
		return fb;
	}
}
