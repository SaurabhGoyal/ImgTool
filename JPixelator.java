import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class JPixelator {

	BufferedImage inputImage, outputImage;
	int[][] pixelRGB;

	public JPixelator(String fileName, int pixLevel, String outputLocation) {
		try {
			inputImage = ImageIO.read(new File(fileName));
		} catch (Exception e) {
			System.err.println("Image Not Found");
		}
		int width = inputImage.getHeight();
		int height = inputImage.getWidth();
		pixelRGB = new int[height][width];
		//System.out.println("Reading image");
		for (int i = 0; i < height - pixLevel; i += pixLevel) {
			for (int j = 0; j < width - pixLevel; j += pixLevel) {
				pixelRGB[i][j] = inputImage.getRGB(i, j);
				int red = (pixelRGB[i][j] >> 8) & (0xFF);
				int green = (pixelRGB[i][j] >> 4) & (0xFF);
				int blue = (pixelRGB[i][j] >> 0) & (0xFF);
			}
		}
		//System.out.println("Image read successfully");
		outputImage = inputImage;
		//System.out.println("starting pixelation");
		for (int i = 0; i < height - pixLevel; i += pixLevel) {
			for (int j = 0; j < width - pixLevel; j += pixLevel) {
				pixelate(pixelRGB[i][j], pixLevel, i, j);
			}
		}
		File op = new File(outputLocation);
		try {
			ImageIO.write(outputImage, "jpg", op);
			System.out.println("Pixelated image generated");
		} catch (Exception e) {
			System.err.println("Image not generated");
		}

	}

	public void pixelate(int pixel, int pixLevel, int x, int y) {
		for (int i = 0; i < pixLevel; i++) {
			for (int j = 0; j < pixLevel; j++) {
				pixelRGB[x + i][y + j] = pixel;
				outputImage.setRGB(x + i, y + j, pixelRGB[x + i][y + j]);
			}
			//System.out.println("row " + (x + i + 1) + " pixelated");
		}

	}

	public static void main(String[] args) {
		new JPixelator("d://wallpapers//source.jpg", 15,"d://source_pixelated.jpg");
	}

}
