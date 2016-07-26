var metas = new Array();
var currentMetaKey ;
var tagList;
var metaList;
var usageList;
window.onload = function() {
	
	$('#ajoutMeta').on('click', function(){
		var metadata = new Object();
		metadata.key = currentMetaKey;
		metadata.value = $('#metaValue').val();
		var metadataHtml = '<span class="metadata">'+ $('#metaKey').val() + ' : '+ metadata.value +'</span>';
		$('#metaKey').val('');
		$('#metaValue').val('');
		$('#listeMetadatas').append(metadataHtml);
		metas.push(metadata);
		$('#metadatas').val(JSON.stringify(metas));
	});
	
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
    messages: {
    	noResults:"Pas de résultats.",
    	results:function(e){return e+(e>1?" mots-clés référencés.":" mot-clé correspondant.")}
    },
    minLength: 4,
      select: function( event, ui ) {
    	  var choix = '<span class="tag">'+ui.item.value +'</span>';
          console.log( "le User a choisi"+ choix);
          $('#chosenTags').append(choix);
          //Par défaut on sauvera la valeur saisie : Saisie libre
          var val = ui.item.value;	          
          if(!jQuery.isEmptyObject(tagList)){
        	 //Si tagList n'est pas vide on sauvera le DescriptorUI
    	    val = $.map( tagList, function(value, key ) { 
        	  if(val==key){
        		  return value;
        	  }
        	}); 
          } 
          $('input[name="chosenTagsValue"]').val(
        		  $('input[name="chosenTagsValue"]').val()
        		  + val + '\n'); 
          console.log( "Ce qui correspond à "+ val);
        }
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
    $("#metaKey").autocomplete({source: function (request, response) {
        $.ajax({
            url: "/medimage/getMetadataTags",
            dataType: "json",	            
            data: {
            	term: request.term
            },
            success: function (data) {
            	//L'ajax renvoie une map<String,String> contenant les 
            	//descriptor ou synonym contenant la chaîne associés à leur DescriptorUI
            	metaList = data;
            	console.log(data);
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

	  	  currentMetaKey = metaList[ui.item.value];
    }
    });
}