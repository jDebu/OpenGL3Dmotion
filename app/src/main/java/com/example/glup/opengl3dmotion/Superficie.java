package com.example.glup.opengl3dmotion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 28/12/15.
 */
public class Superficie  extends GLSurfaceView implements GLSurfaceView.Renderer {
    // Objeto que define la pirámide con textura
    private PiramideTexture piramide;
    // Ángulos de rotación en X e Y. Inicialmente la pirámide
    // aparece un poco girada
    private float anguloX=-15.0f;
    private float anguloY=-45.0f;
    // Variables donde se almacenan los ángulos anteriores
    // para así poder calcular el cambio de ángulo y girar
    // la pirámide únicamente la diferencia de ángulo.
    private float anguloAntiguoX;
    private float anguloAntiguoY;
    // Constante que usamos de escala del toque del usuario
    private final float ESCALA_TOQUE = 0.2f;
    // Constructor de la clase
    public Superficie(Context context) {
        super(context);
        // Indicamos que el Renderer se define en esta misma superficie
        this.setRenderer(this);
        // Solicitamos el foco de la aplicación para que el usuario
        // pueda hacer clic en la imagen. Nota: esto sólo es necesario
        // si la aplicación tuviera otras Vistas, como botones
        this.requestFocus();
        // Indicamos que la superficie responde a toques del usuario,
        // es decir, implementa el evento onTouchEvent
        this.setFocusableInTouchMode(true);
    }
    // Evento que se lanza cuando se crea la superficie.
    // Aquí debemos indicar todo aquello que no cambia en la superficie
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Deshabilitamos la mezcla de colores
        gl.glDisable(GL10.GL_DITHER);
        // Habilitamos las texturas en 2D
        gl.glEnable(GL10.GL_TEXTURE_2D);
        // Establecemos el color NEGRO como fondo de la superficie
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Configuramos el buffer de profundidad
        gl.glClearDepthf(1.0f);
        // Modo de renderizado de la profundidad
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // Función de comparación de la profundidad
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Cómo se calcula la perspectiva
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        // Cargamos la imagen en un bitmap que luego servirá de textura
// a la pirámide
        Bitmap textura = BitmapFactory.decodeResource(getResources(),
                R.drawable.jay);
// Como usamos una única textura, la matriz de texturas sólo
// debe tener un elemento
        int texturaIds[] = new int[1];
        // Asociamos la matriz de texturas al gráfico OpenGL
        gl.glGenTextures(1, texturaIds, 0);
        // Seleccionamos la primera textura de la matriz
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texturaIds[0]);
        // Cargamos la imagen en formato bitmap en la textura
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, textura, 0);
        // Indicamos cómo tratar la textura cuando es necesario
        // modifiarla porque debe hacerse más pequeña
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        // Indicamos cómo tratar la textura cuando es necesario
// modificarla porque debe hacerse más grande
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
// Libera el bitmap ya que ya no es necesario
        textura.recycle();
// Creamos la pirámide usando la textura correspondiente
        piramide = new PiramideTexture(texturaIds[0]);
    }
    // Evento que se lanza cada vez que es necesario dibujar la
    // superficie
    public void onDrawFrame(GL10 gl) {
        // Establecemos el color NEGRO como fondo de la superficie
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
        // Cada vez que se dibuja una escena hay que limpiar
        // tanto el buffer de color como el de profundidad
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Reiniciamos la matriz de proyección del gráfico
        gl.glLoadIdentity();
        // Movemos la posición de los ejes en (x,y,z).
        // Es decir, alejamos el dibujo en el eje Z dando
        // sensación de profundidad.
        gl.glTranslatef(0.0f, 0.0f, -3.0f);
        // Escalamos la pirámide un 50% para que quepa en la pantalla
        gl.glScalef(0.5f, 0.5f, 0.5f);
// Rotamos en los ejes X e Y el toque del usuario
        gl.glRotatef(anguloX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(anguloY, 0.0f, 1.0f, 0.0f);
// Dibujamos la pirámide
        piramide.draw(gl);
    } // end onDrawFrame
    // Evento que se lanza cuando cambia la superficie
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Nos aseguramos de que no se puede dividir por 0
        if(height == 0) {
            height = 1;
        }
// Reiniciamos la zona de visión
        gl.glViewport(0, 0, width, height);
// Establecemos la matriz de proyección como
// activa para modificarla y poder girar la pirámide
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
    // Evento que se lanza cuando el usuario toca la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
// Obtenemos el toque del usuario
        float x = event.getX();
        float y = event.getY();
        // Si el toque es un movimiento sobre la pantalla
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            // Calculamos el cambio de ángulo restando con los
            // anteriores
            float dx = x - anguloAntiguoX;
            float dy = y - anguloAntiguoY;
            // Los nuevos ángulos son el actual + cambio de ángulo
            // teniendo en cuenta un factor de corrección
            anguloX += dy * ESCALA_TOQUE;
            anguloY += dx * ESCALA_TOQUE;
        }
        // Almacenamos los nuevos ángulos
        anguloAntiguoX = x;
        anguloAntiguoY = y;
        // Indicamos que se controla el evento onTouch
        return true;
    } // end onTouchEvent
} // end clase

