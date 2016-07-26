package cnam.medimage.controller.controleur;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeconnexionContr {
	@RequestMapping(value="/deconnexion")
	public ModelAndView identification(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		System.out.println("Je suis dans le contrôleur deconnexion");
		session.invalidate();
		Map<String, Object> param = new HashMap<>();
		param.put("title", "Accueil");
		param.put("titrePage", "MEDIMAGE");
		ModelAndView mv = new ModelAndView("accueil");
		mv.addAllObjects(param);
		return mv;
	}
}
