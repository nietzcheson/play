/**
 * Interacción del formulario de edición/creación de destinos
 *
 * @author Orlando Flores Arroyo
 * @version 1.0
 * @date 10/03/2015
 */
//

// Aquí comenzamos la interacción del formulario
$(document).ready(function () {
    $("#countries").on('change', function() {
        var country=$(this).val();
        load_states(country);
    });

    // Tomamos el valor del id que el controller pinta en el html
    var idValue = $("#id-value").val();
    var instanceId = (idValue == "") ? "" : "/" + idValue;
    var createDestinationURL = "/createDestination" + instanceId;


    // Cuando se intente enviar el formulario
    $('#createDestination').submit(function (evt) {
        // Detenemos el envío
        evt.preventDefault();
        var description=$("#destination-description");
        description_valid=true;
        if($.trim(description.val()) == "" || description.val().length < 2 ){
            description_valid=false;
            description.parent().removeClass("data_error").find("span").remove();
            description.parent()
                .addClass("data_error")
                .append("<span>Description is required</span>");
        }
        // Validamos que los campos hayan sido capturado correctamente
        if(formValidate($("#form-hotel-part"))  && description_valid) {
            // Variable que representa al botón Save
            var $submitButton = $('#submit');

            // Parametros post que serán enviados por ajax
            var data = {
                idValue: idValue,
                name: Encode($('#destination-name').val()),
                active: $(".destination-status:checked").val(),
                description: Encode($("#destination-description").val()),
                state: $("#states").val(),
                country: $("#countries").val()
            };

            // Deshabilitamos el botón Save y cambiamos el texto
            $submitButton.attr("disabled", true).val("creating....");

            // Realizamos el envío del formulario con ajax
            $.ajax({
                type: "POST",
                url: createDestinationURL,
                data: data
            }).done(function (msg) {
                // Convertimos el string recibido en un objeto de javascript
                var result = JSON.parse(msg);
                console.log(msg);
                // Aquí evaluamos si hay un error
                if(result.responsestatus==200 || result.responsestatus==201){
                    $("#redirect input[name=destinationName]").val($("#destination-name").val());
                    console.log("result.name>>>" + result.name);
                    $("#id-value").val(result.id);

                    $("#redirect").submit();
                } else{
                    if(result.message)
                        showError(result.message);
                    else
                        showError("An error occured. Please try again");
                    $submitButton.attr("disabled", false).val("Save");
                }
            })
            .error(function (err) { // Si hay un error

                // Restablecemos el texto del botón save
                $submitButton.attr("disabled", false).val("Save");

            }); // end ajax
        }else{
            showError("Information required");
        }
    }); // end click
}); // end ready