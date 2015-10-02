function assignLanguageIndex(value) {
    var codeContent = new Array();
    for(var i=0; i<value.length; i++) {
        var code = value[i].language.code;
        codeContent[code] = i;
    }
    return codeContent;
}
function formValidate(){
    // Definimos los mensajes de error
    var offerNameMessage = "Offer name is required";
    var destinationMessage = "You must choose at least one destination";
    var offerNightsMessage = "Nights are required";
    var descriptionMessage = "Description is required";
    var detailsMessage = "Details are required";
    var termsMessage = "Terms are required";
    var hookMessage = "Hook type is required";
    var result = true;
    var first_error = true;
    // La función noTermIsCompleted() devuelve true si en ninguna pestaña de términos se han llenado los campos
    // correctamente y false si alguna está completa
    function noTermIsCompleted() {
        var incomplete = true;
        $(".tabterms").each(function() {
            var description = $(this).find(".description-translation");
            var details = $(this).find(".details-translation");
            var terms = $(this).find(".terms-translation");
            if(
                (description.val() != "" && description.val().length > 2) &&
                (details.val() != "" && details.val().length > 2) &&
                (terms.val() != "" && terms.val().length > 2)
            )  {
                console.log("hay uno que es válido");
                incomplete = false;
            } else {
                console.log("este no valida");
            }
        });

        // Si no se encuentra ninguna pestaña completa entonces imcomplete true
        return incomplete;
    }
    function validateConfiguration(status) {

        function checkVisibility() {
            if(status == "inactive") {
                $("#configuration-tab-link").click();
            }
        }

        // Configuration
        // Nombre de la oferta
        if($("#id-value").val()==""){
            $("#offer-name").parent().removeClass("data_error").find("span").remove();
            if ($.trim($("#offer-name").val()) == "" || $("#offer-name").val().length < 2) {
                var parent = $("#offer-name").parent();
                parent.addClass("data_error")
                    .append("<span>" + offerNameMessage + "</span>");
                console.log("offerNameMessage>>>" + offerNameMessage);
                checkVisibility();
                if (first_error) {
                    focusCursor($("#offer-name"));
                    first_error = false;
                }
                result = false;
            }

        }

        //Destinos de la oferta
        $(".selectize-input").parent().removeClass("data_error").find("span").remove();
        if ($(".selectize-input .item").length < 1) {
            $(".selectize-input").parent()
                .addClass("data_error")
                .append("<span>" + destinationMessage + "</span>");
            checkVisibility();
            if (first_error) {
                focusCursor($(".selectize-input"));
                first_error = false;
            }

            result = false;
        }

        //Noches de la oferta
        $("#offer-nights").parent().removeClass("data_error").find("span").remove();
        if ($.trim($("#offer-nights").val()) == "") {
            $("#offer-nights").parent()
                .addClass("data_error")
                .append("<span>" + offerNightsMessage + "</span>");
            checkVisibility();
            if (first_error) {
                focusCursor($("#offer-nights"));
                first_error = false;
            }

            result = false;
        }

        //Descripción de la oferta
        $("#offer-description").parent().removeClass("data_error").find("span").remove();
        if ($.trim($("#offer-description").val()) == "" || $("#offer-description").val().length < 2) {
            $("#offer-description").parent()
                .addClass("data_error")
                .append("<span>" + descriptionMessage + "</span>");
            checkVisibility();
            if (first_error) {
                focusCursor($("#offer-description"));
                first_error = false;
            }

            result = false;
        }

        //Hook Type
        $("#hook").removeClass("data_error").find("span").remove();
        if (typeof $("#hook input[type=radio]:checked").val() == "undefined") {
            $("#hook")
                .addClass("data_error")
                .append("<span>" + hookMessage + "</span>");
            checkVisibility();
            if (first_error) {
                focusCursor($("#hook"));
                first_error = false;
            }

            result = false;
            configurationError = true;
        }
        return result;
    }

    function validateTerms(status) {
        console.log("Inicio de la función validateTerms()");
        function checkVisibility() {
            if(status == "inactive") {
                $("#terms-tab-link").click();
                console.log("check visibility");
            }
        }


        //Descripción en Inglés
        $("#language-tab1 .description-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab1 .description-translation").val()) == "" ||
            $("#language-tab1 .description-translation").val().length < 2) {

            $("#language-tab1 .description-translation").parent()
                .addClass("data_error")
                .append("<span>"+descriptionMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab1 .description-translation"));
                first_error = false;
            }

            result = false;
        }

        //Detalles en Inglés
        $("#language-tab1 .details-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab1 .details-translation").val()) == "" ||
            $("#language-tab1 .details-translation").val().length < 2) {

            $("#language-tab1 .details-translation").parent()
                .addClass("data_error")
                .append("<span>"+detailsMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab1 .details-translation"));
                first_error = false;
            }

            result = false;
        }

        //Términos en Inglés
        $("#language-tab1 .terms-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab1 .terms-translation").val()) == "" ||
            $("#language-tab1 .terms-translation").val().length < 2) {

            $("#language-tab1 .terms-translation").parent()
                .addClass("data_error")
                .append("<span>"+termsMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab1 .terms-translation"));
                first_error = false;
            }

            result = false;
        }

        //Descripción en Español
        $("#language-tab2 .description-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab2 .description-translation").val()) == "" ||
            $("#language-tab2 .description-translation").val().length < 2) {

            $("#language-tab2 .description-translation").parent()
                .addClass("data_error")
                .append("<span>"+descriptionMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab2 .description-translation"));
                first_error = false;
            }

            result = false;
        }

        //Detalles en Español
        $("#language-tab2 .details-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab2 .details-translation").val()) == "" ||
            $("#language-tab2 .details-translation").val().length < 2) {

            $("#language-tab2 .details-translation").parent()
                .addClass("data_error")
                .append("<span>"+detailsMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab2 .details-translation"));
                first_error = false;
            }

            result = false;
        }

        //Términos en Español
        $("#language-tab2 .terms-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab2 .terms-translation").val()) == "" ||
            $("#language-tab2 .terms-translation").val().length < 2) {

            $("#language-tab2 .terms-translation").parent()
                .addClass("data_error")
                .append("<span>"+termsMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab2 .terms-translation"));
                first_error = false;
            }

            result = false;
        }

        //Descripción en Portugués
        $("#language-tab3 .description-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab3 .description-translation").val()) == "" ||
            $("#language-tab3 .description-translation").val().length < 2) {

            $("#language-tab3 .description-translation").parent()
                .addClass("data_error")
                .append("<span>"+descriptionMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab3 .description-translation"));
                first_error = false;
            }

            result = false;
        }

        //Detalles en Portugués
        $("#language-tab3 .details-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab3 .details-translation").val()) == "" ||
            $("#language-tab3 .details-translation").val().length < 2) {

            $("#language-tab3 .details-translation").parent()
                .addClass("data_error")
                .append("<span>"+detailsMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab3 .details-translation"));
                first_error = false;
            }

            result = false;
        }

        //Términos en Portugués
        $("#language-tab3 .terms-translation").parent().removeClass("data_error").find("span").remove();
        if(
            $.trim($("#language-tab3 .terms-translation").val()) == "" ||
            $("#language-tab3 .terms-translation").val().length < 2) {

            $("#language-tab3 .terms-translation").parent()
                .addClass("data_error")
                .append("<span>"+termsMessage+"</span>");
            checkVisibility();
            if(first_error) {
                focusCursor($("#language-tab3 .terms-translation"));
                first_error = false;
            }

            result = false;
        }
        return result;
    }


    var activeTab;

    if($("li.active #configuration-tab-link").length == 1){
        activeTab = "configuration";
    } else if($("li.active #terms-tab-link").length == 1) {
        activeTab = "terms";
    } else {
        console.error("Ha ocurrido un error.");
    }

    if(activeTab == "configuration") {
        console.log("entrando a validación de configuration");
        if(validateConfiguration("active")) {
            console.log("validateConfiguration(active)>>>" + validateConfiguration("active"));
            if(noTermIsCompleted()) {
                validateTerms("inactive");
            }
        }

    } else if (activeTab == "terms") {
        console.log("entrando a validación de terms");
        if(noTermIsCompleted()) {
            validateTerms("active");
        } else {
            validateConfiguration("inactive");
        }
    }
    return result;
}

