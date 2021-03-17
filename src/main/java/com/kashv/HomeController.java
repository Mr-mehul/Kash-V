package com.kashv;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.service.UserService;

@Controller
public class HomeController {

	ModelAndView mv;
	private final UserService userService;

	public HomeController(final UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public ModelAndView index(HttpSession session) {
		mv = new ModelAndView();
		if (SessionHandler.checkStatus(session) == true) {
			mv.setViewName(StringConstance.DASHBOARD);
		} else {
			mv.setViewName(StringConstance.ADMIN_LOGIN);

		}
		return mv;
	}

	// log out method for destroying session
	@GetMapping("logout")
	public ModelAndView logOut(HttpSession session) {
		session.invalidate();
		mv = new ModelAndView();
		mv.setViewName(StringConstance.ADMIN_LOGIN);
		return mv;
	}

	@RequestMapping("testing")
	public ModelAndView testing() {
		//mv.setViewName(StringConstance.RESET_PASSWORD_PAGE);
		return mv;
	}
}
