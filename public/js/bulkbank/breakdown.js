$(function(){

    $("#breakdown-form").submit(function(e) {

        alert('Listos');
        /*e.preventDefault();
        var data = $(this).serialize();
        var button= $(this).find('input[type="submit"]');
        button.attr("disabled", true).val("sending....");
        //Crear breakdown
        var form=$(this);
        $.ajax({
            url: '/createBreakdown',
            type: 'POST',
            data: data,
            success: function(result) {
                result = JSON.parse(result);
                console.log(result);

        result = {
         quantity: 1,
         userName: "XSIERRA",
         weekDTOList: {
         firstname: "Carlos",
         lastname: "Aguilar",
         status: "updated",
         reservationIdM4SG: "5478278745",
         reservationIdHotel: "745535885"
         }
         };


         if(result.message){
         showModalError($("#breakdown"), result.property+" "+result.message);
         }else{
         $.each(result.names, function (index, value) {
         var col=form.find(".col-sm-10:eq("+index+")");
         debugger;
         if(value.status=="Successful"){
         col.append($("<div/>", {class: 'alert alert-success', 'role':'alert',
         text: 'Successful: M4SG Reservation '+value.reservationIdM4SG
         +' Hotel Reservation '+value.reservationIdHotel}))
         }else if(value.status=="Error: Updating on M4SG"){
         col.append($("<div/>", {class: 'alert alert-danger', 'role':'alert',  text: value.status+ "button"}))
         }else{
         col.append($("<div/>", {class: 'alert alert-danger', 'role':'alert',  text: value.status}))
         }
         });
         bulkbank();
         }

         button.attr("disabled", false).val("Enviar");
         }
         });
         */
 });

});