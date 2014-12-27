package com.example.smilehacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class GalleryActivity extends Activity {

	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		Intent i = new Intent(
	    Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);  
		startActivityForResult(i, 0); 	
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
	}


	
}
