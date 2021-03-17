package com.kashv.config;

import javax.servlet.http.HttpSession;

import com.kashv.model.UserType;

public class SessionHandler {

	// checking user are admin
	public static boolean checkStatus(HttpSession session) {
		if (session.getAttribute("status") == "yes") {
			return true;
		}
		return false;
	}

	public static String getUserId(HttpSession session) {
		return (String) session.getAttribute("userId");
	}

	public static String getEmailId(HttpSession session) {
		return (String) session.getAttribute("email");
	}

	public static Long getPhoneNumber(HttpSession session) {
		System.out.println(session.getAttribute("phoneNumber"));
		return (Long) session.getAttribute("phoneNumber");
	}

	public static int getOtp(HttpSession session) {
		return (int) session.getAttribute("otp");
	}

	public static UserType getUserType(HttpSession session) {
		return (UserType) session.getAttribute("userType");
	}
}
