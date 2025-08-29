package com.bootcamp.quickdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bootcamp.quickdemo.colors.ColorPrinter;
import com.bootcamp.quickdemo.colors.ColorPrinterImpl;
import com.bootcamp.quickdemo.config.PizzaConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class QuickdemoApplication {
	private static final Logger log = LoggerFactory.getLogger(QuickdemoApplication.class);

	public static void main(String[] args) {
		var context = SpringApplication.run(QuickdemoApplication.class, args);

		// Run color printer
		ColorPrinter colorPrinter = new ColorPrinterImpl();
		System.out.println(colorPrinter.printInColor());

		// Pizza config
		PizzaConfig pizzaConfig = context.getBean(PizzaConfig.class);
		pizzaConfig.setCrust("Stuffed");
		pizzaConfig.setToppings("Mushrooms");
		pizzaConfig.setSauce("Pesto");

		log.info(String.format(
			"\n I want a pizza with %s crust, %s toppings, and %s sauce",
			pizzaConfig.getCrust(),
			pizzaConfig.getToppings(),
			pizzaConfig.getSauce()
		));
	}
}
