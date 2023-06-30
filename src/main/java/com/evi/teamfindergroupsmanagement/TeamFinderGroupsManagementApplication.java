package com.evi.teamfindergroupsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TeamFinderGroupsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamFinderGroupsManagementApplication.class, args);
	}

}
