/**
 * 
 */
package cnam.medimage.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLStreamException;

import cnam.medimage.bean.IndexMesh;
import cnam.medimage.bean.TagMesh;
import cnam.medimage.repository.IndexMeshRepository;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import com.ximpleware.extended.AutoPilotHuge;
import com.ximpleware.extended.NavExceptionHuge;
import com.ximpleware.extended.VTDGenHuge;
import com.ximpleware.extended.VTDNavHuge;
import com.ximpleware.extended.XPathEvalExceptionHuge;
import com.ximpleware.extended.XPathParseExceptionHuge;

/**
 * @author Jullien
 *
 */
public class ServiceMeshCrawler extends Service {
	
	/**
	* Attribut singleton qui permet de n'avoir qu'une seule instance de la classe Service
	* grâce à un appel unique du constructeur
	* 
	*/
	protected static ServiceMeshCrawler singleton;	
	
	static {
			singleton = new ServiceMeshCrawler();		
	}
	

	/**
	 * Fichier Mesh ouvert
	 */
	private static File mesh;
	
	
	/**
	 * Chemin du fichier mesh
	 */
	private static String meshPath = null;
	
	
	/**
	 * Renvoie le nombre de branche que l'on descend dans l'arborescence MESH
	 * Lors d'une recherche par mots-clés
	 */
	private static int PRECISION_RECHERCHE = 2;
	
	
	/**
	 * La liste simple des codes Mesh et des libellés correspondants
	 */
	//private static ArrayList<IndexMesh> indexMesh = null;
	
	/**
	* Méthode permettant d'accéder à l'unique instance de la classe Service
	* 
	* @return l'instance de la classe Service
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	*/
	public static ServiceMeshCrawler getInstance(HttpServletRequest request) throws FileNotFoundException, XMLStreamException {
		
		init(request,false);
		
		return (ServiceMeshCrawler) singleton;
	}

	/**
	 * 
	 * Crée la Factory et lui donne le fichier MeSH 2014 à digérer
	 * 
	 */
	public static void init(HttpServletRequest request, boolean force) throws XMLStreamException, FileNotFoundException {
				

		if(!isInit() || force){		

			HttpSession session =  request.getSession(false);
			ServletContext context = session.getServletContext();

			//On va chercher le fichier Mesh
			meshPath = context.getInitParameter("MESH_FILEPATH");
			//La ligne en dessous est nécessaire si le fichier Mesh est dans le WEB-INF
			//meshPath = context.getRealPath(meshFilePath);
			
			//Sauvegarde du chemin et du fichier Mesh			
			mesh = new File(meshPath);
			System.out.println("Création du mesh");
			System.out.println("mesh = " + mesh);
			
		}
		
	}
	
	
	/**
	 * 
	 * Contrôle l'initialisation de la classe de service avec le fichier Mesh
	 * 
	 * @return
	 */
	private static boolean isInit() {
		return mesh != null;
	}
	
