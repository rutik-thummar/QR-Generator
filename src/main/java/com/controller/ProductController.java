package com.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.model.Product;
import com.repository.ProductRepository;
import com.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService qrService;

	@Autowired
	ProductRepository qrListRepository;

	@RequestMapping("/")
	public String viewUser(Model model) {
		List<Product> list = qrService.getAll();
		model.addAttribute("list", list);
		return "home";
	}

	@RequestMapping("/insert")
	public String insert() {
		return "insert";
	}

	@PostMapping("/insertdata")
	public RedirectView updateForm(@ModelAttribute Product qrList)
			throws NotFoundException, WriterException, IOException {
		qrService.gen(qrList.getData(), qrList.getName());
		// qrService.getBarCodeImage();
		Product add = new Product();
		add.setName(qrList.getName());
		add.setData(qrList.getData());
		add.setQr(qrList.getName() + ".png");
		qrListRepository.save(add);
		return new RedirectView("/");
	}
}
