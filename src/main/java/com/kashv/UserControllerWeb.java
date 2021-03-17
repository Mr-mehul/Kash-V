package com.kashv;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kashv.config.SessionHandler;
import com.kashv.constance.StringConstance;
import com.kashv.model.UserDTO;
import com.kashv.model.UserType;
import com.kashv.service.UserService;

@Controller
public class UserControllerWeb {

	ModelAndView mv;
	private final UserService userService;

	public UserControllerWeb(final UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/getAdmin")
	public ModelAndView getUser(HttpSession session) {
		mv = new ModelAndView();
		if (SessionHandler.checkStatus(session) == true) {
			if (SessionHandler.getUserType(session).equals(UserType.SUPER_ADMIN)) {
				mv.setViewName(StringConstance.ADMIN_DFTAIL_PAGE);
				mv.addObject("list", userService.findAllByUserType(UserType.ADMIN));
			} else {
				mv.setViewName("redirect:/");
			}
		} else {
			mv.setViewName(StringConstance.ADMIN_LOGIN);

		}
		return mv;
	}

	@PostMapping("addAndUpdateUser")
	public ModelAndView addAndUpdateUser(HttpSession session, @ModelAttribute("user") @Valid final UserDTO userDTO) {
		mv = new ModelAndView();
		if (SessionHandler.checkStatus(session) == true) {
			if (SessionHandler.getUserType(session).equals(UserType.SUPER_ADMIN)) {
				if (userDTO.getId() == null) {
					userService.create(userDTO);
				} else {
					userService.update(userDTO.getId(), userDTO);
				}

				mv.setViewName(StringConstance.ADMIN_DFTAIL_PAGE);
				mv.addObject("list", userService.findAllByUserType(UserType.ADMIN));
			} else {
				mv.setViewName("redirect:/");
			}
		} else {
			mv.setViewName(StringConstance.ADMIN_LOGIN);

		}
		return mv;
	}

	@RequestMapping("/getUser")
	public ModelAndView getAllUser(HttpSession session) {
		mv = new ModelAndView();
		if (SessionHandler.checkStatus(session) == true) {
			if (SessionHandler.getUserType(session).equals(UserType.SUPER_ADMIN)) {
				mv.setViewName(StringConstance.USER_DFTAIL_PAGE);
				mv.addObject("list", userService.findAllByUserType(UserType.USER));
			} else {
				mv.setViewName("redirect:/");
			}
		} else {
			mv.setViewName(StringConstance.ADMIN_LOGIN);

		}
		return mv;
	}

	@PostMapping("/blockAdmin")
	public ModelAndView getAllUser(HttpSession session, @RequestParam("id") final Long id,
			@RequestParam("status") final Boolean status) {
		mv = new ModelAndView();
		if (SessionHandler.checkStatus(session) == true) {
			if (SessionHandler.getUserType(session).equals(UserType.SUPER_ADMIN)) {
				if (status == false) {
					userService.blockUser(id, true);
				} else {
					userService.blockUser(id, false);
				}
				mv.setViewName(StringConstance.ADMIN_DFTAIL_PAGE);
				mv.addObject("list", userService.findAllByUserType(UserType.ADMIN));
			} else {
				mv.setViewName("redirect:/");
			}
		} else {
			mv.setViewName(StringConstance.ADMIN_LOGIN);

		}
		return mv;
	}
}
