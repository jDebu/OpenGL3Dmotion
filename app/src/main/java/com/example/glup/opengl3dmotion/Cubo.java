package com.example.glup.opengl3dmotion;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 22/12/15.
 */
public class Cubo {
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los vértices del cubo
    private FloatBuffer bufferVertices;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los colores del cubo
    private FloatBuffer bufferColores;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los índices del cubo
    private ByteBuffer bufferIndices;
    // Vértices del cubo
    private float vertices[] = {
            -1.0f,  -1.0f,  1.0f,   // abajo delante izq. (V0)
            1.0f,  -1.0f,  1.0f,    // abajo delante dcha. (V1)
            -1.0f,   1.0f,  1.0f,   // arriba delante izq. (V2)
            1.0f,   1.0f,  1.0f,    // arriba delante dcha. (V3)
            1.0f,  -1.0f, -1.0f,    // abajo detrás dcha. (V4)
            -1.0f,  -1.0f, -1.0f,   // abajo detrás izq. (V5)
            1.0f,   1.0f, -1.0f,    // arriba detrás dcha. (V6)
            -1.0f, 1.0f, -1.0f      // arriba detrás izq. (V7)
    };
    // Matriz con colores en formato RGBA
    private float colores[] = {
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  1.0f,  1.0f,
            0.0f,  0.0f,  1.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f
    };
    // Matriz con los índices que definen los triángulos que
    // se usan para crear las caras
    private byte indices[] = {
    /*  Por ejemplo:
        Cara definida mediante los vértices abajo detrás izq. (0),
        abajo delante izq. (4),
        abajo delante dcha. (5), 0, 5, 1 */
            5,0,1,    5,1,4,
            // Y así las 6 caras en total
            4,1,3,  4,3,6,
            6,3,2,  6,2,7,
            7,2,0,  7,0,5,
            0,2,3,  0,3,1,
            7,5,4,  7,4,6
    };
    public void draw(GL10 gl) {

    }
}
