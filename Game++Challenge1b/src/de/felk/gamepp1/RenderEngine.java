package de.felk.gamepp1;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;

public class RenderEngine {

	public static void init(int width, int height) throws LWJGLException {

		try {
			DisplayMode[] displayModes = Display.getAvailableDisplayModes();

			for (DisplayMode mode : displayModes) {
				//boolean flag1 = mode.getFrequency() == Display.getDisplayMode().getFrequency();
				boolean flag2 = mode.getBitsPerPixel() == Display.getDisplayMode().getBitsPerPixel();
				boolean flag3 = mode.getHeight() == height;
				boolean flag4 = mode.getWidth() == width;

				if (flag2 && flag3 && flag4) {
					Display.setDisplayMode(mode);
				}

			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		Display.create();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glViewport(0, 0, width, height);
	}
	
	public static void setOrtho() {
		glOrtho(0.0D, Display.getDisplayMode().getWidth(), 0.0D, Display.getDisplayMode().getHeight(), 1f, -1f);
	}
	
	public static void disableTexture() {
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void disableLighting() {
		glDisable(GL_LIGHTING);
	}
	
	public static void enableTexture() {
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void enableLighting() {
		glDisable(GL_LIGHTING);
	}
	
	public static void enableVSync() {
		Display.setVSyncEnabled(true);
	}
	
	public static void disableVSync() {
		Display.setVSyncEnabled(false);
	}
	
	public static void clearScreen() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

}
