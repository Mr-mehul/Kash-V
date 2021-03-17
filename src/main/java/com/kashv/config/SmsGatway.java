package com.kashv.config;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import com.plivo.api.Plivo;
import com.plivo.api.exceptions.PlivoRestException;
import com.plivo.api.models.message.Message;
import com.plivo.api.models.message.MessageCreateResponse;

public class SmsGatway {

	static int min = 3000;
	static int max = 7000;
	static String authId = "MAMWE5MTNLNWM3ZTM0NW";
	static String authToken = "Njk0MmNkYjFhYjcwZGNhYjlkZDI5M2UwZWRhMzQz";

	public static int sendotp(String number) throws Exception {
		int otp = (int) (Math.random() * max + min);
		try {
			Plivo.init(authId, authToken);
			MessageCreateResponse response = Message.creator("+7486099988", Collections.singletonList(number), "#" + otp)
					.url(new URL("http://localhost:8084/" + number + "/")).create();
			System.out.println(response);
			System.out.println("We have sent Otp : " + otp + " on this number : " + number);
		} catch (PlivoRestException | IOException e) {
			e.printStackTrace();
			System.out.println("otp sending failed for this number : " + number);
			return 0000;
		}
		return otp;
	}
	
	public static boolean sendMessage(String number,String massage) throws Exception {
		try {
			Plivo.init(authId, authToken);
			MessageCreateResponse response = Message.creator("+7486099988", Collections.singletonList(number), massage)
					.url(new URL("http://localhost:8084/" + number + "/")).create();
			System.out.println(response);
			System.out.println("We have sent Massage : " + massage + " on this number : " + number);
		} catch (PlivoRestException | IOException e) {
			e.printStackTrace();
			System.out.println("Massage sending failed for this number : " + number);
			return false;
		}
		return true;
	}
}
