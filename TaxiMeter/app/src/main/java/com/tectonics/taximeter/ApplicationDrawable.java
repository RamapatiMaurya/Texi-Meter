package com.tectonics.taximeter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ApplicationDrawable 
{
	int widthpixel;
	int heightpixel;
	Context context;

	public ApplicationDrawable(Context context, int widthPixels,int heightPixels) 
	{
		this.context=context;
		widthpixel=widthPixels;
		heightpixel=heightPixels;
	}

	public Drawable Button(int resource_Id) 
	{

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resource_Id);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float newWidth = (float) ((float) widthpixel / 6);
		float scaleWidth = ((float) newWidth) / width;
		float newHeight = (float) ((float) widthpixel / 8);
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();		
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,	matrix, true);
		Drawable d = new BitmapDrawable(context.getResources(), resizedBitmap);
		return d;
	}

	public Drawable ActionButton(int resource_Id) 
	{
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resource_Id);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float newWidth;		
		float newHeight;		
		if((height==64)||height==203)
		{			
			if(height==64)
			{
				newWidth = (float) widthpixel / 5;			
				newHeight = (float) ((float) widthpixel / 8);
			}
			else
			{
				newWidth = (float) widthpixel / 5;			
				newHeight = (float) ((float) widthpixel / 10.5);
			}
		}
		else
		{		
			if(height==38)
			{
				newWidth = (float) widthpixel / 5;
				newHeight = (float) ((float) widthpixel / 10);
			}
			else
			{
				newWidth = (float) widthpixel / 5;
				newHeight = (float) ((float) widthpixel / 13.5);
			}	
		}
		float scaleWidth = ((float) newWidth) / width;		
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,	matrix, true);
		Drawable d = new BitmapDrawable(context.getResources(), resizedBitmap);
		return d;
	}

}
