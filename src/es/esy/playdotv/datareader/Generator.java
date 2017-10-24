package es.esy.playdotv.datareader;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Generator {
	public static BufferedImage writeQRCode(String text){
		QRCodeWriter writer = new QRCodeWriter();
		int w = 512;
		int h = 512;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int white = 255 << 16 | 255 << 8 | 255;
		int black = 0;
		try{
			BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, w, h);
			for(int i = 0; i < w; i++){
				for(int j = 0; j < h; j++){
					image.setRGB(i, j, bitMatrix.get(i, j) ? black : white);
				}
				
			}
			return image;
			
		}catch(WriterException e){
			e.printStackTrace();
		}
		return null;
		
	}
	
}
