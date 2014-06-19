package com.example.googlesearchimage;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImageResultAdapter extends android.widget.ArrayAdapter<ImageResult> {

	public ImageResultAdapter(Context context, List<ImageResult> results) {
		super(context, R.layout.image_item, results);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		SmartImageView imgView;
		if( convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			imgView = (SmartImageView) inflator.inflate(R.layout.image_item, parent, false);
		} else {
			imgView = (SmartImageView) convertView;
			imgView.setImageResource(android.R.color.transparent);
		}
		imgView.setImageUrl(imageInfo.getThumbUrl());
		return imgView;
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
	}
	
}
