package de.felk.gamepp1;

import static org.lwjgl.opengl.GL11.*;

public class RenderHelper {

	public static void renderQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);

		glTexCoord2f(0, 0);
		glColor3f(0, 1, 0);
		glVertex3f(x, y, -1);
		glTexCoord2f(0, 1);
		glVertex3f(x, y + height, -1);
		glTexCoord2f(1, 1);
		glVertex3f(x + width, y + height, -1);
		glTexCoord2f(1, 0);
		glVertex3f(x + width, y, -1);

		glEnd();

	}

	public static void drawFilledCircle(float x, float y, float radius) {
		int i;
		int triangleAmount = 32; //# of triangles used to draw circle
		//GLfloat radius = 0.8f; //radius
		float twicePi = (float) (2.0f * Math.PI);
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y); // center of circle
		for (i = 0; i <= triangleAmount; i++) {
			glVertex2f((float) (x + (radius * Math.cos(i * twicePi / triangleAmount))),
			           (float) (y + (radius * Math.sin(i * twicePi / triangleAmount))));
		}
		glEnd();
	}

}
