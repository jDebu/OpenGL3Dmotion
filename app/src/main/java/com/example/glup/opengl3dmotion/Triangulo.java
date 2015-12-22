package com.example.glup.opengl3dmotion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 22/12/15.
 */
public class Triangulo {
    // Indica si deseamos colorear el triángulo
    private boolean conColor;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los vértices del triángulo
    private FloatBuffer bufferVertices;
    // Vértices del triángulo 
    private float vertices[] = {
            0.0f,  1.0f, 0.0f, // Arriba
            -1.0f, -1.0f, 0.0f, // Abajo izquierda
            1.0f, -1.0f, 0.0f // Abajo derecha
    };
    // Constructor del triángulo
    public Triangulo(boolean conColor) {
        // Definimos el buffer con los vértices del polígono.
        // Un número float tiene 4 bytes de longitud, así que
        // multiplicaremos x 4 el número de vértices.
        ByteBuffer byteBuf=ByteBuffer.allocateDirect(vertices.length*4);
        // Establecemos el orden de los bytes en el buffer con el valor
        // nativo (es algo así como indicar cómo se leen los bytes de
        // izq a dcha o al revés).
        byteBuf.order(ByteOrder.nativeOrder());
        // Asignamos el nuevo buffer al buffer de esta clase
        bufferVertices = byteBuf.asFloatBuffer();
        // Introducimos los vértices en el buffer
        bufferVertices.put(vertices);
        // Movemos la posición del buffer al inicio
        bufferVertices.position(0);
        // Guardamos si es necesario colorear el polígono
        this.conColor=conColor;
    }
    // Método que invoca el Renderer cuando debe dibujar el triángulo
    public void draw(GL10 gl) {
        // Dibujamos al revés que las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
        // Indicamos el no de coordenadas (3), el tipo de datos de la
        // matriz (float), la separación en la matriz de los vértices
        // (0) y el buffer con los vértices
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferVertices);
        // Indicamos al motor OpenGL que le hemos pasado una matriz de
        // vértices
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Dibujamos la superficie mediante la matriz en el modo
        // triángulo
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
        // Si hemos indicado que queremos color...
        if (conColor)
            // Establecemos el color del triángulo en modo RGBA
            gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
        // Desactivamos el buffer de los vértices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
    /*
    el método glFrontFace(GL10.GL_CCW). Este sentido de dibujo 
    es importante ya que determina la dirección del vector normal 
    de iluminación; si éste apunta a la dirección equivocada, 
    no veremos nada si utilizamos iluminación
    El orden contrario a las agujas del reloj indica que la superficie 
    del polígono está dirigida hacia el observador (regla de la mano derecha). 
    Fíjate en este esquema:
    * */
    /*
    Con el método glVertexPointer() indicamos el número de coordenadas (3), 
    el tipo de datos de la matriz (float), la separación (0) en la matriz 
    de los vértices (podría haber más información en esta matriz además de 
    los vértices) y el buffer con los vértices.
    * */
    /*
    Existen dos métodos para dibujar un gráfico mediante sus vértices:
    -public abstract void glDrawArrays(int mode, int first, int count): dibuja el
    gráfico según el orden definido en la construcción de los vértices. Ya hemos utilizado este
    método en el código anterior.
    - public abstract void glDrawElements(int mode, int count, int type, Buffer indices): 
    el segundo método es similar al anterior, si bien debemos indicar el orden en el que se 
    pintarán los vértices.
    * */
}
