/**
 * Created by Orlando on 09/03/2015.
 */


// La función getInt() sirve para obtener el valor númerico de un string que puede contener
// caracteres alfanuméricos.
function getInt(value) {
    if(value == "") {
        return 0;
    } else {
        return parseInt(value.replace(/[A-Za-z$-]/g, ""));
    }
}


function formValidate(){

    var serialNumberMessage   = "You must indicate the amount";
    var result                = true;
    var first_error           = true;

    //Nombre de la campaña
    $("#serialsNumber").parent().removeClass("data_error").find("span").remove();
    if($.trim($("#serialsNumber").val()) == "") {
        $("#serialsNumber").parent()
            .addClass("data_error")
            .append("<span>"+serialNumberMessage+"</span>");
        if(first_error)  {
            focusCursor($("#serialsNumber"));
            first_error = false;
        }
        result = false;
    }


    return result;
}

function focusCursor(control){
    $('body,html').animate({
        scrollTop: $(control).offset().top - 30
    }, 800);

    $(control).focus();
}





$(document).ready(function () {

    var idValue = $("#id-value").val();

    $('#serials-form').submit(function (evt) {
        evt.preventDefault();
        if(formValidate()) {
            var $submitButton = $('#submit');
            var data = {
                "quantityFolios":$("#serialsNumber").val(),
                "campaignId":idValue
            };
            $submitButton.attr("disabled", true).val("creating....");
            $.ajax({
                type: "POST",
                url: "/Serials/createSerials",
                data: data
            })
                .done(function (msg) {

                    var result = JSON.parse(msg);

                    // Si hay error
                    if(result.status == "success" || result == "success") {
                        console.log(result.status);
                        $("#QTYofSerials").val($("#serialsNumber").val());
                        $("#redirect").attr("action", "/serials/" + idValue);
                        $("#redirect").submit();
                        $submitButton.attr("disabled", false).val("Save");
                    } else {
                        alert("Ha ocurrido un error");
                        $submitButton.attr("disabled", false).val("Save");
                    }

                })
                .error(function (err) {
                    console.log("Error: " + err);
                    $submitButton.attr("disabled", false).val("Save");
                }); // end ajax
        } else {
            //alert("prueba");
        }

    }); // end click

}); // end ready