package com.example.smilehacks;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.media.FaceDetector;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class GalleryActivity extends Activity {

	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView imageView4;
	private ImageView imageView5;
	private ImageView imageView6;
	private ImageView imageView7;

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
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		imageView6 = (ImageView) findViewById(R.id.imageView6);
		imageView7 = (ImageView) findViewById(R.id.imageView7);
		
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
        canvas.drawColor(Color.TRANSPARENT);
         
        // Paintの作成
        
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        
        //元画像
        imageView1.setImageBitmap(bitmap);
        
        // キャンバスを変換する
        changeBitmap(bmp, 1);//右目右
        imageView2.setImageBitmap(bmp);
        
        changeBitmap(bmp, 2);//右目左
        imageView2.setImageBitmap(bmp);
        
        changeBitmap(bmp, 1);//左目右
        imageView2.setImageBitmap(bmp);
        
        changeBitmap(bmp, 2);//左目左
        imageView2.setImageBitmap(bmp);
        
        changeBitmap(bmp, 1);//口右
        imageView2.setImageBitmap(bmp);
        
        changeBitmap(bmp, 2);//口左
        imageView2.setImageBitmap(bmp);
        
        canvas.drawBitmapMesh(bitmap, 1, 1, vers, 0, null, 0, null);
        int size = container.size();
        
        
        /*矢野書き換え(START)*/
        System.out.println("変換はじまったっぽい");
		Bitmap bmp2 = bmp.copy(Bitmap.Config.RGB_565, true);
		FaceDetector.Face faces[] = new FaceDetector.Face[10];
		FaceDetector detector = new FaceDetector(bmp2.getWidth(),bmp2.getHeight(),faces.length);
		detector.findFaces(bmp2,faces);
		PointF point = new PointF();
		if(faces != null)
		for(FaceDetector.Face face : faces){
			try{
				
				face.getMidPoint(point);
				float fDist = face.eyesDistance();
				PointF LEye = new PointF((float)0.1*fDist+point.x,(float)-0.5*fDist+point.y);
				PointF REye = new PointF((float)-1*fDist+point.x,(float)-0.5*fDist+point.y);
				
				
				//canvas.drawRect((float) 5, fDist,point.x, point.y, paint);
				
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inJustDecodeBounds = true;
//				
//				BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(data.getData(),true); 
//				
//				Bitmap BitmapR = decorder.decodeEegion(new RectF(REye.x, REye.y, REye.x+fDist, REye.y+fDist),options);
//				Bitmap BitmapL = decorder.decodeEegion(new RectF(LEye.x, LEye.y, LEye.x+fDist, LEye.y+fDist),options);
//				
				
				
				
//				new RectF(REye.x, REye.y, REye.x+fDist, REye.y+fDist);
//				new RectF(LEye.x, LEye.y, LEye.x+fDist, LEye.y+fDist);
				
				System.out.println("x座標:"+point.x+"　y座標:"+point.y);
				System.out.println("x座標:"+LEye.x+"　y座標:"+point.y);
				System.out.println("FaceUP!!!!!!");
			}catch(Exception e){}
		}

        /*矢野書き換え(END)*/
        for(int i=0 ; i< size ; i++)
        {
           Circle c = container.get(i);
           canvas.drawCircle(c.x, c.y, c.radius, paint);
        }

	    
	}

	//flag==1の時右上とがり、flag==2の時左上とがり、表示するx,y座標の指定
	private void changeBitmap(Bitmap bitmap, int flag){ 
		
		
		// 画像の四隅に円を配置
	    Circle c1 = new Circle(25f, 10f, 10f);
	    container.add(c1);
	    Circle c2 = new Circle(25f, bitmap.getWidth()+10, 10f);
	    container.add(c2);
	    Circle c3 = new Circle(25f, 10f, bitmap.getHeight()+10);
	    container.add(c3);
	    Circle c4 = new Circle(25f, bitmap.getWidth()+10, bitmap.getHeight()+10);
	    container.add(c4);
	    
	    if(flag == 1){
	    	vers = new float[]{c1.x, c1.y+100, c2.x, c2.y-100, c3.x, c3.y+100, c4.x, c4.y-100};
	    }
	    else if(flag == 2){
	    	vers = new float[]{c1.x, c1.y-100, c2.x, c2.y+100, c3.x, c3.y-100, c4.x, c4.y+100};
	    }
	    

	    
		
	    
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