// La función ejecutar se invoca cuando la petición responde
function ejecutar(edit) {
    //var stuff = $('#select-destination').data('stuff');
    var itemValues;

    // Aquí definimos si habrá valores iniciales en selectize
    if(edit) {
        itemValues = $('#select-destination').data('itemValues');
    } else {
        itemValues = 0;
    }

    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/destinations'},
        error: function (err) {
            console.log(err);
        },
        success: function (data) {
            data= $.parseJSON(data);
            var select = $('#select-destination');
            var destinationHTML = '';
            if (data.length > 0) {
                $.each(data, function(i, destination) {
                    console.log(destination);
                    destinationHTML += '<option value="' + destination.id + '">' + destination.name + '</option>';
                }); // end each
                select.html(destinationHTML);
            } else {
                console.log("No destinations found");
            }
            select.selectize({
                plugins: ['remove_button'],
                items: itemValues,
                maxItems: 100
            });
        }
    });
}; // termina la función ejecutar

$(document).ready(function () {
    var idValue = $("#id-value").val();
    var instanceId = (idValue == "") ? "" : "/" + idValue;
    var createOfferURL = "/createOffer" + instanceId;
    var lastTermsEdited = "#english-tab";

    $("#meal-plan").on('change', function() {
        var meal=$(this).val();
        if(meal==4)
            $("#offer-price").closest(".col-lg-4").hide();
        else
            $("#offer-price").closest(".col-lg-4").show();
    });

    $("#offer-nights").keyup(function() {
        var length    = $(this).val().length;
        var maxLength = parseInt($(this).attr("maxlength"));

        if(length >= maxLength) {
            $(this).val(truncateString($(this).val(), 2));
            return false;
        }
    });

    $(".tabterms textarea").change(function() {
        var tabId = $(this).parent().parent().parent().attr("id");
        lastTermsEdited = "#" + tabId + "-tab";
    });

    $('#createOffer').submit(function (evt) {
        evt.preventDefault();
        if(formValidate()) {
            var selectize = "";
            selectize += "[";
            if($('.selectize-input div').length > 0) {
                $('.selectize-input div').each(function(index) {
                    console.log("index>>>" + index);
                    console.log( "$(this).length)>>>" + $('.selectize-input div').length );
                    if(index + 1 == $(this).length) {
                        //console.log("(index >>>" + index );

                    }
                    var separation = (index + 1 == $('.selectize-input div').length) ? "" : ", ";
                    selectize += ($(this).data("value") + separation);

                });
            }

            if(idValue=="")
                offerName=$("#offer-name").val();
            else
                offerName=$("#offer-name").html();

            selectize += "]";
            console.log("selectize>>>" + selectize);
            var $submitButton = $('#submit');
            console.log(Encode($("#language-tab1 .description-translation").val()));
            var data = {
                name: Encode($('#offer-name').val()),
                mealPlanId: getInt($('#meal-plan').val()),
                price: getInt( $('#offer-price').val() ),
                activationFee: getInt($('#activation-fee').val()),
                taxes: getInt($('#offer-taxes').val()),
                //countryCode: $('#country-code').val(),
                countryCode: "USA",
                destinationIds: selectize,
                status: $(".status:checked").val(),
                transportationId: $('#offer-transportation').val(),
                description: Encode($("#offer-description").val()),
                hookId: $("[name=hook]:checked").val(),
                nights: $('#offer-nights').val(),
                // Valores de los términos en sus diferentes lenguajes
                englishDescription: Encode($("#language-tab1 .description-translation").val()),
                englishDetails: Encode($("#language-tab1 .details-translation").val()),
                englishTerms: Encode($("#language-tab1 .terms-translation").val()),

                spanishDescription: Encode($("#language-tab2 .description-translation").val()),
                spanishDetails: Encode($("#language-tab2 .details-translation").val()),
                spanishTerms: Encode($("#language-tab2 .terms-translation").val()),

                portugueseDescription: Encode($("#language-tab3 .description-translation").val()),
                portugueseDetails: Encode($("#language-tab3 .details-translation").val()),
                portugueseTerms: Encode($("#language-tab3 .terms-translation").val())

            };
            console.log("data>>>" + JSON.stringify(data));
            $submitButton.attr("disabled", true).val("creating....");
            console.log("data>>>");
            console.log(data);
            $.ajax({
                type: "POST",
                url: createOfferURL,
                data: data
            })
            .done(function (msg) {
                var result = JSON.parse(msg);
                if(result.responsestatus==200 || result.responsestatus==201){
                    //console.log(result)
                    $("#redirect input[name=offerName]").val(offerName);
                    $("#id-value").val(result.id);
                    $("#redirect").submit();
                    $submitButton.attr("disabled", false).val("Save");
                } else if(formValidate()) {
                    if(result.message)
                        showError(result.message);
                    else
                        showError("An error occured. Please try again");
                    //for(var i=0;i<result.errors.length; i++) {
                    //    errorMessage += result.errors[i] + "\n";
                    //}
                    //alert(errorMessage);
                    $submitButton.attr("disabled", false).val("Save");
                }


            })
            .error(function (err) {
                showError("Error: " + err);
                $submitButton.attr("disabled", false).val("Save");
            }); // end ajax
        } else {
            $(lastTermsEdited).click();

        }

    }); // end click

}); // end ready