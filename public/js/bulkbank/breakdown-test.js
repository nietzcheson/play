$(document).on('ready', function(){

    $('#breakdown-test').on('click', function () {

        $.ajax({
            url: '/bulkbank/breakdown-test',
            type: 'POST',
            //data: data,
            success: function(data) {

                data = JSON.parse(data);

                console.log(data);

                $(data.data).each(function (e,i) {
                    console.log(i);
                    console.log(i.firstname);
                    console.log(i.lastname);
                    console.log(i.reservationIdHotel);
                    console.log(i.reservationIdM4SG);
                });
            }

        });
    });

});