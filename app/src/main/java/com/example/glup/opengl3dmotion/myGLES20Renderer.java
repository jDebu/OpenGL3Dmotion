package com.example.glup.opengl3dmotion;

/**
 * Created by Glup on 28/12/15.
 */


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

public class myGLES20Renderer implements Renderer {
    public float xAngle;
    public float yAngle;
    private FloatBuffer cubeBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indexBuffer;
    private float[] m_fViewMatrix = new float[16];
    private float[] m_fProjMatrix = new float[16];
    private float[] m_fVPMatrix = new float[16];
    private float[] m_fIdentity = new float[16];
    private int myProgram;
    private int myAVertexLocation;
    private int myAColorLocation;
    private int myu_VPMatrix;

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        // GLES20.glFrontFace(GLES20.GL_CW);
        initShapes();
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        myProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(myProgram, vertexShader);
        GLES20.glAttachShader(myProgram, fragmentShader);
        GLES20.glLinkProgram(myProgram);
    }
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        GLES20.glViewport(0, 0, width, height);
        Matrix.setLookAtM(m_fViewMatrix, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0);
        Matrix.frustumM(m_fProjMatrix, 0, -ratio, ratio, -1, 1, 2, 5);
        myAVertexLocation = GLES20.glGetAttribLocation(myProgram, "vPosition");
        myAColorLocation = GLES20.glGetAttribLocation(myProgram, "vColor");
        myu_VPMatrix = GLES20.glGetUniformLocation(myProgram, "u_VPMatrix");
        xAngle = 0;
        yAngle = 0;
    }
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(myProgram);
        GLES20.glVertexAttribPointer(myAVertexLocation, 3, GLES20.GL_FLOAT, false, 12, cubeBuffer);
        GLES20.glEnableVertexAttribArray(myAVertexLocation);
        GLES20.glVertexAttribPointer(myAColorLocation, 4, GLES20.GL_FLOAT, false, 16, colorBuffer);
        GLES20.glEnableVertexAttribArray(myAColorLocation);
        Matrix.setIdentityM(m_fIdentity, 0);
        Matrix.rotateM(m_fIdentity, 0, -xAngle, 0, 1, 0);
        Matrix.rotateM(m_fIdentity, 0, -yAngle, 1, 0, 0);
        Matrix.multiplyMM(m_fVPMatrix, 0, m_fViewMatrix, 0, m_fIdentity, 0);
        Matrix.multiplyMM(m_fVPMatrix, 0, m_fProjMatrix, 0, m_fVPMatrix, 0);
        GLES20.glUniformMatrix4fv(myu_VPMatrix, 1, false, m_fVPMatrix, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 192*3, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
    }
    private void initShapes()  {
        //cube co-ordinated
        float[] cube = {0.000000f,-0.878472f,-0.000000f,
                0.350694f,-0.819444f,-0.000000f,
                -0.000000f,-0.819444f,0.350694f,
                -0.350694f,-0.819444f,-0.000000f,
                0.000000f,-0.819444f,-0.350695f,
                0.326389f,-0.763889f,-0.326389f,
                0.326389f,-0.763889f,0.326389f,
                -0.326389f,-0.763889f,0.326389f,
                -0.326389f,-0.763889f,-0.326389f,
                -0.000000f,0.878472f,0.000000f,
                0.000000f,0.819444f,-0.350694f,
                -0.350695f,0.819444f,0.000000f,
                -0.000000f,0.819444f,0.350695f,
                0.350694f,0.819444f,0.000000f,
                0.326389f,0.763889f,-0.326389f,
                -0.326389f,0.763889f,-0.326389f,
                -0.326389f,0.763889f,0.326389f,
                0.326389f,0.763889f,0.326389f,
                0.878472f,0.000000f,0.000000f,
                0.819445f,0.000000f,-0.350694f,
                0.819444f,0.350694f,0.000000f,
                0.819444f,-0.000000f,0.350695f,
                0.819444f,-0.350694f,0.000000f,
                0.763889f,-0.326389f,-0.326389f,
                0.763889f,0.326389f,-0.326389f,
                0.763889f,0.326389f,0.326389f,
                0.763889f,-0.326389f,0.326389f,
                -0.000000f,-0.000000f,0.878472f,
                0.350694f,0.000000f,0.819445f,
                -0.000000f,0.350694f,0.819444f,
                -0.350695f,0.000000f,0.819444f,
                -0.000000f,-0.350694f,0.819444f,
                0.326389f,-0.326389f,0.763889f,
                0.326389f,0.326389f,0.763889f,
                -0.326389f,0.326389f,0.763889f,
                -0.326389f,-0.326389f,0.763889f,
                -0.878472f,0.000000f,-0.000000f,
                -0.819445f,0.000000f,0.350694f,
                -0.819444f,0.350694f,-0.000000f,
                -0.819444f,0.000000f,-0.350695f,
                -0.819444f,-0.350694f,-0.000000f,
                -0.763889f,-0.326389f,0.326389f,
                -0.763889f,0.326389f,0.326389f,
                -0.763889f,0.326389f,-0.326389f,
                -0.763889f,-0.326389f,-0.326389f,
                0.000000f,0.000000f,-0.878472f,
                0.350695f,0.000000f,-0.819444f,
                0.000000f,-0.350694f,-0.819444f,
                -0.350694f,0.000000f,-0.819444f,
                0.000000f,0.350694f,-0.819444f,
                0.326389f,0.326389f,-0.763889f,
                0.326389f,-0.326389f,-0.763889f,
                -0.326389f,-0.326389f,-0.763889f,
                -0.326389f,0.326389f,-0.763889f,
                0.598958f,-0.598958f,-0.302083f,
                0.643229f,-0.643229f,0.000000f,
                0.598958f,-0.598958f,0.302083f,
                0.302083f,-0.598958f,-0.598958f,
                0.000000f,-0.643229f,-0.643229f,
                -0.302083f,-0.598958f,-0.598958f,
                0.598958f,-0.302083f,-0.598958f,
                0.643229f,0.000000f,-0.643229f,
                0.598958f,0.302083f,-0.598958f,
                0.302083f,-0.598958f,0.598958f,
                -0.000000f,-0.643229f,0.643229f,
                -0.302083f,-0.598958f,0.598958f,
                0.598958f,-0.302083f,0.598958f,
                0.643229f,0.000000f,0.643229f,
                0.598958f,0.302083f,0.598959f,
                -0.598958f,-0.598958f,0.302083f,
                -0.643229f,-0.643229f,-0.000000f,
                -0.598958f,-0.598958f,-0.302084f,
                -0.598958f,-0.302083f,0.598958f,
                -0.643229f,0.000000f,0.643229f,
                -0.598959f,0.302083f,0.598958f,
                -0.598958f,-0.302083f,-0.598958f,
                -0.643229f,0.000000f,-0.643229f,
                -0.598958f,0.302083f,-0.598958f,
                0.598958f,0.598958f,-0.302083f,
                0.643229f,0.643229f,0.000000f,
                0.598958f,0.598958f,0.302084f,
                0.302083f,0.598958f,-0.598958f,
                0.000000f,0.643229f,-0.643229f,
                -0.302083f,0.598958f,-0.598958f,
                0.302083f,0.598958f,0.598959f,
                -0.000000f,0.643229f,0.643229f,
                -0.302084f,0.598958f,0.598958f,
                -0.598958f,0.598958f,0.302083f,
                -0.643229f,0.643229f,-0.000000f,
                -0.598958f,0.598958f,-0.302083f,
                0.509259f,-0.509259f,-0.509259f,
                0.509259f,-0.509259f,0.509259f,
                -0.509259f,-0.509259f,0.509259f,
                -0.509259f,-0.509259f,-0.509259f,
                0.509259f,0.509259f,-0.509259f,
                0.509259f,0.509259f,0.509260f,
                -0.509260f,0.509259f,0.509259f,
                -0.509259f,0.509259f,-0.509259f};
        //colors for each vertices
        float[] colors = {
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1,
                1,0,0,1,	1,0,0,1
        };
        //indeces for drawing the vertices in specified order
        short[] indeces = {0,4,5,
                1,5,54,
                4,58,57,
                5,57,90,
                0,1,6,
                2,6,63,
                1,55,56,
                6,56,91,
                0,2,7,
                3,7,69,
                2,64,65,
                7,65,92,
                0,3,8,
                4,8,59,
                3,70,71,
                8,71,93,
                9,13,14,
                10,14,81,
                13,79,78,
                14,78,94,
                9,10,15,
                11,15,89,
                10,82,83,
                15,83,97,
                9,11,16,
                12,16,86,
                11,88,87,
                16,87,96,
                9,12,17,
                13,17,80,
                12,85,84,
                17,84,95,
                18,22,23,
                19,23,60,
                22,55,54,
                23,54,90,
                18,19,24,
                20,24,78,
                19,61,62,
                24,62,94,
                18,20,25,
                21,25,68,
                20,79,80,
                25,80,95,
                18,21,26,
                22,26,56,
                21,67,66,
                26,66,91,
                27,31,32,
                28,32,66,
                31,64,63,
                32,63,91,
                27,28,33,
                29,33,84,
                28,67,68,
                33,68,95,
                27,29,34,
                30,34,74,
                29,85,86,
                34,86,96,
                27,30,35,
                31,35,65,
                30,73,72,
                35,72,92,
                36,40,41,
                37,41,72,
                40,70,69,
                41,69,92,
                36,37,42,
                38,42,87,
                37,73,74,
                42,74,96,
                36,38,43,
                39,43,77,
                38,88,89,
                43,89,97,
                36,39,44,
                40,44,71,
                39,76,75,
                44,75,93,
                45,49,50,
                46,50,62,
                49,82,81,
                50,81,94,
                45,46,51,
                47,51,57,
                46,61,60,
                51,60,90,
                45,47,52,
                48,52,75,
                47,58,59,
                52,59,93,
                45,48,53,
                49,53,83,
                48,76,77,
                53,77,97,
                1,0,5,
                55,1,54,
                5,4,57,
                54,5,90,
                2,0,6,
                64,2,63,
                6,1,56,
                63,6,91,
                3,0,7,
                70,3,69,
                7,2,65,
                69,7,92,
                4,0,8,
                58,4,59,
                8,3,71,
                59,8,93,
                10,9,14,
                82,10,81,
                14,13,78,
                81,14,94,
                11,9,15,
                88,11,89,
                15,10,83,
                89,15,97,
                12,9,16,
                85,12,86,
                16,11,87,
                86,16,96,
                13,9,17,
                79,13,80,
                17,12,84,
                80,17,95,
                19,18,23,
                61,19,60,
                23,22,54,
                60,23,90,
                20,18,24,
                79,20,78,
                24,19,62,
                78,24,94,
                21,18,25,
                67,21,68,
                25,20,80,
                68,25,95,
                22,18,26,
                55,22,56,
                26,21,66,
                56,26,91,
                28,27,32,
                67,28,66,
                32,31,63,
                66,32,91,
                29,27,33,
                85,29,84,
                33,28,68,
                84,33,95,
                30,27,34,
                73,30,74,
                34,29,86,
                74,34,96,
                31,27,35,
                64,31,65,
                35,30,72,
                65,35,92,
                37,36,41,
                73,37,72,
                41,40,69,
                72,41,92,
                38,36,42,
                88,38,87,
                42,37,74,
                87,42,96,
                39,36,43,
                76,39,77,
                43,38,89,
                77,43,97,
                40,36,44,
                70,40,71,
                44,39,75,
                71,44,93,
                46,45,50,
                61,46,62,
                50,49,81,
                62,50,94,
                47,45,51,
                58,47,57,
                51,46,60,
                57,51,90,
                48,45,52,
                76,48,75,
                52,47,59,
                75,52,93,
                49,45,53,
                82,49,83,
                53,48,77,
                83,53,97
        };
        cubeBuffer = null;
        colorBuffer = null;
        indexBuffer = null;

        cubeBuffer = ByteBuffer.allocateDirect(cube.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        cubeBuffer.put(cube);
        cubeBuffer.position(0);

        colorBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(cube);
        colorBuffer.position(0);

        indexBuffer = ByteBuffer.allocateDirect(indeces.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indeces);
        indexBuffer.position(0);
    }
    private int loadShader(int type, String source)  {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        return shader;
    }
    private String vertexShaderCode =
            "attribute vec4 vPosition;							\n"
                    +	"attribute vec4 vColor;								\n"
                    +	"varying vec4 vFinalColor;							\n"
                    +	"uniform mat4 u_VPMatrix;							\n"
                    +	"void main()										\n"
                    +	"{													\n"
                    +	" gl_Position = u_VPMatrix * vPosition;				\n"
                    +	" vFinalColor = vColor;								\n"
                    +	"}													\n";
    private String fragmentShaderCode =
            "#ifdef GL_FRAGMENT_PRECISION_HIGH					\n"
                    +	"precision highp float;								\n"
                    +	"#else												\n"
                    +	"precision mediump float;							\n"
                    +	"#endif												\n"
                    +	"varying vec4 vFinalColor;							\n"
                    +	"void main()										\n"
                    +	"{													\n"
                    +	" gl_FragColor = vFinalColor;						\n"
                    +	"}													\n";

}
