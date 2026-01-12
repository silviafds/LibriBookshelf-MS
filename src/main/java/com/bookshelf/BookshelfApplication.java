package com.bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookshelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshelfApplication.class, args);
	}

}
