package cnam.medimage.controller.controleurAjax;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.IndexMesh;
import cnam.medimage.bean.TagMesh;
import cnam.medimage.service.ServiceMeshCrawler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import com.ximpleware.extended.NavExceptionHuge;
import com.ximpleware.extended.XPathEvalExceptionHuge;
import com.ximpleware.extended.XPathParseExceptionHuge;

@Controller
public class AjxRechercheMesh {
	
	List<TagMesh> data = new ArrayList<TagMesh>();
	
	 
 
	@RequestMapping(value = "/getTags", method = RequestMethod.GET )
	public @ResponseBody
	Map<String,String> getTags(@RequestParam("term") String tagName, 
								HttpServletResponse response,
								HttpServletRequest  request) throws IOException {
		
		System.out.println("Ajax de parse du mesh");
		List<TagMesh> result = null;
		
		//On renvoie une TreeMap pour classer alphabétiquement les réponses
		TreeMap<String, String> indexes = new TreeMap<String, String>(new Comparator<String>() {
		    public int compare(String o1, String o2) {
		        return o1.toLowerCase().compareTo(o2.toLowerCase());
		    }
		});
		
		try{
			result = searchResultFromXml(tagName, request);
			//result = searchResultFromBase(tagName);
			
		}catch (Exception e){
			//traiter recherche vide
			result = new ArrayList<TagMesh>();
		}
		
		//On sauve les TagMesh Structurés
		for(TagMesh tag : result){
			indexes.put(tag.getNom(),tag.getIdTag());
				for(String s:tag.getSynonymes()){
					if(s.toLowerCase().contains(tagName.toLowerCase())){
						indexes.put(s,tag.getIdTag());								
					}
				}
		
		}		
		
		//On affiche les résultats par curiosité
//		for(TagMesh t:result){
//			System.out.println(t.getNom());
//		}
 
		return indexes;
	}
 

	/**
	 * 
	 * Appelle le serviceMesh
	 * 
	 * @param tagName
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	 * @throws NavExceptionHuge 
	 * @throws XPathEvalExceptionHuge 
	 * @throws XPathParseExceptionHuge 
	 */
	private List<TagMesh> searchResultFromXml(String tagName, HttpServletRequest request) throws FileNotFoundException, XMLStreamException, XPathParseExceptionHuge, XPathEvalExceptionHuge, NavExceptionHuge, XPathParseException {
		
		//On ne passe pas le request, l'initialisation a déjà été effectuée dans AccueilImportContr
		ServiceMeshCrawler serviceMeshCrawler = ServiceMeshCrawler.getInstance(request);
 
		data = serviceMeshCrawler.getListTagJsonFromXML(tagName);
		
		System.out.println("Sortie de searchResultFromXml : "+tagName);
		
		return data;
	}
	
	/**
	 * 
	 * Appelle le serviceMesh
	 * 
	 * @param tagName
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	 */
//	private List<TagMesh> searchResultFromBase(String tagName) throws XPathParseException, XPathEvalException, NavException, FileNotFoundException, XMLStreamException {
//		
//		data = new ArrayList<TagMesh>();
//		
//		//On ne passe pas le request, l'initialisation a déjà été effectuée dans AccueilImportContr
//		ServiceMeshCrawler serviceMeshCrawler = ServiceMeshCrawler.getInstance(null);
//		 
//		List<IndexMesh> indexes = serviceMeshCrawler.getListTagJsonFromBase(tagName);
// 
//		// iterate a list and filter by tagName
//		for (IndexMesh index : indexes) {
//				data.add(new TagMesh(index.getIdTag(), index.getNom()));
//		}
//
//		System.out.println("Sortie de searchResultFromBase : "+tagName);
//		
//		return data;
//	}



}
