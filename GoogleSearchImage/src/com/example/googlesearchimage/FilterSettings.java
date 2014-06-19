package com.example.googlesearchimage;

import java.io.Serializable;

public class FilterSettings implements Serializable {
	private static final long serialVersionUID = -7598622630840432360L;
	private String imgSize;
	private String imgColor;
	private String siteFilter;
	private String imgType;
	
	public FilterSettings(String size, String color, String type, String filter) {
		this.imgSize = size;
		this.imgColor = color;
		this.imgType = type;
		this.siteFilter = filter;
	}
	
	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}
	
	public String getImgSize() {
		return imgSize;
	}
	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}
	public String getImgColor() {
		return imgColor;
	}
	public void setImgColor(String imgColor) {
		this.imgColor = imgColor;
	}
	public String getSiteFilter() {
		return siteFilter;
	}
	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
	
	@Override
	public String toString() {
		return "FilterSettings [imgSize=" + imgSize + ", imgColor=" + imgColor
				+ ", siteFilter=" + siteFilter + "]";
	}

}
