package com.example.glup.opengl3dmotion;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 22/12/15.
 */
public class MiRenderer implements GLSurfaceView.Renderer {
    // Constantes que indican el tipo de polígono que debemos dibujar
    public static int MOSTRAR_TRIANGULO=1;
    public static int MOSTRAR_CUADRADO=2;
    public static int MOSTRAR_CUBO=3;
    public static int MOSTRAR_PIRAMIDE=4;
    // Guarda el polígono que estamos mostrando
    private int mostrar_poligono=-1;
    // Objeto del tipo Triángulo que describe su geometría
    private Triangulo triangulo;
    // Objeto del tipo Cuadrado que describe su geometría
    private Cuadrado cuadrado;
    // Objeto del tipo Pirámide que describe su geometría
    private Piramide piramide;
    // Objeto del tipo Cubo que describe su geometría
    private Cubo cubo;
    // Ángulo de giro del movimiento del polígono
    private float angulo;
    //prueba
    private Square square;

    public  MiRenderer(int mostrar_poligono,boolean ver_color){
        // Guardamos el tipo de polígono que queremos mostrar y
        // creamos la geometría correspondiente
        this.mostrar_poligono=mostrar_poligono;
        if (mostrar_poligono == MOSTRAR_TRIANGULO)
            triangulo = new Triangulo(ver_color);
        else if (mostrar_poligono == MOSTRAR_CUADRADO)
            //cuadrado = new Cuadrado(ver_color);
            square=new Square();
        else if (mostrar_poligono == MOSTRAR_CUBO)
            cubo = new Cubo(ver_color);
        else if (mostrar_poligono == MOSTRAR_PIRAMIDE)
            piramide = new Piramide();
    }
    // Evento que se lanza cuando se crea la superficie.
    // Aquí debemos indicar todo aquello que no cambia en la superficie
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Establecemos el color GRIS como fondo de la superficie
        gl.glClearColor(0.6f, 0.6f, 0.6f, 0.5f);
        // Indicamos el modo de sombreado suave
        gl.glShadeModel(GL10.GL_SMOOTH);
        // Configuramos el buffer de profundidad
        gl.glClearDepthf(1.0f);
        // Modo de renderizado de la profundidad
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // Función de comparación de la profundidad
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Cómo se calcula la perspectiva
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }
    // Evento que se lanza cuando cambia la superficie
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Nos aseguramos de que no se puede dividir por 0
        if(height == 0) {
        }     height = 1;
        // Reiniciamos la zona de visión
        gl.glViewport(0, 0, width, height);
        // Establecemos la matriz de proyección como
        // activa para modificarla y poder girar el polígono
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reiniciamos la matriz de proyección del gráfico
        gl.glLoadIdentity();
        // Calculamos el tamaño de la perspectiva en función del nuevo
        // tamaño de la pantalla
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height,
                0.1f, 100.0f);
        // Establecemos de nuevo la matriz de vista/modelo
        // como activa
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reiniciamos la matriz de proyección del gráfico
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        angulo=0;
        // Cada vez que se dibuja una escena hay que limpiar
        // tanto el buffer de color como el de profundidad
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Reinicia la matriz de proyección del gráfico que se utiliza para
        //poder realizar cambios de perspectiva(giros,traslaciones,etc)
        //movemos el nuevo puntero al eje (0,0,0)
        gl.glLoadIdentity();
        // Movemos la posición de los ejes en (x,y,z).
        // Es decir, alejamos el dibujo en el eje Z dando
        // sensación de profundidad.
        gl.glTranslatef(0.0f, 0.0f, -4.0f);
        // Dibujamos el polígono solicitado
        if (mostrar_poligono == MOSTRAR_TRIANGULO) {
            // Rotamos el triángulo un ángulo sobre el eje Y
            gl.glRotatef(angulo, 0.0f, 1.0f, 0.0f);
            triangulo.draw(gl);
            // Definimos el ángulo del siguiente giro
            angulo -= 0.45f;
        }else if (mostrar_poligono == MOSTRAR_CUADRADO) {
            // Rotamos el cuadrado un ángulo sobre el eje Y
            //gl.glRotatef(angulo, 0.0f, 1.0f, 0.0f);
            //cuadrado.draw(gl);
            square.draw(gl);
            // Definimos el ángulo del siguiente giro
            //angulo += 0.4f;
        }else if (mostrar_poligono == MOSTRAR_CUBO) {
            // Escalamos el cubo al 80% para que quepa en
            // la pantalla del AVD
            gl.glScalef(0.8f, 0.8f, 0.8f);
            // Rotamos el cubo un ángulo sobre el eje X, Y, Z
            gl.glRotatef(angulo, 1.0f, 1.0f, 1.0f);
            cubo.draw(gl);
            // Definimos el ángulo del siguiente giro
            angulo -= 0.45f;
        } else {
        // Movemos la pirámide 1.0 al fondo para que quepa
        // en la pantalla del AVD
            gl.glTranslatef(0.0f, 0.0f, -1.0f);
            // Rotamos la pirámide un ángulo sobre el eje X,Y
            gl.glRotatef(angulo, 0.5f, 1.0f, 0.0f);
            piramide.draw(gl);
            // Definimos el ángulo del siguiente giro
        }
    }
}
