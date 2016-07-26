//Liste des tags remontés
var tagList;
var tagListId;
var usageList;

window.onload = function() {

	$("#dropzone-form").dropzone({
		  // The configuration we've talked about above
		  url:"/medimage/upload",
		  autoProcessQueue: false,
		  autoDiscover: false,
		  uploadMultiple: true,
		  parallelUploads: 1000,
		  previewsContainer: ".dropzone-previews",
		  maxFiles: 2000,
		
		  // The setting up of the dropzone
		  	init: function() {
			    var myDropzone = this;
			    console.log("hello");
			    console.log("Coucou");
				// First change the button to actually tell Dropzone to process the queue.
				$("button[type=submit]").on('click',function(e) {
				// Make sure that the form isn't actually being sent.
					e.preventDefault();
					e.stopPropagation();
					console.log("Je lance l'upload");
					myDropzone.processQueue();
				});
		
				// Listen to the sendingmultiple event. In this case, it's the sendingmultiple event instead
				// of the sending event because uploadMultiple is set to true.
				this.on("sendingmultiple", function() {
					console.log("sendingmultiple");
				});
				this.on("successmultiple", function(files, response) {
					console.log("successmultiple");
					console.log("réponse" + response.id_examen);
					console.log("réponse" + files);
					// similar behavior as an HTTP redirect
					window.location.replace("/medimage/resultat_import?id_examen="+response.id_examen);
				});
				this.on("errormultiple", function(files, response) {
					console.log("error");
				});
		  	}
	});
	
	$(function() {
	    $( "#tags" ).autocomplete({source: function (request, response) {
	        $.ajax({
	            url: "/medimage/getTags",
	            dataType: "json",	            
	            data: {
	            	term: request.term
	            },
	            success: function (data) {
	            	//L'ajax renvoie une map<String,String> contenant les 
	            	//descriptor ou synonym contenant la chaîne associés à leur DescriptorUI
	            	tagList = data;
	            	//On sauve la liste puisqu'il faudra retrouver les DescriptorUI
		  			console.log("Retour de recherche : "+data.length+" "+data);
	            	if(jQuery.isEmptyObject(data)){
	            		//En cas de retour vide, on considère que c'est une saisie libre
	            		//on propose la saisie de l'utilisateur
			  			console.log("Recherche vide");
			  			response([request.term]);
	            	}else{
	            		//On renvoie une liste ne comprenant que les noms
			  			console.log("Recherche réussie !");
		            	response($.map( data, function(value, key ) {return key;}));	            		
	            	}	            
	            },
	            error: function (message) {
		  			console.log("Retour nok...");
		  			console.log(message);
	            }
	        });
	    },
	    autoFill: true,
	    mustMatch: true,
	    selectFirst: true,
	    minLength: 4,
	    messages: {
	    	noResults:"Pas de résultats.",
	    	results:function(e){return e+(e>1?" mots-clés référencés.":" mot-clé correspondant.")}
	    },
	      select: function( event, ui ) {
	    	  var choix = ui.item.value;
	          console.log( "le User a choisi"+ choix);
	          $('#chosenTags').val($('#chosenTags').val()+choix+'\n');
	          //Par défaut on sauvera la valeur saisie : Saisie libre
	          var val = choix;	          
	          if(!jQuery.isEmptyObject(tagList)){
	        	//Si tagList n'est pas vide on sauvera le DescriptorUI
        	    val = $.map( tagList, function(value, key ) { 
	        	  if(choix==key){
	        		  return value;
	        	  }
	        	}); 
	          }
	          $('#chosenTagsValueForDicom').val($('#chosenTagsValueForDicom').val()+choix+'\n');
	          $('#chosenTagsValue').val($('#chosenTagsValue').val()+val+'\n'); 
	          console.log( "Ce qui correspond à "+ val);
	        }
	    });

    });
	
    $("#usage").autocomplete({source: function (request, response) {
        $.ajax({
            url: "/medimage/getUsages",
            dataType: "json",	            
            data: {
            	term: request.term
            },
            success: function (data) {
            	//L'ajax renvoie une map<String,String> contenant les 
            	//descriptor ou synonym contenant la chaîne associés à leur DescriptorUI
            	usageList = data;
            	//On sauve la liste puisqu'il faudra retrouver les DescriptorUI
	  			console.log("Retour de recherche : "+data.length+" "+data);
            	if(jQuery.isEmptyObject(data)){
            		//En cas de retour vide, on considère que c'est une saisie libre
            		//on propose la saisie de l'utilisateur
		  			console.log("Recherche vide");
		  			response([request.term]);
            	}else{
            		//On renvoie une liste ne comprenant que les noms
		  			console.log("Recherche réussie !");
	            	response($.map( data, function(value, key ) {return key;}));	            		
            	}	            
            },
            error: function (message) {
	  			console.log("Retour nok...");
	  			console.log(message);
            }
        });
	    },
	    autoFill: true,
	    mustMatch: true,
	    selectFirst: true,
	    minLength: 4, 
	    messages: {
	        noResults: '',
	        results: function() {}
	    },
	    select: function( event, ui ) {	          
	    	$('#usageConnu').val(usageList[ui.item.value]);
	    }
    });
}