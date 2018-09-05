package com.ranhfun.util.services;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.tapestry5.services.Response;

public class WaterServiceImpl implements WaterService {

	public BufferedImage mark(File file, InputStream waterStream, float alpha) throws IOException {
		BufferedImage imageBuff = ImageIO.read(file);
		return mark(imageBuff, waterStream, alpha);
	}

	public void mark(File file, InputStream waterStream, float alpha, Response response) throws IOException {
		BufferedImage imageBuff = ImageIO.read(file);
		mark(imageBuff, waterStream, alpha, response);
	}

	public BufferedImage mark(BufferedImage imageBuff, InputStream waterStream, float alpha) throws IOException {
		Graphics2D g = imageBuff.createGraphics();
		Image waterImage = ImageIO.read(waterStream);    // 水印文件
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g.drawImage(waterImage, 0, 0, imageBuff.getWidth(), imageBuff.getHeight(), null);
		g.dispose();
		return imageBuff;
	}

	@Override
	public void mark(BufferedImage imageBuff, InputStream waterStream, float alpha, Response response)
			throws IOException {
		response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.setHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
		OutputStream stream = response.getOutputStream("image/jpeg");
		ImageIO.write(mark(imageBuff, waterStream, alpha), "jpg", stream);
        stream.flush();
        stream.close();
	}
	
}
