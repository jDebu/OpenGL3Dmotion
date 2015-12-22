package com.example.glup.opengl3dmotion;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 22/12/15.
 */
public class Cuadrado {
    // Indica si debemos dibujar el cuadrado con color
    private boolean conColor;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los vértices del cuadrado
    private FloatBuffer bufferVertices;
    // Buffer de tipo float que usamos para pasar
    // a la librería OpenGL los colores del cuadrado
    private FloatBuffer bufferColores;
    // Vértices del cuadrado
    private float vertices[] = {
            -1.0f, -1.0f, 0.0f,  // Abajo izq
            1.0f, -1.0f, 0.0f,  // Abajo dcha
            -1.0f,  1.0f, 0.0f,  // Arriba izq
            1.0f,  1.0f, 0.0f   // Arriba dcha
    };
    // Matriz con los colores rojo, verde y azul (RGBA)
    private float colores[] = {
            1.0f, 0.0f, 0.0f, 1.0f, // Color Rojo con 100% de luminosidad
            0.0f, 1.0f, 0.0f, 1.0f, // Color Verde con 100% de luminosidad
            0.0f, 0.0f, 1.0f, 1.0f  // Color Azul con 100% de luminosidad
    };
    // Mapping coordinates for the vertices
    float textureCoordinates[] = { 0.0f, 1.0f, //
            1.0f, 1.0f, //
            0.0f, 0.0f, //
            1.0f, 0.0f, //
    };
    //la UV textura buffer
    private FloatBuffer textureBuffer;
    //la textura id
    private int textureId = -1;
    //el bitmap que se desea cargar como textura
    private Bitmap bitmap;
    //indicador si cargo la textura
    private boolean loadTexture=false;
    
    // Constructor del cuadrado
    public Cuadrado(boolean conColor) {
        // Definimos el buffer con los vértices del polígono.
        // Un número float tiene 4 bytes de longitud, así que
        // multiplicaremos x 4 el número de vértices.
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
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
        // Si es necesario colorear el cuadrado...
        if (conColor) {
            // Definimos el buffer de la matriz de colores de igual
            // forma que hemos hecho con la matriz de vértices
            byteBuf = ByteBuffer.allocateDirect(colores.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            bufferColores = byteBuf.asFloatBuffer();
            bufferColores.put(colores);
            bufferColores.position(0);
        }
        setTextureCoordinates(textureCoordinates);
    }
    // Método que invoca el Renderer cuando debe dibujar el cuadrado
    public void draw(GL10 gl) {
        // Dibujamos al revés que las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
        // Indicamos el no de coordenadas (3), el tipo de datos de la
        // matriz (float), la separación en la matriz de los vértices
        // (0) y el buffer con los vértices
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferVertices);
        // Indicamos al motor OpenGL que le hemos pasado una matriz de 115 // vértices
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Buffer de colores
        if (conColor) {
            // Indicamos el no de campos que definen el color (4), el
            // tipo de datos de la matriz (float), la separación en la
            // matriz de los colores (0) y el buffer con los colores.
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, bufferColores);
            // Indicamos al motor OpenGL que le hemos pasado una matriz
            // de colores
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        }
        //begin new part
        //ver el indicador de carga de textura
        if (loadTexture){
            loadGLTexture(gl);
            loadTexture=false;
        }
        if (textureId != -1 && textureBuffer != null){
            gl.glEnable(GL10.GL_TEXTURE_2D);
            //habilitar el estado de la textura
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            //point to our buffers
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
        }
        //end new part
        // Dibujamos la superficie mediante la matriz en el modo
        // triángulo
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
        // Desactivamos el buffer de los vértices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Desactivamos el buffer de los colores si es necesario
        if (conColor){
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        }
        // New part...
        if (textureId != -1 && textureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }// ... end new part.
    } // end clase

    public void loadBitmap(Bitmap bitmap) { // New function.
        this.bitmap = bitmap;
        loadTexture = true;
    }
    private void loadGLTexture(GL10 gl) { // New function
        // Generate one texture pointer...
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        textureId = textures[0];

        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

        // Create Nearest Filtered Texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);

        // Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);

        // Use the Android GLUtils to specify a two-dimensional texture image
        // from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    }
    protected void setTextureCoordinates(float[] textureCoords) { // New
        // function.
        // float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer byteBuf = ByteBuffer
                .allocateDirect(textureCoords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuf.asFloatBuffer();
        textureBuffer.put(textureCoords);
        textureBuffer.position(0);
    }
}
