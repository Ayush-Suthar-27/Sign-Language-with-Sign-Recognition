package com.example.signswift;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class objectDetectorClass {

    private Interpreter interpreter;
    private Interpreter interpreter2;
    private List<String> labelList;
    private int INPUT_SIZE;
    private int PIXEL_SIZE=3;
    private int IMAGE_MEAN=0;
    private float IMAGE_STD=255.0f;
    private GpuDelegate gpuDelegate;
    private int height=0;
    private int width=0;
    private int Classification_Input_Size = 0;

    objectDetectorClass(AssetManager assetManager,String modelPath,String labelPath,int inputSize,String classification_model,int classification_input_size) throws IOException{
        INPUT_SIZE=inputSize;
        Classification_Input_Size = classification_input_size;
        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4);
        interpreter=new Interpreter(loadModelFile(assetManager,modelPath),options);
        labelList= loadLabelList(assetManager,labelPath);

        Interpreter.Options options2 = new Interpreter.Options();
        options2.setNumThreads(2);
        interpreter2 = new Interpreter(loadModelFile(assetManager,classification_model),options2);
    }

    private List<String> loadLabelList(AssetManager assetManager, String labelPath) throws IOException{
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(labelPath)));
        String line;
        while ((line= reader.readLine())!=null){
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor =assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength= fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

    public Mat recognizeImage(Mat mat_image){
        Mat rotated_mat_image = new Mat();

        Mat a=mat_image.t();
        Core.flip(a,rotated_mat_image,1);
        a.release();

        Bitmap bitmap = null;
        bitmap = Bitmap.createBitmap(rotated_mat_image.cols(),rotated_mat_image.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rotated_mat_image,bitmap);
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,INPUT_SIZE,INPUT_SIZE,false);
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);
        //float[][][]result = new float[1][10][4];
        Object[] input = new Object[1];
        input[0] = byteBuffer;
        Map<Integer,Object> output_map = new TreeMap<>();
        float[][][]boxes = new float[1][10][4];
        float[][]scores = new float[1][10];
        float[][]classes = new float[1][10];

        output_map.put(0,boxes);
        output_map.put(1,classes);
        output_map.put(2,scores);

        interpreter.runForMultipleInputsOutputs(input,output_map);

        Object value = output_map.get(0);
        Object Object_class = output_map.get(1);
        Object score = output_map.get(2);

        for (int i=0;i<10;i++){
            float class_value=(float) Array.get(Array.get(Object_class,0),i);
            float score_value=(float) Array.get(Array.get(score,0),i);
            if (score_value>0.5){
                Object box1 = Array.get(Array.get(value,0),i);

                float y1 = (float) Array.get(box1,0)*height;
                float x1 = (float) Array.get(box1,1)*width;
                float y2 = (float) Array.get(box1,2)*height;
                float x2 = (float) Array.get(box1,3)*width;

                if(y1<0){
                    y1=0;
                }
                if(x1<0){
                    x1=0;
                }
                if(x2>width){
                    x2=width;
                }
                if(y2>height){
                    y2=height;
                }

                float w1=x2-x1;
                float h1=y2-y1;

                Rect cropped_roi = new Rect((int)x1,(int)y1,(int)w1,(int)h1);
                Mat cropped=new Mat(rotated_mat_image,cropped_roi).clone();
                Bitmap bitmap1;
                bitmap1=Bitmap.createBitmap(cropped.cols(),cropped.rows(),Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(cropped,bitmap1);
                Bitmap scaledBitmap1= Bitmap.createScaledBitmap(bitmap1,Classification_Input_Size,Classification_Input_Size,false);
                ByteBuffer byteBuffer1= convertBitmapToByteBuffer1(scaledBitmap1);
                float[][] output_class_value=new float[1][1];
                interpreter2.run(byteBuffer1,output_class_value);
                Log.d("objectDetectorClass","output_class_value"+ output_class_value[0][0]);
                String sign_val=get_alphabets(output_class_value[0][0]);
                Imgproc.putText(rotated_mat_image," "+sign_val,new Point(x1+10,y1+40),2,1.5,new Scalar(255,255,255,255),2);

                Imgproc.rectangle(rotated_mat_image,new Point(x1,y1),new Point(x2,y2),new Scalar(255,155,155),2);
                //Imgproc.putText(rotated_mat_image,labelList.get((int) class_value),new Point(left,top),3,1,new Scalar(100,100,100),2);

            }
        }

        Mat b = rotated_mat_image.t();
        Core.flip(b,mat_image,0);
        b.release();

        return mat_image;
    }

    private String get_alphabets(float sig_v) {
        String val="";
        if(sig_v>=-0.5 & sig_v<0.5){
            val="A";
        } else if (sig_v>=0.5 & sig_v<1.5) {
            val="B";
        }else if (sig_v>=1.5 & sig_v<2.5) {
            val="C";
        }else if (sig_v>=2.5 & sig_v<3.5) {
            val="D";
        }else if (sig_v>=3.5 & sig_v<4.5) {
            val="E";
        }else if (sig_v>=4.5 & sig_v<5.5) {
            val="F";
        }else if (sig_v>=5.5 & sig_v<6.5) {
            val="G";
        }else if (sig_v>=6.5 & sig_v<7.5) {
            val="H";
        }else if (sig_v>=7.5 & sig_v<8.5) {
            val="I";
        }else if (sig_v>=8.5 & sig_v<9.5) {
            val="J";
        }else if (sig_v>=9.5 & sig_v<10.5) {
            val="K";
        }else if (sig_v>=10.5 & sig_v<11.5) {
            val="L";
        }else if (sig_v>=11.5 & sig_v<12.5) {
            val="M";
        }else if (sig_v>=12.5 & sig_v<13.5) {
            val="N";
        }else if (sig_v>=13.5 & sig_v<14.5) {
            val="O";
        }else if (sig_v>=14.5 & sig_v<15.5) {
            val="P";
        }else if (sig_v>=15.5 & sig_v<16.5) {
            val="Q";
        }else if (sig_v>=16.5 & sig_v<17.5) {
            val="R";
        }else if (sig_v>=17.5 & sig_v<18.5) {
            val="S";
        }else if (sig_v>=18.5 & sig_v<19.5) {
            val="T";
        }else if (sig_v>=19.5 & sig_v<20.5) {
            val="U";
        }else if (sig_v>=20.5 & sig_v<21.5) {
            val="V";
        }else if (sig_v>=21.5 & sig_v<22.5) {
            val="W";
        }else if (sig_v>=22.5 & sig_v<23.5) {
            val="X";
        }else {
            val="Y";
        }
        return val;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        int quant = 0;
        int size_images = INPUT_SIZE;
        if (quant == 0){
            byteBuffer = ByteBuffer.allocateDirect(1*size_images*size_images*3);
        }
        else {
            byteBuffer = ByteBuffer.allocateDirect(4*1*size_images*size_images*3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[size_images*size_images];
        bitmap.getPixels(intValues,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        int pixel = 0;
        for(int i=0;i<size_images;++i){
            for (int j=0;j<size_images;++j){
                final int val=intValues[pixel++];
                if (quant==0){
                    byteBuffer.put((byte) ((val>>16)&0xFF));
                    byteBuffer.put((byte) ((val>>8)&0xFF));
                    byteBuffer.put((byte) (val&0xFF));
                }
                else {
                    byteBuffer.putFloat((((val>>16)&0xFF))/255.0f);
                    byteBuffer.putFloat((((val>>8)&0xFF))/255.0f);
                    byteBuffer.putFloat((((val)&0xFF))/255.0f);
                }
            }
        }
        return byteBuffer;
    }

    private ByteBuffer convertBitmapToByteBuffer1(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        int quant = 1;
        int size_images = Classification_Input_Size;
        if (quant == 0){
            byteBuffer = ByteBuffer.allocateDirect(1*size_images*size_images*3);
        }
        else {
            byteBuffer = ByteBuffer.allocateDirect(4*1*size_images*size_images*3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[size_images*size_images];
        bitmap.getPixels(intValues,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        int pixel = 0;
        for(int i=0;i<size_images;++i){
            for (int j=0;j<size_images;++j){
                final int val=intValues[pixel++];
                if (quant==0){
                    byteBuffer.put((byte) ((val>>16)&0xFF));
                    byteBuffer.put((byte) ((val>>8)&0xFF));
                    byteBuffer.put((byte) (val&0xFF));
                }
                else {
                    byteBuffer.putFloat((((val>>16)&0xFF)));
                    byteBuffer.putFloat((((val>>8)&0xFF)));
                    byteBuffer.putFloat((((val)&0xFF)));
                }
            }
        }
        return byteBuffer;
    }

}
