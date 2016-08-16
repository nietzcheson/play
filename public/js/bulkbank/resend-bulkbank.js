$(document).on('ready', function(){

    $('.resend-bulkbank').on('click', function () {

        var id = $(this).attr('id');

        var button = $(this);
        button.button("loading");

        $.ajax({
            url: '/bulkbank/resend-bulkbank/'+ id ,
            type: 'GET',
            success: function (data) {

                data = JSON.parse(data);

                if(data.status == 200){

                    var alert = $("<div />", { "class": "alert alert-info" });
                    var idM4SG = $("<p />").text("Reservation M4SG: " + data.reservationIdM4SG);
                    var idHotel = $("<p />").text("Reservation Hotel: " + data.reservationIdHotel);

                    alert.append(idM4SG);
                    alert.append(idHotel);

                    $("#failed-alert").html(alert);

                    var tr = button.closest("tr");

                    tr.remove();
                }
            }
        });
    });


});