	/**
	 * 
	 * Ramène un TagMesh à partir de son DescriptorUI
	 * 
	 * @param saisie
	 * @return
	 * @throws XPathParseExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws XPathEvalExceptionHuge 
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 */
	public TagMesh getDescriptorUI(String saisie) throws XPathParseExceptionHuge, XPathEvalExceptionHuge, NavExceptionHuge, XPathParseException {
		
		System.out.println("Entrée dans le Crawler");
		
		TagMesh tag = null;
		VTDGenHuge vg = new VTDGenHuge();
	    AutoPilotHuge ap = new AutoPilotHuge();
	    int i;
	
	    //Xpath de détection d'un DescriptorUI précis
	    ap.selectXPath("DescriptorRecord[DescriptorUI = '"+saisie+"']");
	      
          if (vg.parseFile(meshPath, true, VTDGenHuge.MEM_MAPPED)  ){
        	  
              VTDNavHuge vn = vg.getNav();
              
              ap.bind(vn); // apply XPath to the VTDNav instance, you can associate ap to different vns
              // AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
              while((i=ap.evalXPath())!=-1){                	  
            	  tag = tagBuilder(vn,i);   
            	}     
              
          } 
                        
  		System.out.println("Sortie du Crawler");
  		
		return tag;        
		
	}
	
	
	/**
	 * 
	 * Ramène tous les TagMesh du fichier XML
	 * 
	 * @param saisie
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws XPathParseExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws XPathEvalExceptionHuge 
	 */
	public ArrayList<TagMesh> parseThemAll() throws XPathParseException, XPathEvalException, NavException, XPathParseExceptionHuge, XPathEvalExceptionHuge, NavExceptionHuge{
		
		ArrayList<TagMesh> listeMesh = new ArrayList<TagMesh>();
		VTDGenHuge vg = new VTDGenHuge();
	    AutoPilotHuge ap = new AutoPilotHuge();
	    int i;
	
	    //Xpath de détection de tous les DescriptorRecord
	    ap.selectXPath("DescriptorRecord");

	    if (vg.parseFile(meshPath, true, VTDGenHuge.MEM_MAPPED)  ){
        	
  	      VTDNavHuge vn = vg.getNav();
  	      
  	      ap.bind(vn); // apply XPath to the VTDNav instance, you can associate ap to different vns
  	      // AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
  	      while((i=ap.evalXPath())!=-1){  
  	    		try{
  	          	  listeMesh.add(tagBuilder(vn,i)); 
  				}catch(Exception e){
  					System.out.println("Erreur de parsing...");
  				}  
  	    	}     
        	
        }
		 
		 return listeMesh;
		
	}
	
	
	/**
	 * 
	 * renvoie une liste de TagMesh correspondant à une saisie de mots-clés
	 * depuis le fichier XML
	 * 
	 * @param query
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws XPathParseExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws XPathEvalExceptionHuge 
	 */
	public ArrayList<TagMesh> getListTagJsonFromXML(String query) throws  XPathParseExceptionHuge, XPathEvalExceptionHuge, NavExceptionHuge, XPathParseException   {
		
		System.out.println("Entrée dans getListTagJsonFromXML()");
	
		ArrayList<TagMesh> reponseList = new ArrayList<TagMesh> ();
		
	    AutoPilotHuge ap = new AutoPilotHuge();		
	    AutoPilotHuge ap2 = new AutoPilotHuge();
	    int i;
	
	    //Xpath de détection d'un DescriptorName correspondant
	    ap.selectXPath("DescriptorRecord[contains(lower-case(DescriptorName/String), lower-case('"+query+"'))]");

	    //Xpath de détection des synonymes correspondants
	    ap2.selectXPath("DescriptorRecord[contains(lower-case(ConceptList/Concept/TermList/Term/String), lower-case('"+query+"'))]");
		
	    VTDGenHuge vg = new VTDGenHuge();
	    System.out.println("je vais rentrer dans le fichier : " + meshPath);
	    if (vg.parseFile(meshPath, true, VTDGenHuge.MEM_MAPPED)  ){
	    	System.out.println("je rentre dans le fichier : " + meshPath);
		    VTDNavHuge vn = vg.getNav();
		    
	        ap.bind(vn); // apply XPath to the VTDNav instance, you can associate ap to different vns
	        // AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
	        while((i=ap.evalXPath())!=-1){          
	        	
	            //move back to parent
	        	reponseList.add(tagBuilder(vn, i));
	            
	        }     

	        ap2.bind(vn); // apply XPath to the VTDNav instance, you can associate ap to different vns
	        // AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
	        while((i=ap2.evalXPath())!=-1){          
	        	
	            //On ajoute le tag dans lequel on navigue
	        	reponseList.add(tagBuilder(vn, i));
	            
	        }     
        
		}
	
		System.out.println("Sortie de getListTagJsonFromXML()");
	      
		return reponseList;
		
		
	}
	
	
	/**
	 * 
	 * à partir d'un DescriptorUI on ramène tous les DescriptorUI a aller chercher en base
	 * On se limite à descendre de PRECISION_RECHERCHE dans l'arborescence
	 * 
	 * @param descriptorUI
	 * @return
	 * @throws XPathParseException
	 * @throws XPathEvalException
	 * @throws NavException
	 * @throws XPathParseExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws XPathEvalExceptionHuge 
	 */
	public ArrayList<String> getNuageRechercheFromDescriptorUI(String descriptorUI) throws XPathParseException, XPathEvalException, NavException, XPathParseExceptionHuge, XPathEvalExceptionHuge, NavExceptionHuge  {

		System.out.println("Entrée dans getNuageRechercheFromDescriptorUI()");
		
		ArrayList<String> resultat = new ArrayList<String>();
		
		//On arrive avec un mot-clé ou plutôt son DescriptorUI et il faut retrouver toutes ses arborescences filles
		// Pour commencer on utilise ce qu'on a déjà : 
		TagMesh tagOriginal = getDescriptorUI(descriptorUI);

		//On oublie pas la requête d'origine
		resultat.add(descriptorUI);

	    VTDGenHuge vg = new VTDGenHuge();
	    int i,j;
	    
	    
	    if (vg.parseFile(meshPath, true, VTDGenHuge.MEM_MAPPED)  ){

    	    VTDNavHuge vn = vg.getNav();
    	    
			for(String categorie:tagOriginal.getCategories()){

				//On vérifie la profondeur du Descriptor dans l'arbre du Mesh
				//La position dans l'arbre est définie par le(s) TreeNumber du Descriptor
				//Ils sont sous la forme A01.123.456.789 le plus de chiffres, le plus profond
				//On limite la recherche à PRECISION_RECHERCHE de profondeurs après le terme souhaité
			    int precision = categorie.split("\\.").length;				

			    //Xpath de détection des hierarchies inférieures correspondantes (dans la limite de : PRECISION_RECHERCHE)
				AutoPilotHuge ap = new AutoPilotHuge();
			    ap.selectXPath("DescriptorRecord[starts-with(TreeNumberList/TreeNumber, '"+categorie+"')]");
				
			    	VTDNavHuge vnClone = vn.cloneNav();
		        	ap.bind(vnClone);
		        	
		        	// apply XPath to the VTDNav instance, you can associate ap to different vns
			        // AutoPilot moves the cursor for you, as it returns the index value of the evaluated node
			        while((i=ap.evalXPath())!=-1){
			        	
			        	 //On a trouvé une branche sous la catégorie cherché
			        	//On crée le tag équivalent
		        		 TagMesh tag = tagBuilder(vnClone, i);
		        		 
		        		 for(String cat:tag.getCategories()){
		        			 
		        			// precision + PRECISION_RECHERCHE < à la profondeur	 
		        			//On filtre le tag, s'il est trop profond on ne le traite pas
		        			 if(	 (cat.startsWith(categorie) &&
		        					 (precision+PRECISION_RECHERCHE)>=cat.split("\\.").length) 		        					 
		        					 && !resultat.contains(tag.getIdTag()) ){
		        				 
		        				 resultat.add(tag.getIdTag());
		        				 break;
		        				 
		        			 }
		        				 
		                 }
		            	
			            
			        } 
			        
			}
		        		
        }

		System.out.println("Sortie de getNuageRechercheFromDescriptorUI()");
		
		return resultat;
		
	}
	
	
	/**
	 * 
	 * Consulte la liste des "indexMesh" 
	 * 
	 * @param query
	 * @return
	 */
//	public ArrayList<IndexMesh> getListTagJsonFromBase(String query)  {
//		
//		ArrayList<IndexMesh> result = new ArrayList<IndexMesh>();
//		ArrayList<IndexMesh> all = getIndexMesh();
//		
//		for(IndexMesh i:all){
//			if(i.getNom().toLowerCase().contains(query.toLowerCase())){
//				result.add(i);
//			}
//		}		
//		
//		return result;
//		
//	}
		
	
	
