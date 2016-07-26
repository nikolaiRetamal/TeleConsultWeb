package cnam.medimage.controller.controleur;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.User;
import cnam.medimage.repository.UserRepository;

@Controller
@Scope("session")
public class AccueilContr {
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		System.out.println("Je suis dans le contrôleur Accueil");
		Map<String, Object> param = new HashMap<>();
		String id_user = "";
		User user;
		if(session.getAttribute("id_user") != null){
			id_user = session.getAttribute("id_user").toString();
			System.out.println("USER = " + id_user);
			UserRepository userRepo = new UserRepository();
			user = userRepo.findOne(UUID.fromString(id_user));
			param.put("user", user);
		}
		param.put("title", "Accueil");
		param.put("titrePage", "MEDIMAGE");
		ModelAndView mv = new ModelAndView("accueil");
		mv.addAllObjects(param);
		return mv;
	}
}
