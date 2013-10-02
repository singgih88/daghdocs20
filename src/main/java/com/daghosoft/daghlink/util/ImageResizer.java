package com.daghosoft.daghlink.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.scheduling.annotation.Async;



public class ImageResizer {
	private interface Approach {
		public Image getScaledInstance(BufferedImage img, int w, int h);
	}

	private class Approach1 implements Approach {
		public Image getScaledInstance(BufferedImage img, int w, int h) {
			return ImageResizer.getScaledInstance(img, w, h,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, false);
		}
	}

	private class Approach2 implements Approach {
		public Image getScaledInstance(BufferedImage img, int w, int h) {
			return ImageResizer.getScaledInstance(img, w, h,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
		}
	}

	private class Approach3 implements Approach {
		public Image getScaledInstance(BufferedImage img, int w, int h) {
			return ImageResizer.getScaledInstance(img, w, h,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC, false);
		}
	}

	private class Approach4 implements Approach {
		public Image getScaledInstance(BufferedImage img, int w, int h) {
			Image ret = img.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
			return ret;
		}
	}

	private class Approach5 implements Approach {
		public Image getScaledInstance(BufferedImage img, int w, int h) {
			return ImageResizer.getScaledInstance(img, w, h,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
		}
	}

	/**
	 * BONUS: This approach uses a special "trilinear mipmapping" technique
	 * written by Jim Graham. Some developers may find the visual quality
	 * provided by this technique to be similar to SCALE_AREA_AVERAGING, but at
	 * a fraction of the performance cost (although visually slightly "fuzzier"
	 * perhaps).
	 */
	private class Approach6 implements Approach {
		public Image getScaledInstance(BufferedImage img, int targetWidth,
				int targetHeight) {
			// REMIND: This only works for opaque images...

			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			int iw = img.getWidth();
			int ih = img.getHeight();

			Object hint = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
			int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
					: BufferedImage.TYPE_INT_ARGB;

			// First get down to no more than 2x in W & H
			while (iw > targetWidth * 2 || ih > targetHeight * 2) {
				iw = (iw > targetWidth * 2) ? iw / 2 : iw;
				ih = (ih > targetHeight * 2) ? ih / 2 : ih;
				img = scaleImage(img, type, hint, iw, ih);
			}

			// REMIND: Conservative approach:
			// first get W right, then worry about H

			// If still too wide - do a horizontal trilinear blend
			// of img and a half-width img
			if (iw > targetWidth) {
				int iw2 = iw / 2;
				BufferedImage img2 = scaleImage(img, type, hint, iw2, ih);
				if (iw2 < targetWidth) {
					img = scaleImage(img, type, hint, targetWidth, ih);
					img2 = scaleImage(img2, type, hint, targetWidth, ih);
					interp(img2, img, iw - targetWidth, targetWidth - iw2);
				}
				img = img2;
				iw = targetWidth;
			}
			// iw should now be targetWidth or smaller

			// If still too tall - do a vertical trilinear blend
			// of img and a half-height img
			if (ih > targetHeight) {
				int ih2 = ih / 2;
				BufferedImage img2 = scaleImage(img, type, hint, iw, ih2);
				if (ih2 < targetHeight) {
					img = scaleImage(img, type, hint, iw, targetHeight);
					img2 = scaleImage(img2, type, hint, iw, targetHeight);
					interp(img2, img, ih - targetHeight, targetHeight - ih2);
				}
				img = img2;
				ih = targetHeight;
			}
			// ih should now be targetHeight or smaller

			// If we are too small, then it was probably because one of
			// the dimensions was too small from the start.
			if (iw < targetWidth && ih < targetHeight) {
				img = scaleImage(img, type, hint, targetWidth, targetHeight);
			}

			return img;
		}

		private BufferedImage scaleImage(BufferedImage orig, int type,
				Object hint, int w, int h) {
			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(orig, 0, 0, w, h, null);
			g2.dispose();
			return tmp;
		}

		private void interp(BufferedImage img1, BufferedImage img2,
				int weight1, int weight2) {
			float alpha = weight1;
			alpha /= (weight1 + weight2);
			Graphics2D g2 = img1.createGraphics();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					alpha));
			g2.drawImage(img2, 0, 0, null);
			g2.dispose();
		}
	}

	/**
	 * Convenience method that returns a scaled instance of the provided
	 * {@code BufferedImage}.
	 * 
	 * @param img
	 *            the original image to be scaled
	 * @param targetWidth
	 *            the desired width of the scaled instance, in pixels
	 * @param targetHeight
	 *            the desired height of the scaled instance, in pixels
	 * @param hint
	 *            one of the rendering hints that corresponds to
	 *            {@code RenderingHints.KEY_INTERPOLATION} (e.g.
	 *            {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
	 *            {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
	 *            {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
	 * @param higherQuality
	 *            if true, this method will use a multi-step scaling technique
	 *            that provides higher quality than the usual one-step technique
	 *            (only useful in down-scaling cases, where {@code targetWidth}
	 *            or {@code targetHeight} is smaller than the original
	 *            dimensions, and generally only when the {@code BILINEAR} hint
	 *            is specified)
	 * @return a scaled version of the original {@codey BufferedImage}
	 */
	public static BufferedImage getScaledInstance(BufferedImage img,
			int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage) img;
		if (targetHeight == 0) {
			targetHeight = img.getHeight() * targetWidth / img.getWidth();
		}
		if (targetWidth == 0) {
			targetWidth = img.getWidth() * targetHeight / img.getHeight();
		}
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

	/*
	 * public static BufferedImage getScaledInstance(BufferedImage img, int
	 * targetWidth, int targetHeight, int approach){ String approachClassName =
	 * "Approach"+approach; Approach a = (Approach)
	 * Class.forName(approachClassName).newInstance(); return new
	 * BufferedImage(a.getScaledInstance(img, targetWidth, targetHeight)); }
	 */
	@Async
	public static void scale(File input, File output, int targetWidth,
			int targetHeight, Object hint, boolean higherQuality,String mime) {
		BufferedImage in;
	
		try {
			in = ImageIO.read(input);
			
			if( targetHeight>0 && targetWidth>0){
				if (in.getHeight() > in.getWidth() && targetHeight>0){	
					targetWidth=0 ;
				}else{
					targetHeight=0;	
				}
			}
			
						
			if (targetWidth > in.getWidth() || targetHeight >in.getHeight()){
				targetWidth=in.getWidth() ;
				targetHeight=in.getHeight();
			}
			
			
			BufferedImage out = getScaledInstance(in, targetWidth,targetHeight, hint, higherQuality);
			ImageIO.write(out, mime, output);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void scale(InputStream inputStream,
			OutputStream outputStream, int targetWidth, int targetHeight,
			Object hint, boolean higherQuality) {
		BufferedImage input;
		try {
			input = ImageIO.read(inputStream);     
			
			BufferedImage output = getScaledInstance(input, targetWidth,
					targetHeight, hint, higherQuality);
			ImageIO.write(output, "jpg", outputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void scale(InputStream inputStream,
			OutputStream outputStream, int targetWidth, int targetHeight,
			Object hint, boolean higherQuality,String mime) {
		BufferedImage in;
		try {
			in = ImageIO.read(inputStream);
			
			if (targetWidth > in.getWidth() || targetHeight >in.getHeight()){
				targetWidth=in.getWidth() ;
				targetHeight=in.getHeight();
			}
			BufferedImage output = getScaledInstance(in, targetWidth,
					targetHeight, hint, higherQuality);
			ImageIO.write(output, mime, outputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}