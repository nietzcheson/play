(function($){

    $.fn.extend({

        bulkbankFailures: function(){

            var template = $("#templateId").val();
            var year = $("#year").val();

            $("#failures-table tbody").html("Loading...");

            $(this).each(function () {

                $.ajax({
                    url: '/bulkbank/breakdown-failures',
                    type: 'POST',
                    data: { "template": template, "year": year },
                    dataType: "text",
                    success: function(data) {

                       data = JSON.parse(data);

                        var tbody = $("<tbody />");


                        $(data).each(function (i, e) {
                            var tr = $("<tr />");
                            var m4sg = $("<td />").text(e.id);
                            var name = $("<td />").text(e.name +" "+e.lastname);

                            var btn = $("<button />", {"class":"btn btn-default resend-bulkbank", "type": "button", "id": e.id, "data-loading-text":"Sending..."}).text("Re enviar");

                            var tdBtn = $("<td />", {"class":"text-right"});

                            tdBtn.append(btn);

                            tr.append(m4sg);
                            tr.append(name);
                            tr.append(tdBtn);
                            tbody.append(tr);
                        });

                        $("#failures-table tbody").remove();
                        $("#failures-table").append(tbody);

                        $('.resend-bulkbank').on('click', function () {

                            $(this).bulkbankResend();

                        });

                    }
                });

            });


        }

    });

})(jQuery);