/**
 * Created by desarrollo1 on 13/04/2015.
 */
$(document).ready(function (){
    var idValue = $("#id-value").val();
    var mode = $("#mode").val();
    var instanceId = (idValue == "") ? "" : "/" + idValue;
    $( document ).on( "click", ".openwindow", function(e){
    //$('.openwindow').click(function(e) {
        e.preventDefault();
        window.open($(this).attr("href"), "popupWindow", "menubar=1,resizable=1,width=1000,height=1000, toolbar=1");
    });
    $(document).on("click", "#add-sale-button", function (event) {
        event.preventDefault();
        $("#add-sale-container").slideToggle("600");
    });
    $(document).on("click", "#new-offer-link", function (event) {
        event.preventDefault();
        $("#new-offer-details").slideToggle("600");
    });
    $(document).on("click", "#toggle-sale-information", function (event) {
        event.preventDefault();
        $("#information-container").slideToggle("600");
    });
    $(document).on("click", "#toggle-personal-information", function (event) {
        event.preventDefault();
        $("#personal-information-container").slideToggle("600");
    });
    $( "#certificate-code" ).keyup(function() {
        var length = $(this).val().length;
        if(length >= 8) {
            console.log("length");
            validateCertCode($(this));
        }
    });

    $( document ).on( "click", ".view-terms", function(e){
        item=$(this).attr("data-target");
        if(item=="#terms"){
            var text=$(this).closest(".item-sale").find(".terms").text();
            $("#terms .modal-body").html(text);
        }else{
            var text=$(this).closest(".item-sale").find(".campaign").text();
            $("#campaign .modal-body").html(text);
        }
    });
    $( document ).on( "click", ".ecert_link", function(e){
        var cert=$(this).attr("data-type");
        var booking=$(this).attr("data-booking");
        var url=$(this).attr("data-url");
        if(cert !='null' && cert != '')	{
            //window.open("http://bpo.m4sunset.com:8080/M4CApp/reportes/requestReportes.jsp?REPORTE="+cert+"&BookingNumber="+booking);
            console.log(url+"/M4CApp/reportes/requestReportes.jsp?REPORTE="+cert+"&BookingNumber="+booking);
            window.open(url+"/M4CApp/reportes/requestReportes.jsp?REPORTE="+cert+"&BookingNumber="+booking);
        }
    });
    $( document ).on( "click", ".read-more", function(e){
        var height=$(this).closest("p").find("span").height();
        if(height==20){
            $(this).closest("p").find("span").css({"height":"auto"});
            $(this).html("Read less");
        }else{
            $(this).closest("p").find("span").css({"height":"20px"});
            $(this).html("Read more");
        }
    });

});