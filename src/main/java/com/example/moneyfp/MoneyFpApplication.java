package com.example.moneyfp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class MoneyFpApplication implements CommandLineRunner  {

	private final FpApplication fpApplication;

	MoneyFpApplication(FpApplication fpApplication) {
		this.fpApplication = fpApplication;
	}

	public static void main(String[] args) {
		SpringApplication.run(MoneyFpApplication.class, args);
	}

	@Override
	public void run(String... args) {
		BigDecimal listingPrice = new BigDecimal(100);
		System.out.println(fpApplication.getClass().getName());
		System.out.println(fpApplication.run(listingPrice));
	}
}
