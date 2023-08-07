package com.service;

import java.io.IOException;
import java.util.List;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.model.Product;

public interface ProductService {
	
	List<Product> getAll();

	void gen(String data,String name) throws WriterException, IOException, NotFoundException;
	
	public byte[] getBarCodeImage() throws WriterException, IOException;
}
