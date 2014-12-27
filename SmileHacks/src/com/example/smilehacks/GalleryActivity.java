package com.example.smilehacks;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.MotionEvent;
import android.widget.ImageView;

public class GalleryActivity extends Activity {

	private ImageView imageView;
	private Bitmap bitmap;
	private Bitmap bmp;
	private ArrayList<Circle> container = new ArrayList<Circle>();
	private float[] vers;
	private Canvas canvas;
	private Paint paint;
	private int isDrag = -1;
	
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
		
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		
		bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// Canvasの作成:描画先のBitmapを与える
        
        canvas = new Canvas(bmp);
        canvas.drawColor(Color.BLACK);
         
        // Paintの作成
        
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
 
        // キャンバスに円を描画する
        changeBitmap(bmp);
        
        imageView.setImageBitmap(bmp);
        canvas.drawBitmapMesh(bitmap, 1, 1, vers, 0, null, 0, null);
        //canvas.drawCircle(50, 50, 100, paint);
        int size = container.size();
        for(int i=0 ; i< size ; i++)
        {
           Circle c = container.get(i);
           canvas.drawCircle(c.x, c.y, c.radius, paint);
        }

	    
	}

	private void changeBitmap(Bitmap bitmap){
		
		
		
		// 画像の四隅に円を配置
	    Circle c1 = new Circle(25f, 10f, 10f);
	    container.add(c1);
	    Circle c2 = new Circle(25f, bitmap.getWidth()+10, 10f);
	    container.add(c2);
	    Circle c3 = new Circle(25f, 10f, bitmap.getHeight()+10);
	    container.add(c3);
	    Circle c4 = new Circle(25f, bitmap.getWidth()+10, bitmap.getHeight()+10);
	    container.add(c4);
	    
	    vers = new float[]{c1.x + 100, c1.y + 100, c2.x, c2.y, c3.x, c3.y, c4.x, c4.y};
		
		
	    
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
