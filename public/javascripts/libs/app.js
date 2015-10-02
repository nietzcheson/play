/**
 * Created by orlando on 15/01/15.
 */

$(document).ready(function() {

    var urlAPI = "http://10.194.21.232:8888/v1/offers";

//    $.getJSON(urlAPI,
//        function(data){
//            console.log(data);
//        }); // end getJSON
//}); // end ready
//$.get( urlAPI, function( data ) {
//    alert( "Data Loaded: " + data );
//});
//});
$.ajax({
    url: 'http://10.194.21.232:8888/v1/offers',
    data: 'http://10.194.21.232:8888/v1/offers',
    success: function() {
        alert("prueba");
    },
    dataType: json
});
});
