package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private int w, h;
	private String name;
	private long window;
	public int frames;
	public static long time;
	public Input input;

	public Window(int width, int height, String title) {
		this.w = width;
		this.h = height;
		this.name = title;
	}

	public void create() {
		if(!GLFW.glfwInit()) {
			System.out.println("Error Window Init");
			return;
		}
		this.input = new Input();

		this.window = GLFW.glfwCreateWindow(this.w, this.h, this.name, 0, 0);

		if(this.window == 0) {
			System.out.println("Error window Create");
			return;
		}

		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(this.window, videoMode.width()-this.w/2 - 700, videoMode.height()-this.h - 100);
		GLFW.glfwMakeContextCurrent(this.window);

		GLFW.glfwSetKeyCallback(this.window, input.getKeyboardCallback());
		GLFW.glfwSetCursorPosCallback(this.window, input.getCursorPosCallback());
		GLFW.glfwSetMouseButtonCallback(this.window, input.getMouseButtonsCallback());

		GLFW.glfwShowWindow(this.window);

		GLFW.glfwSwapInterval(1);

		time = System.currentTimeMillis();
	}

	public void update() {
		GLFW.glfwPollEvents();
		this.frames++;
		if(System.currentTimeMillis() > time + 1000) {
			GLFW.glfwSetWindowTitle(this.window, this.name + " | FPS: " + this.frames);
			time = System.currentTimeMillis();
			this.frames = 0;
		}
	}

	public void destroy() {
		input.destroy();
		GLFW.glfwWindowShouldClose(this.window);
		GLFW.glfwDestroyWindow(this.window);
		GLFW.glfwTerminate();
	}

	public void swapBuffers() {
		GLFW.glfwSwapBuffers(this.window);
	}

	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(this.window);
	}
}