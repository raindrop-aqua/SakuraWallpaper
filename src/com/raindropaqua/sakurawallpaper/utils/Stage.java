package com.raindropaqua.sakurawallpaper.utils;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.raindropaqua.sakurawallpaper.casts.Base;
import com.raindropaqua.sakurawallpaper.casts.Sakura;

public class Stage {

	private List<Base> bases = new LinkedList<Base>();

	public Stage() {
		for (int i = 0; i < 100; i++) {
			bases.add(new Sakura());
		}
	}

	public void onStandby(GL10 gl) {
		for (Base base : bases) {
			if (base.onStandby(gl) == false) {
				bases.remove(base);
			}
		}
	}

	public void onUpdate() {
		for (Base base : bases) {
			if (base.onUpdate() == false) {
				bases.remove(base);
			}
		}
	}

	public void onDraw(GL10 gl) {
		for (Base base : bases) {
			base.onDraw(gl);
		}
	}

}
