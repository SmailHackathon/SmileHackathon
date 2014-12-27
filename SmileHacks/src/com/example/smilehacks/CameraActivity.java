package com.example.smilehacks;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;

public class CameraActivity extends Activity {
	
	SurfaceView sur;
	SurfaceHolder surHold;
	Camera c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		//getUIparts
		final Button gallery = (Button) findViewById(R.id.gallery);
		final Button takePhoto = (Button) findViewById(R.id.takePhoto);
		
		StartCamera();
		gallery.setOnClickListener(new OnClickListener(){
			public void onClick(View v){startGalleryActivity();}
		});
		takePhoto.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(c != null){
					c.takePicture(null,null,new Camera.PictureCallback(){
						public void onPictureTaken(byte[] data, Camera camera){
							if(data == null) return;
							String savePath = Environment.getExternalStorageDirectory().getPath() + "Camera";
							System.out.println("これが、パスだ！！！"+savePath);
							File file = new File(savePath);
							if(!file.exists()) file.mkdir();
							Calendar cal = Calendar.getInstance();
				            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
				            String imgPath = savePath + "/" + "IMG_"
				                    + sdFormat.format(cal.getTime()) + ".jpg";
							
							try{
								FileOutputStream fos = new FileOutputStream(imgPath, true);
								//指定したフォルダにファイル
								fos.write(data);
								fos.close();
								
								ContentValues values = new ContentValues();
								values.put(Images.Media.MIME_TYPE,"image/jpeg");
								values.put("_data",imgPath);
								getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
								System.out.println("SaveComplete!!");
							}catch(Exception e){
								System.out.println("SaveFailed!!");
							}
							c.startPreview();
							
							
						}
					});
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startGalleryActivity(){
		Intent intent = new Intent(this,GalleryActivity.class);
		startActivity(intent);
	}
	private void StartCamera(){
		sur = (SurfaceView) findViewById(R.id.surfaceView1);
		surHold = sur.getHolder();
		surHold.addCallback( new SurfaceCallback() );
	}
	private class SurfaceCallback implements SurfaceHolder.Callback{
		
		
		List<Size> cSize;
		
		public void surfaceCreated(SurfaceHolder holder){
			c = Camera.open(0);
			c.setDisplayOrientation(90);
			if(c != null){
				try{
					c.setPreviewDisplay(surHold);
				}catch(Exception e){
					e.printStackTrace();
				}
				cSize = c.getParameters().getSupportedPreviewSizes();
			}
		}
		
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
			if(c != null){
				Parameters params = c.getParameters();
				if(cSize != null){
					params.setRotation(90);
					params.setPreviewSize(864,480);
					c.setParameters(params);
				}
				c.startPreview();
			}
		}
		public void surfaceDestroyed(SurfaceHolder arg0){
			c.stopPreview();
			c.release();
			c = null;
		}
	}
		
}
