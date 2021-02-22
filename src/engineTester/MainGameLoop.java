package engineTester;

import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.File;

public class MainGameLoop {
    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("C:\\Users\\user01\\IdeaProjects\\CerealEngine\\lib\\natives").getAbsolutePath());

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        float[] vertices = {
                -0.5f, 0.5f, 0, // V0
                -0.5f, -0.5f, 0, // V1
                0.5f, -0.5f, 0, // V2
                0.5f, 0.5f, 0 // V3
        };

        int[] indices = {
                0, 1, 3, // Top left triangle (V0, V1, V3)
                3, 1, 2 // Bottom right triangle (V3, V1, V2)
        };

        float[] textureCoords = {
                0, 0, // V0
                0, 1, // V1
                1, 1, //V2
                1, 0 // V3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("walter")));

        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);

        while(!Display.isCloseRequested()){
            entity.increasePosition(0, 0, -0.1f);
            entity.increaseRotation(0, 0, 5);
            renderer.prepare();
            shader.start();
            // Game logic
            // Render
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}