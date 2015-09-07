package com.example.haveimgfun;

public class LibImgFun {
	static{
		System.loadLibrary("ImgFun");
	}

	public native void processImg(long matAddrInRGBA, long matAddroutInRGBA);
}