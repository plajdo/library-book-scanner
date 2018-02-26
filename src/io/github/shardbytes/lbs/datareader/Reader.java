package io.github.shardbytes.lbs.datareader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.aztec.AztecReader;

public class Reader {
	
	public static String readQR(String fileName){
		File file = new File(fileName);
		BufferedImage image = null;
		BinaryBitmap bitmap = null;
		Result result = null;
		
		try{
			image = ImageIO.read(file);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
			bitmap = new BinaryBitmap(new HybridBinarizer(source));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(bitmap == null){
			return null;
		}
		
		QRCodeReader reader = new QRCodeReader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(ChecksumException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}

	public static String readQR(BufferedImage image){
		BinaryBitmap bitmap = null;
		Result result = null;

		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
		bitmap = new BinaryBitmap(new HybridBinarizer(source));

		QRCodeReader reader = new QRCodeReader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(ChecksumException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}
	
	public static String readAztec(String fileName){
		File file = new File(fileName);
		BufferedImage image = null;
		BinaryBitmap bitmap = null;
		Result result = null;
		
		try{
			image = ImageIO.read(file);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
			bitmap = new BinaryBitmap(new HybridBinarizer(source));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(bitmap == null){
			return null;
		}
		
		AztecReader reader = new AztecReader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}
	
	public static String readAztec(BufferedImage image){
		BinaryBitmap bitmap = null;
		Result result = null;

		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
		bitmap = new BinaryBitmap(new HybridBinarizer(source));

		AztecReader reader = new AztecReader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}
	
	public static String readDataMatrix(String fileName){
		File file = new File(fileName);
		BufferedImage image = null;
		BinaryBitmap bitmap = null;
		Result result = null;
		
		try{
			image = ImageIO.read(file);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
			bitmap = new BinaryBitmap(new HybridBinarizer(source));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(bitmap == null){
			return null;
		}
		
		DataMatrixReader reader = new DataMatrixReader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(ChecksumException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}

	public static String readDataMatrix(BufferedImage image){
		BinaryBitmap bitmap = null;
		Result result = null;

		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
		bitmap = new BinaryBitmap(new HybridBinarizer(source));

		DataMatrixReader reader = new DataMatrixReader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(ChecksumException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}
	
	public static String read128(String fileName){
		File file = new File(fileName);
		BufferedImage image = null;
		BinaryBitmap bitmap = null;
		Result result = null;
		
		try{
			image = ImageIO.read(file);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
			bitmap = new BinaryBitmap(new HybridBinarizer(source));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		if(bitmap == null){
			return null;
		}
		
		Code128Reader reader = new Code128Reader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}

	public static String read128(BufferedImage image){
		BinaryBitmap bitmap = null;
		Result result = null;

		int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
		bitmap = new BinaryBitmap(new HybridBinarizer(source));

		Code128Reader reader = new Code128Reader();
		try{
			result = reader.decode(bitmap);
			return result.getText();
		}catch(NotFoundException e){
			return null;
		}catch(FormatException e){
			return null;
		}
		
	}
	
}
