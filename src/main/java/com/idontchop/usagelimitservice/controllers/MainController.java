package com.idontchop.usagelimitservice.controllers;

import java.net.NetworkInterface;
import java.net.SocketException;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idontchop.usagelimitservice.dtos.RestMessage;
import com.idontchop.usagelimitservice.entities.UsageType;
import com.idontchop.usagelimitservice.entities.WarningLimit;
import com.idontchop.usagelimitservice.repositories.UsageTypeRepository;
import com.idontchop.usagelimitservice.repositories.WarningLimitRepository;


@RestController
public class MainController {
		
	@Value ("${server.port}")
	private String serverPort;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@GetMapping("/helloWorld")
	public RestMessage helloWorld () {
		String serverAddress,serverHost;
		try {
			serverAddress = NetworkInterface.getNetworkInterfaces().nextElement()
					.getInetAddresses().nextElement().getHostAddress();
		} catch (SocketException e) {
			serverAddress = e.getMessage();
		}
		try {
			serverHost = NetworkInterface.getNetworkInterfaces().nextElement()
					.getInetAddresses().nextElement().getHostName();
		} catch (SocketException e) {
			serverHost = e.getMessage();
		}
		return RestMessage.build("Hello from " + appName)
				.add("service", appName)
				.add("host", serverHost)
				.add("address", serverAddress)
				.add("port", serverPort);
			
	}
	
	@Autowired
	WarningLimitRepository warningRepository;
	
	@Autowired
	UsageTypeRepository uRepository;
	
	@GetMapping("/geteight")
	public UsageType getengith() {
		return uRepository.findByName("guest-query").orElseThrow();
	}
		

}
