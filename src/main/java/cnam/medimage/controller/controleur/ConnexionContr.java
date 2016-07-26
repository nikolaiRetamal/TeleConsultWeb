package cnam.medimage.controller.controleur;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConnexionContr {
	
	@RequestMapping(value="/connexion")
	public ModelAndView identification(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("Je suis dans le contrôleur identification");
		Map<String, Object> param = new HashMap<>();
		param.put("title", "Connexion");
		param.put("titrePage", "Connexion");
		ModelAndView mv = new ModelAndView("connexion", param);
		return mv;
	}
	
}
