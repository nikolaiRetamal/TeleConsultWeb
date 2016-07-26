package cnam.medimage.controller.controleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.Dicom;
import cnam.medimage.bean.User;
import cnam.medimage.repository.DicomRepository;
import cnam.medimage.repository.UserRepository;

@Controller
@Scope("session")
public class DetailImageContr {
	@RequestMapping(value="/detailImage")
	public ModelAndView handleRequest(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @RequestParam("id_dicom") String id_dicom) throws Exception {
		System.out.println("Je suis dans le contrôleur detail examen");
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
		//On trouve l'examen avec l'id_examen reçu en paramètre
		DicomRepository dicomRepo = new DicomRepository();
		Dicom dicom = dicomRepo.findOne(UUID.fromString(id_dicom));
		param.put("title", "Image");
		param.put("titrePage", "Detail de l'image");
		param.put("dicom", dicom);
		ModelAndView mv = new ModelAndView("detailImage", param);
		return mv;
	}

}