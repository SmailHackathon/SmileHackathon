package com.example.smilehacks;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.widget.ImageView;

public class GalleryActivity extends Activity {

	private ImageView imageView;
	private Bitmap bitmap;
	private ArrayList<Circle> container = new ArrayList<Circle>();
	private float[] vers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		imageView = (ImageView) findViewById(R.id.imageView1);
		Intent i = new Intent(
	    Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);  
		startActivityForResult(i, 0); 	
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
	
		try {
			InputStream in = getContentResolver().openInputStream(data.getData());
			bitmap = BitmapFactory.decodeStream(in);
			in.close();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		
		changeBitmap(bitmap);
		
	    imageView.setImageBitmap(bitmap);
	}

	private void changeBitmap(Bitmap bitmap){
		
		// 画像の四隅に円を配置
	    Circle c1 = new Circle(25, 10, 10);
	    container.add(c1);
	    Circle c2 = new Circle(25, bitmap.getWidth()+10, 10);
	    container.add(c2);
	    Circle c3 = new Circle(25, 10, bitmap.getHeight()+10);
	    container.add(c3);
	    Circle c4 = new Circle(25, bitmap.getWidth()+10, bitmap.getHeight()+10);
	    container.add(c4);
	    
	    vers = new float[]{c1.x, c1.y, c2.x, c2.y, c3.x, c3.y, c4.x, c4.y};
		
	    
		//return bitmap;
	}
	
	private class Circle
	{
	   public float radius;
	   public float x;
	   public float y;
	    
	   public Circle(float radius, float x, float y)
	   {
	      this.radius = radius;
	      this.x = x;
	      this.y = y;
	   }
	}

	
	
}
