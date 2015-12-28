package com.example.glup.opengl3dmotion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 28/12/15.
 */
public class PiramideTexture {
    // Id de OpenGL de la textura de la pirámide
    private int texturaId;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los vértices de la pirámide
    private FloatBuffer bufferVertices;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los vértices de la base de la pirámide
    private FloatBuffer bufferVerticesBase;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL las texturas de la pirámide
    private FloatBuffer bufferTexturas;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los colores del cubo
    private FloatBuffer bufferColores;

    // Vértices de la pirámide
    private float vertices[] = {
            0.0f, 1.0f, 0.0f, // Punto superior del triángulo frontal
            -1.0f, -1.0f, 1.0f, // Punto izq. del triángulo frontal
            1.0f, -1.0f, 1.0f, // Punto dcho. del triángulo frontal
            0.0f, 1.0f, 0.0f, // Punto superior del triángulo dcho.
            1.0f, -1.0f, 1.0f, // Punto izq. del triángulo dcho.
            1.0f, -1.0f, -1.0f, // Punto dcho. del triángulo dcho.
            0.0f, 1.0f, 0.0f, // Punto sup. del triángulo de la base
            1.0f, -1.0f, -1.0f, // Punto izq. del triángulo de la base
            -1.0f, -1.0f, -1.0f, // Punto dcho. del triángulo de la base
            0.0f, 1.0f, 0.0f, // Punto superior del triángulo izq.
            -1.0f, -1.0f, -1.0f, // Punto izq. del triángulo izq.
            -1.0f, -1.0f, 1.0f // Punto dcho. del triángulo izq.
    };
    // Vértices de la base de la pirámide
    private float vertices_base[] = {
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f
    };
    // Matriz con texturas
    private float texturas[] = {
            0.5f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.5f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.5f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
            0.0f, 1.0f,
    };
    // Matriz con colores de la base en formato RGBA
    private float colores[] = {
            0.6f, 0.4f, 0.0f, 1.0f,
            0.6f, 0.4f, 0.0f, 1.0f,
            0.6f, 0.4f, 0.0f, 1.0f,
            0.6f, 0.4f, 0.0f, 1.0f
    };
    public PiramideTexture(int texturaId) {
// Usando el método local makeFloatBuffer definimos
// los buffer de la pirámide: vértices, texturas,
// vértices de su base y colores de su base.
        bufferVertices = makeFloatBuffer(vertices);
        bufferTexturas = makeFloatBuffer(texturas);
        bufferVerticesBase = makeFloatBuffer(vertices_base);
        bufferColores = makeFloatBuffer(colores);
// Guardamos el id de la imagen que sirve de textura
        this.texturaId = texturaId;
    } // end constructor
    public void draw(GL10 gl) {

        // Dibujamos al revés que las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
// Activamos la texturas en 2D (superficie)
        gl.glEnable(GL10.GL_TEXTURE_2D);
        // Indicamos el ID de la textura
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texturaId);
        // Indicamos el nº de coordenadas (3), el tipo de datos de la
// matriz (float), la separación en la matriz de los vértices
// (0) y el buffer con los vértices
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferVertices);
        // Seleccionamos la textura indicando el tamaño y tipo de la
// matriz de texturas
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, bufferTexturas);
        // Indicamos al motor OpenGL que le hemos pasado una matriz de
// vértices y de texturas
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // Dibujamos la superficie mediante la matriz en el modo
// triángulo
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);
        // Deshabilitamos la textura para seguir pintando sin ésta
        gl.glDisable(GL10.GL_TEXTURE_2D);
// Indicamos el nº de campos que definen el color (4), el tipo
// de datos de la matriz (float), la separación en la matriz de
// los colores (0) y el buffer con los colores.
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, bufferColores);
        // Indicamos al motor OpenGL que le hemos pasado una matriz de
// colores para la base
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // Establecemos los nuevos vértices de la base de la pirámide
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferVerticesBase);
        // Dibujamos la base de la pirámide mediante la matriz en el
// modo triángulo
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0,
                vertices_base.length/3);
        // Desactivamos el buffer de los vértices, texturas y colores.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    } // end draw

    // Método que crea un FloatBuffer. Así optimizamos el código fuente
    protected static FloatBuffer makeFloatBuffer(float[] matriz)
    {
        // Definimos el buffer multiplicando x 4 ya que un número float
        // tiene 4 bytes de longitud.
        ByteBuffer bb = ByteBuffer.allocateDirect(matriz.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(matriz);
        fb.position(0);
        return fb;
    }
}