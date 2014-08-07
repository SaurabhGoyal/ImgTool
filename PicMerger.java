import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class PicMerger {

	public static int count = 0;

	public BufferedImage getImage(String fileName) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(fileName));
			return image;
		} catch (Exception e) {
			System.err.println(fileName + " - not found");
			return null;
		}
	}

	public void setImage(BufferedImage image, String fileName) {
		File output = new File(fileName);
		try {
			ImageIO.write(image, "jpg", output);
		} catch (Exception e) {
			System.err.println("Image not generated");
		}
	}

	public int[][] getPixelRGB(BufferedImage inputImage) {
		int[][] rgbArray;
		int height = inputImage.getHeight();
		int width = inputImage.getWidth();
		rgbArray = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				rgbArray[i][j] = inputImage.getRGB(j, i);
			}
		}
		return rgbArray;
	}

	public int getAlphaValue(int pixel) {
		return ((pixel >> 12) & (0xFF));
	}

	public int getRedValue(int pixel) {
		return ((pixel >> 8) & (0xFF));
	}

	public int getGreenValue(int pixel) {
		return ((pixel >> 4) & (0xFF));
	}

	public int getBlueValue(int pixel) {
		return ((pixel >> 0) & (0xFF));
	}

	public void merge(String inputFile1, String inputFile2, String outputLocation) {
		BufferedImage inputImage1, inputImage2, outputImage;
		inputImage1 = getImage(inputFile1);
		inputImage2 = getImage(inputFile2);
		int height = inputImage1.getHeight();
		int width = inputImage1.getWidth();
		
		int[][] rgb_img2 = new int[height][width];
		rgb_img2 = getPixelRGB(inputImage2);
		outputImage = inputImage1;
		if (inputImage1.getWidth() != inputImage2.getWidth()) {
			System.err.println("given images have different resolutions, currently images of same resolution only are supported!!!");
			return;
		}
		for (int i = 0; i < height; i++) {
			int j = 0;
			if (i % 2 == 0)
				j = 1;
			for (; j < width - 2; j += 2) {
				outputImage.setRGB(j, i, rgb_img2[i][j]);
			}
		}
		setImage(outputImage,outputLocation);
		//System.out.println("Merge Complete");
	}

	public static void main(String[] args) {
		new PicMerger().merge("d://Pics//20130127_202816.jpg", "d://Pics//20130127_202823.jpg", "c://users//Saurabh-Goyal//desktop//output_merged.jpg");
	}
}
