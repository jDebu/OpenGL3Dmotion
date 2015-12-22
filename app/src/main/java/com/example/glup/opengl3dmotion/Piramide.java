package com.example.glup.opengl3dmotion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 22/12/15.
 */
public class Piramide {
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los vértices de la pirámide
    private FloatBuffer bufferVertices;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los colores de la pirámide
    private FloatBuffer bufferColores;
    // Vértices de la pirámide - 4 triangulos
    private float vertices[] = {
            0.0f,  1.0f,  0.0f,  // Punto superior del triángulo frontal
            -1.0f, -1.0f,  1.0f,  // Punto izq. del triángulo frontal
            1.0f, -1.0f,  1.0f,  // Punto dcho. del triángulo frontal
            0.0f,  1.0f,  0.0f,  // Punto superior del triángulo dcho.
            1.0f, -1.0f,  1.0f,  // Punto izq. del triángulo dcho.
            1.0f, -1.0f, -1.0f,  // Punto dcho. del triángulo dcho.
            0.0f,  1.0f,  0.0f,  // Punto superior del triángulo de la base
            1.0f, -1.0f, -1.0f,  // Punto izq. del triángulo de la base
            -1.0f, -1.0f, -1.0f,  // Punto dcho. del triángulo de la base
            0.0f,  1.0f,  0.0f,  // Punto superior del triángulo izq.
            -1.0f, -1.0f, -1.0f,  // Punto izq. del triángulo izq.
            -1.0f, -1.0f,  1.0f   // Punto dcho. del triángulo izq.
    };
    // Matriz con colores en formato RGBA
    private float colores[] = {
            1.0f, 0.0f, 0.0f, 1.0f, // Rojo
            0.0f, 1.0f, 0.0f, 1.0f, // Verde
            0.0f, 0.0f, 1.0f, 1.0f, // Azul
            1.0f, 0.0f, 0.0f, 1.0f, // Rojo
            0.0f, 0.0f, 1.0f, 1.0f, // Azul
            0.0f, 1.0f, 0.0f, 1.0f, // Verde
            1.0f, 0.0f, 0.0f, 1.0f, // Rojo
            0.0f, 1.0f, 0.0f, 1.0f, // Verde
            0.0f, 0.0f, 1.0f, 1.0f, // Azul
            1.0f, 0.0f, 0.0f, 1.0f, // Rojo
            0.0f, 0.0f, 1.0f, 1.0f, // Azul
            0.0f, 1.0f, 0.0f, 1.0f  // Verde
    };
    public Piramide(){
        // Definimos el buffer con los vértices del polígono.
        // Un número float tiene 4 bytes de longitud, así que 121 // multiplicaremos x 4 el número de vértices.
        ByteBuffer byteBuf=ByteBuffer.allocateDirect(vertices.length*4);
        byteBuf.order(ByteOrder.nativeOrder());
        bufferVertices = byteBuf.asFloatBuffer();
        bufferVertices.put(vertices);
        bufferVertices.position(0);
        // Definimos el buffer de la matriz de colores de igual forma
        // que hemos hecho con la matriz de vértices
        byteBuf = ByteBuffer.allocateDirect(colores.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        bufferColores = byteBuf.asFloatBuffer();
        bufferColores.put(colores);
        bufferColores.position(0);
    }
    // Método que invoca el Renderer cuando debe dibujar la pirámide
    public void draw(GL10 gl) {
        // Dibujamos al revés que las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
        // Indicamos el no de coordenadas (3), el tipo de datos de la
        // matriz (float), la separación en la matriz de los vértices
        // (0) y el buffer con los vértices
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferVertices);
        // Indicamos el no de campos que definen el color (4), el tipo
        // de datos de la matriz (float), la separación en la matriz de
        // los colores (0) y el buffer con los colores.
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, bufferColores);
        // Indicamos al motor OpenGL que le hemos pasado una matriz de
        // vértices y de colores
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // Dibujamos la superficie mediante la matriz en el modo
        // triángulo
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);
        // Desactivamos el buffer de los vértices y colores
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
