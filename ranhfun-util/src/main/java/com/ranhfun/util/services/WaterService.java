package com.ranhfun.util.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.services.Response;

public interface WaterService {

	public BufferedImage mark(File file, InputStream waterStream, float alpha) throws IOException;
	
	public void mark(File file, InputStream waterStream, float alpha, Response response) throws IOException;
	
	public BufferedImage mark(BufferedImage imageBuff, InputStream waterStream, float alpha) throws IOException;
	
	public void mark(BufferedImage imageBuff, InputStream waterStream, float alpha, Response response) throws IOException;
	
}
