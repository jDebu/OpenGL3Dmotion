package com.example.glup.opengl3dmotion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    };//6 caras cuadradas *2 trinaglus en 1 cara=12
    public Cubo(){
        // Definimos el buffer con los vértices del polígono.
        // Un número float tiene 4 bytes de longitud, así que
        // multiplicaremos x 4 el número de vértices.
        ByteBuffer byteBuf=
                ByteBuffer.allocateDirect(vertices.length*4);
        byteBuf.order(ByteOrder.nativeOrder());
        bufferVertices = byteBuf.asFloatBuffer();
        bufferVertices.put(vertices);
        bufferVertices.position(0);
        // Definimos el buffer de la matriz de colores de igual
        // forma que hemos hecho con la matriz de vértices
        byteBuf = ByteBuffer.allocateDirect(colores.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        bufferColores = byteBuf.asFloatBuffer();
        bufferColores.put(colores);
        bufferColores.position(0);
        // Definimos el buffer de la matriz de índices de igual
        // forma que hemos hecho con la matriz de vértices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.put(indices);
        bufferIndices.position(0);
    }
    // Método que invoca el Renderer cuando debe dibujar el cubo
    public void draw(GL10 gl) {
        // Dibujamos al revés que las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
        // Indicamos el no de coordenadas (3), el tipo de datos de
        // la matriz (float), la separación en la matriz de los
        // vértices (0) y el buffer con los vértices
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferVertices);
        // Indicamos el no de campos que definen el color (4), el
        // tipo de datos de la matriz (float), la separación en la
        // matriz de los colores (0) y el buffer con los colores.
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, bufferColores);
        // Indicamos al motor OpenGL que le hemos pasado una matriz
        // de vértices y de colores
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // Dibujamos la superficie mediante la matriz en el modo
        // triángulo utilizando los índices para unirlos y formar
        // las caras
        gl.glDrawElements(GL10.GL_TRIANGLES, 36,
                GL10.GL_UNSIGNED_BYTE, bufferIndices);
        // Desactivamos el buffer de los vértices y colores
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
