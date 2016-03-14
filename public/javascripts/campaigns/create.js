function formValidate(){
    var campaignNameMessage   = "Campaign name is required";
    var expirationMessage     = "Expiration is required";
    var result                = true;
    var first_error           = true;

    //Nombre de la campaña
    $("#campaign-name").parent().removeClass("data_error").find("span").remove();
    if($.trim($("#campaign-name").val()) == "" || $("#campaign-name").val().length < 4) {
        $("#campaign-name").parent()
            .addClass("data_error")
            .append("<span>"+campaignNameMessage+"</span>");
        if(first_error)  {
            focusCursor($("#campaign-name"));
            first_error = false;
        }
        result = false;
    }

    //Expiración de la campaña
    $("#campaign-expiration").parent().removeClass("data_error").find("span").remove();
    if($.trim($("#campaign-expiration").val()) == "") {
        $("#campaign-expiration").parent()
            .addClass("data_error")
            .append("<span>"+expirationMessage+"</span>");
        if(first_error)  {
            focusCursor($("#campaign-expiration"));
            first_error = false;
        }
        result = false;
    }
    return result;
}

$(document).ready(function () {
    var idValue = $("#id-value").val();
    var createCampaignURL = "/createCampaign";
    //var msValue = $(".ms-parent button span").html();
    var mode=$("#mode").val();

    $( "#offer" ).autocomplete({
        serviceUrl: "/application/offers/search",
        minLength: 2,
        paramName: 'q',
        dataType: "json",
        transformResult: function(response) {
            return {
                suggestions:
                    $.map(response.elements, function(dataItem) {
                        return { value: dataItem.name.toString(), label: dataItem.name.toString() ,id:dataItem.id};
                    })
            }
        },
        onSelect: function(suggestion) {
            $("#offerId").val(suggestion.id);
        }
    });

    $('#broker').change(function() {
        console.log($(this).val());
    }).multipleSelect({
        width: '100%'
    });

    $(document).on("click", ".thumbnail", function (event) {
        $(".thumbnail").removeClass("selected");
        console.log("was here");
        $(this).addClass("selected");
    });

    $("#campaign-expiration").keyup(function() {
        var length    = $(this).val().length;
        var maxLength = parseInt($(this).attr("maxlength"));

        if(length >= maxLength) {
            $(this).val(truncateString($(this).val(), 2));
            return false;
        }
    });

    $('#createCampaign').submit(function (evt) {
        evt.preventDefault();
        var id_master=$("#id-master").val();
        var brokers = $("#broker").multipleSelect("getSelects");
        brokers = stringFormat(brokers);
        if(formValidate()) {
            var $submitButton = $('#submit');
            var typeCert=$(".typeCert:checked").val();
            var data = {
                id: idValue,
                name: Encode($('#campaign-name').val()),
                code: $('#campaign-code').val(),
                expiration: $("#campaign-expiration").val(),
                merchant: $("#merchant").val(),
                callcenter: $("#callcenter").val(),
                offerId: $("#offerId").val(),
                country: $("#country").val(),
                segment: $("#segment").val(),
                description: Encode($("#description").val()),
                reservationGroup: $("#reservationGroup").val(),
                slug: $("#campaignCode").val(),
                //userToken: $("#userToken").val(),
                typeFolio:$(".typeFolio:checked").val(),
                typeCert:$(".typeCert:checked").val(),
                masterBroker: $("#id-master").val(),
                brokers: brokers
            };
            console.log("data>>>");
            console.log(data);
            $submitButton.attr("disabled", true).val("creating....");
            console.log("data>>>");
            console.log(data);
            $.ajax({
                type: "POST",
                url: createCampaignURL,
                data: data
            })
            .done(function (msg) {
                var result = JSON.parse(msg);
                console.log(result);
                if(result.responsestatus==200 || result.responsestatus==201){
                    if (formValidate()) {
                        $("#redirect #id-value").val(result.id);
                        console.log("result.id>>>" + result.id);
                        $("#redirect input[name=campaignName]").val($("#campaign-name").val());
                        if(id_master!="" && mode=="master")
                            $("#redirect").attr("action", "/master-brokers");
                        if(mode=="edit")
                            $("#redirect").submit();
                        else{
                            if(typeCert!=2){
                                $("#redirect").submit();
                            }else{
                                $(".panel-heading ul li").eq(1).removeClass("oculto").find("a").click();
                            }
                        }
                    }
                }else{
                    if(result.message)
                        showError(result.message);
                    else
                        showError("An error occured. Please try again");
                }
            }).error(function (err) {
                console.log("Error: " + err);
                $submitButton.attr("disabled", false).val("Save");
            }); // end ajax
        } else {
            //alert("prueba");
        }

    }); // end click

    $("#img-config").submit(function (event) {
        var id_master=$("#id-master").val();
        event.preventDefault();
        var siteimg=$(".site-image:checked").val();
        var certtype=$(".certtype:checked").val();
        console.log(siteimg);
        if(siteimg==""){
            showError("Select a Cert Images please");
        }else if(!siteimg.match(/(?:jpg|jpeg|PNG|JPG|JPEG|png)$/)){
            showError("File path is not an image!");
        }else{
            var data = {
                siteimg: siteimg,
                certtype: certtype,
                idcam: $("#id-value").val(),
                idconfig: $("#id-config").val()
            };
            $.ajax({
                type: "POST",
                url: '/createConfigImage',
                data: data
            })
                .done(function (msg) {
                    var result = JSON.parse(msg);
                    console.log(result);
                    if(result.responsestatus==200 || result.responsestatus==201){
                        console.log("result.id>>>" + result);
                        $("#redirect input[name=campaignName]").val($("#campaign-name").val());
                        //$submitButton.attr("disabled", false).val("Save");
                        if(id_master!="" && mode=="master")
                            $("#redirect").attr("action", "/master-brokers");
                        $("#redirect").submit();
                            //window.location = "/campaigns";

                    }else{
                        if(result.message)
                            showError(result.message);
                        else
                            showError("An error occured. Please try again");
                    }
                }).error(function (err) {
                    console.log("Error: " + err);
                    $submitButton.attr("disabled", false).val("Save");
                }); // end ajax
        }
    });
    load_master_brokers();

    $("#master-broker").change(function() {
        load_multiple_brokers($(this).val());
    });
}); // end ready