package com.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.model.Product;
import com.repository.ProductRepository;
import com.service.ProductService;

@Service
public class ProductServiceimpl implements ProductService {

	@Autowired
	ProductRepository qrListRepository;

	@Override
	public List<Product> getAll() {
		return qrListRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	public void generateQRcode(String data, String path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> map, int h, int w)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, w, h);
		MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
	}

	public void gen(String str, String name) throws WriterException, IOException, NotFoundException {
		String path = "./src/main/resources/static/qrcode/" + name + ".png"; 
		String charset = "UTF-8";
		Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
		hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		generateQRcode(str, path, charset, hashMap, 200, 200);
		System.out.println("QR Code created successfully.");
	}

	public byte[] getBarCodeImage() throws WriterException, IOException {
		String text = "Git Valley technologies";
		int width = 100;
		int height = 100;
		HashMap<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		Writer writer = new Code128Writer();
		BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.CODE_128, width, height);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
		System.out.println("barcode successfully");
		System.out.println(byteArrayOutputStream.toByteArray());
		return byteArrayOutputStream.toByteArray();
	}
}
