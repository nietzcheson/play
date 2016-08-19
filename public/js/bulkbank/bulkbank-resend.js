(function($){

    $.fn.extend({

        bulkbankResend: function(){

            $(this).each(function(){

                var id = $(this).attr('id');

                var button = $(this);
                button.button("loading");

                $.ajax({
                    url: '/bulkbank/resend-bulkbank/'+ id ,
                    type: 'GET',
                    success: function (data) {

                        data = JSON.parse(data);

                        console.log(data);

                        if(data.code == 200){

                            var alert = $("<div />", { "class": "alert alert-info" });

                            $(data.data).each(function (i, e) {
                                var idM4SG = $("<p />").text("Reservation M4SG: " + e.reservationIdM4SG);
                                var idHotel = $("<p />").text("Reservation Hotel: " + e.reservationIdHotel);

                                alert.append(idM4SG);
                                alert.append(idHotel);
                            });


                            $("#failed-alert").append(alert);

                            var tr = button.closest("tr");

                            tr.remove();
                        }

                    }
                });

            });

        }

    });

})(jQuery);

$(document).on('ready', function(){

    $('.resend-bulkbank').on('click', function () {

        $(this).bulkbankResend();

    });

});