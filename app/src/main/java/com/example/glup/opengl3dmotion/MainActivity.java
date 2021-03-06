package com.example.glup.opengl3dmotion;

import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    // Superficie de OpenGL
    private GLSurfaceView glSurface;
    private OpenGLRenderer renderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Creamos la instancia de la superficie de OpenGL
        //glSurface = new Superficie(this);
        // Definimos el Renderer de la superficie
        //renderer=new OpenGLRenderer(0);
        //renderer.setFactory(BitmapFactory.decodeResource(getResources(), R.drawable.funny_luffy));
        //glSurface.setRenderer(renderer);
        //setContentView(glSurface);
        // Buscamos el FrameLayout para añadirle la superficie anterior
        glSurface=new GLCubeView(this);
        //myGLES20Renderer renderer1=new myGLES20Renderer();
        //glSurface.setRenderer(renderer1);
        final FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        final FrameLayout frame2= (FrameLayout)findViewById(R.id.frame2);
        frame.addView(glSurface);

        glSurface = new GLSurfaceView(this);
        MyGLRenderer renderer=new MyGLRenderer(this);
        //renderer.setFactory(BitmapFactory.decodeResource(getResources(), R.drawable.funny_luffy));
        //renderer.setRgba(new float[]{0.2f,0.2f,0.2f,0.5f});
        glSurface.setRenderer(renderer);
        frame2.addView(glSurface);
        /*
        // Buscamos el resto de Vistas de la Actividad
        final CheckBox conColor = (CheckBox)findViewById(R.id.cbColorear);
        final Button boton_triang = (Button) findViewById(R.id.triangulo);
        // Creamos el evento onClick de los botones
        boton_triang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Recreamos la superficie con el polígono triángulo e
                // indicamos si es necesario añadir el color
                glSurface = new GLSurfaceView(MainActivity.this);
                glSurface.setRenderer(new MiRenderer(MiRenderer.MOSTRAR_TRIANGULO, conColor.isChecked()));
                // Quitamos las Vistas del Frame y añadimos la nueva
                // superficie
                frame.removeAllViews();
                frame.addView(glSurface, 0);
            }});
        final Button boton_cuadrado = (Button)findViewById(R.id.cuadrado);
        boton_cuadrado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Recreamos la superficie con el polígono cuadrado e
                // indicamos si es necesario añadir el color
                glSurface = new GLSurfaceView(MainActivity.this);
                glSurface.setRenderer(new
                        MiRenderer(MiRenderer.MOSTRAR_CUADRADO,
                        conColor.isChecked()));
                // Quitamos las Vistas del Frame y añadimos la nueva
                // superficie
                frame.removeAllViews();
                frame.addView(glSurface, 0);
            } });
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    // Cuando la Actividad vuelve a primer plano, indicamos a la
    // superficie que se actualice de nuevo
    @Override
    protected void onResume() {
        super.onResume();
        glSurface.onResume();
    }
    // Cuando la Actividad pasa a segundo plano, indicamos a la
    // superficie que NO se actualice 109 @Override
    protected void onPause() {
        super.onPause();
        glSurface.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {return false;}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {return false;}
}