	/**
	 * @throws XPathParseExceptionHuge 
	 * @throws XPathParseException 
	 * @throws XPathEvalExceptionHuge 
	 * @throws NavExceptionHuge 
	 * @throws NavException 
	 * @throws XPathEvalException 
	 * 
	 */
	private TagMesh tagBuilder(VTDNavHuge vn,int xPathEval) throws XPathParseExceptionHuge, NavExceptionHuge, XPathEvalExceptionHuge, XPathParseException  {
		
		TagMesh tag = new TagMesh(null,null);
		String idTag = null;
		String tagName = null;
		ArrayList<String> synonymes = new ArrayList<String>(); 
		ArrayList<String> categories = new ArrayList<String>();
		int i,j;
		
		
		VTDNavHuge vnClone = vn.cloneNav();		
		
		//Xpath du DescriptorUI
	    AutoPilotHuge ap = new AutoPilotHuge();
        ap.selectXPath("DescriptorUI");
		//Xpath du DescriptorName
	    AutoPilotHuge ap2 = new AutoPilotHuge();
        ap2.selectXPath("DescriptorName/String");
		//Xpath de détection de la liste des termes/synonymes
	    AutoPilotHuge ap3 = new AutoPilotHuge();
        ap3.selectXPath("ConceptList/Concept/TermList/Term/String");
		//Xpath de détection de la liste des categories
	    AutoPilotHuge ap4 = new AutoPilotHuge();
        ap4.selectXPath("TreeNumberList/TreeNumber");
       
        //On remonte au DescriptorRecord
        if("DescriptorRecord".equals(vnClone.toString(xPathEval))){         	
       	  
            //on descend au prochain DescriptorUI
        	ap.bind(vnClone);
        	while((i=ap.evalXPath())!=-1){ 
            
        		 j = vnClone.getText();
                 if (j != -1) idTag = vnClone.toString(j);
            	
            }    
            
            //on descend au prochain DescriptorName
        	ap2.bind(vnClone);
            while((i=ap2.evalXPath())!=-1){ 
                            	  
            	 j = vnClone.getText();
                 if (j != -1)  tagName = vnClone.toString(j).split("\\[")[0];
          	  
            }  
		        
	        //Récupération des synonymes
	        ap3.bind(vnClone);
	        while(ap3.evalXPath()!=-1){
	        	
	            j = vnClone.getText();
                if (j != -1) synonymes.add(vnClone.toString(j));
	        	
	        }
	        
	        
	        //Récupération de la categories
	        ap4.bind(vnClone);
	        while(ap4.evalXPath()!=-1){
	        	
	            j = vnClone.getText();
                if (j != -1) categories.add(vnClone.toString(j));
	        	
	        }
		        
        }else{
        	
        	throw new XPathParseException("Le TagBuilder n'est pas dans un DescriptorRecord.");
        	
        }		
        
        tag.setIdTag(idTag);
        tag.setNom(tagName);
        tag.setSynonymes(synonymes);
        tag.setCategories(categories);
        
		
		return tag;
		
	}

	/**
	 * 
	 * Va chercher en base les index et les sauve dans le singleton
	 * 
	 * @return
	 */
//	public static ArrayList<IndexMesh> getIndexMesh() {
//		
//		if(indexMesh == null){
//
//			System.out.println("Initialisation d'indexMesh");
//			IndexMeshRepository indexRepo = new IndexMeshRepository();
//			indexMesh = (ArrayList<IndexMesh>)indexRepo.getAllIndexes();
//			System.out.println("Initialisation OK");
//			
//		}
//		
//		return indexMesh;
//	}
//
//	public static void setIndexMesh(ArrayList<IndexMesh> indexMesh) {
//		ServiceMeshCrawler.indexMesh = indexMesh;
//	}
//	
//	

}
