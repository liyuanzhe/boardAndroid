package com.example.haveimgfun;

public class LibImgFun {
	static{
		System.loadLibrary("ImgFun");
	}

	public native String processImg(long matAddrInRGBA);
}