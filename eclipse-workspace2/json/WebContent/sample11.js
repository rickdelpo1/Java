//this one does not use a loaddata function from ajax

jQuery(document).ready(function() {

       //only ajax call to get string buffer for dynamic col model


$(".abutton").click(function() {


//***********ajax handler is setting servlet attribute (get param) so put that call here first
//then for each additional servlet need to get attribute, so do this for col model and colModel2
             //already done in startscript
//********** also get connection only once starting win colmodel servlet and closing conn in startscript when done
                  //each servelet gets conn object just like each servlet is getting the attribute
   //**********then need to do the create table dialog and add select count(*) functionality + show errors                             

var responseText = '';     
$.ajax({ type: "GET",   
         url: "http://localhost:8080/json/ColModel", 
         async: false,  //used this so ajax calls are called in order synchroniously vs async
         success : function(response)   //grab response as text and not json from servlet do get method
         {
             responseText = response;   //for cols , gets array list from ColModel servlet
            // alert(responseText);   //instead of alert here just pass var to either console below or use anywhere
         }
});


var responseText2 = {};     //seems like datatype is not a factor
$.ajax({ type: "GET",   
         url: "http://localhost:8080/json/ColModel2", 
         dataType: 'JSON',   //on server side setting content type to text shows uncaught exception
         async: false,  //used this so ajax calls are called in order synchroniously vs async
        // writable: true,
         success : function(response)   //grab response as text and not json from servlet do get method
         {
             responseText2 = response;   //for cols , gets array list from ColModel servlet
             //alert(responseText2);   //instead of alert here just pass var to either console below or use anywhere
         }
});


//$(".abutton").click(function() {    //start of on click button..dont execute unless clicked


var name = $("#name").val();

$.ajax({ 
            type: "POST",
            data: { name1 : name},
            async: false,
            url: "http://localhost:8080/json/AjaxHandler"
        });


     
$('#grid1').jqGrid('GridUnload');
        $("#grid1").jqGrid({

                url : "http://localhost:8080/json/StartScript",
 
              datatype : "json",
                mtype : 'GET',
               // colNames : [ 'first', 'email' ],    //bring in string buffer
               // colNames : [ responseText + " passed in as var", 'email' ],
                  //colNames : [ responseText ],
            colNames :  responseText ,    //array list of cols as json string getting response object from ColModel 
                //pass dynamic var here and also to col model (col names only puts names there, col model gets fields)


                //colModel : [ { name : 'region' }, { name : 'plant_name' } ],
                                 //double quotes are also working
                //  colModel : [ { "name" : "first" }, { "name" : "email" } ],           //bring in second array
                  colModel : responseText2 ,    //col model calling for small n in name
                          //need to append {name :' to each col..these are actually key pairs vs above plain array
                          //response needs to be written as an actual key pair..append txt to array object ??
                          //or is this a json array vs a json string like above
           //   console.log(responseText2),

          //default model returns gridview even with all below turned off, except row numbers on or off and caption

             //   pager : '#pager',
               // rowNum : 10,
     rownumbers: true,
              //  rowList : [ 10, 20, 30 ],
               // sortname : 'first',
               // sortorder : 'desc',
              //  viewrecords : true,
             //   gridview : true,
             loadonce: false,
                caption : 'Data Report from servlet with resizable cols unlike js grid',


               // editurl : "http://localhost:8080/json/ManageCars"

     
        }); //end of jq grid function

//console.log(responseText2)


      });    //this is end of button press


});    //end of ready function