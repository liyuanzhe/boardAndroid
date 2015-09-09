package com.example.haveimgfun;

import java.io.File;

import org.opencv.android.BaseLoaderCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//opencv
import org.opencv.android.LoaderCallbackInterface;  
import org.opencv.android.OpenCVLoader;  
import org.opencv.android.Utils;  
import org.opencv.core.Core;
import org.opencv.core.Mat;  
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import com.example.haveimgfun.R;


public class MainActivity extends Activity {
	
	private static final int TAKE_PICTURE = 0;
	
	private Button button,buttonShot;
	private ImageView image; 
	private Bitmap bmp;
	private TextView text;
	  
	private Uri outputFileUri;
	
	//Opencvº”‘ÿ
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this){
		@Override
		public void onManagerConnected(int status) {
			switch (status) {  
				case LoaderCallbackInterface.SUCCESS:{ 
					System.out.println("load opencv sucess");
				} break;  
				default:{  
					super.onManagerConnected(status);  
				} break;  
			}  
		}
	};
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		image = (ImageView)findViewById(R.id.ImageView);
		button = (Button)findViewById(R.id.button_process);
		text = (TextView)findViewById(R.id.text);
		buttonShot = (Button)findViewById(R.id.button_shot);
		
		//set button listener
		button.setOnClickListener(new ButtonListener());
		buttonShot.setOnClickListener(new ButtonListener());
		
	}
	
	
	//get photo
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		 if (requestCode == TAKE_PICTURE) {
			 
			 try{
				 Log.i("TAG","Uri path: "+outputFileUri.getPath());
				 bmp = BitmapFactory.decodeFile(outputFileUri.getPath(),null);
				 image.setImageBitmap(bmp);
				 Log.i("TAG","bmp is loaded!");
			 }catch(Exception e){
				 Log.i("Tag","exception");
				 Log.i("Tag","Uri path: "+outputFileUri.getPath());
			 }
		 }
	}
	
	
	//image process
	class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			switch(v.getId()){
			//process image
			case R.id.button_process:
				Mat rgbMat = new Mat();
				Utils.bitmapToMat(bmp, rgbMat); 
				text.setText("row: "+rgbMat.rows()+" col:"+rgbMat.cols()+" channels: "+rgbMat.channels());
				
				//c++ process
				LibImgFun libimagefun = new LibImgFun();
				String tmp = libimagefun.processImg(rgbMat.getNativeObjAddr()); //tmp="count;line;"
			    
				//string split
				String [] strs = tmp.split("[;]"); 
			    int count = Integer.parseInt(strs[0]);
			    int column = Integer.parseInt(strs[1]);
			    Log.i("TAG","count:"+count+" colum:"+column);
				text.setText(tmp);
				
				//draw line
				Core.line(rgbMat, new Point(column,0), new Point(column,rgbMat.height()), new Scalar(0,0,255),3);
				Utils.matToBitmap(rgbMat, bmp);
				image.setImageBitmap(bmp);
	            //release mat
	            rgbMat.release(); ;
	        //take photo    
			case R.id.button_shot:
				File file = new File(Environment.getExternalStorageDirectory(),"shot.jpg");
				outputFileUri = Uri.fromFile(file);
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				
				startActivityForResult(intent,TAKE_PICTURE);	//mark TAKE_PICTURE
				break;
				
			default:
				break;
			}
			  
		}
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
	}

}
