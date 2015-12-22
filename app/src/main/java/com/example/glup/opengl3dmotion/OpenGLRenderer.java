package com.example.glup.opengl3dmotion;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 19/12/15.
 */
public class OpenGLRenderer implements Renderer{
    private Cubo cubo;
    private Cuadrado cuadrado;
    private int tipoPoligono;
    private float angulo;
    private float[] rgba=new float[]{0.6f, 0.6f, 0.6f, 0.5f};
    public OpenGLRenderer(){
        tipoPoligono=0;
        angulo=0;
        cubo=new Cubo();
    }
    public OpenGLRenderer(int poligono){ //poligono=0 defaul
        tipoPoligono=poligono;
        if (poligono!=0){
            cuadrado=new Cuadrado(true);
            angulo=0;
        }else {
            tipoPoligono=0;
            angulo=0;
            cubo=new Cubo();
        }
    }

    public void setRgba(float[] rgba) {
        this.rgba = rgba;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //set background color to balck rgba
        // Establecemos el color GRIS como fondo de la superficie
        gl.glClearColor(rgba[0], rgba[1], rgba[2], rgba[3]);
        //enable smooth shading, default not really needed
        gl.glShadeModel(GL10.GL_SMOOTH);
        //depth buffer setup
        gl.glClearDepthf(1.0f);
        //enables depth testing
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //the type of depth testing to do
        gl.glDepthFunc(GL10.GL_LEQUAL);
        //Really nice perspectiva calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //sets the current view port to the new size
        gl.glViewport(0,0,width,height);
        //select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //reset the projection matrix
        gl.glLoadIdentity();
        //calculate the aspect ratio of the window
        GLU.gluPerspective(gl,45.0f,(float) width/ (float) height,0.1f, 100.0f);
        //select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //reset the modelview matrix
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //clears the screen and depth buffer
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //replace the current matrix with identity matriz
        gl.glLoadIdentity();
        //Translates 4 units into the screen
        gl.glTranslatef(0, 0, -4);
        //draw our cubo
        gl.glScalef(0.5f, 0.5f, 0.5f);
        if (tipoPoligono==0){
            // Rotamos el cubo un ángulo sobre el eje X, Y, Z
            gl.glRotatef(angulo, 1.0f, 1.0f, 1.0f);
            cubo.draw(gl);
            // Definimos el ángulo del siguiente giro
            angulo -= 0.45f;
        }else{
            // Rotamos el cubo un ángulo sobre el eje Y
            gl.glRotatef(angulo, 0.0f, 1.0f, 0.0f);
            cuadrado.draw(gl);
            // Definimos el ángulo del siguiente giro
            angulo += 0.4f;
        }


    }

}
