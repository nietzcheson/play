/**
 * Created by Orlando on 12/03/2015.
 */

// Esta función también debería estár dentro de un archivo functions.js
function focusCursor(control){
    $('body,html').animate({
        scrollTop: $(control).offset().top - 30
    }, 800);

    $(control).focus();
}
// Verifica que ambos campos de password coincidan y tengan al menos 6 caracteres de longitud
function passwordLength(minLength) {
    var userPassword             = $.trim($("#password").val());
    var userPasswordLength       = userPassword.length;
    var repeatUserPassword       = $.trim($("#repeat-password").val());
    var repeatUserPasswordLength = repeatUserPassword.length;
    return ( userPasswordLength >= minLength && repeatUserPasswordLength >= minLength ) && ( userPassword == repeatUserPassword );
}

function validatePassword(mode){
    var userPassword  = $.trim($("#password").val());
    if(mode == "create" || userPassword.length>=1 || $("#pass_oculto").val()==""){
        // Validamos password
        $("#password").closest(".form-group").removeClass("data_error").find("span").remove();
        $("#repeat-password").closest(".form-group").removeClass("data_error").find("span").remove();
        if(!passwordLength(6)) {
            var passwordErrorMessage;
            $("#password").closest(".form-group")
                .addClass("data_error")
                .append("<span>Password must be at least six characters length and match with repeat password </span>");
            $("#repeat-password").closest(".form-group")
                .addClass("data_error");
            return false;
        }
    }
    return true;
}
function formValidate(mode){
    var result=resp = true;
    result=validateInputText($("#user-form"));
    resp= validatePassword( mode);
    return (result && resp);
}

$(document).ready(function () {
    // Tomamos el valor del id que el controller pinta en el html
    var idValue = $("#id-value").val();
    var mode = $("#mode").val();
    var instanceId = (idValue == "") ? "" : "/" + idValue;
    var createUserURL = "/createUser" + instanceId;
    var userNameValue = "";
    var buttonpressed;

    $("#username").keydown(function(evt) {
        if (evt.keyCode == 32) {
            return false;
        }
    });
    $('#submit-save').click(function() {
        buttonpressed = "save";
    });

    $('#submit').click(function() {
        buttonpressed = "saveAndCreate";
    });


    if(idValue != "") {
        $.ajax({
            url:  '/application/userslist/' + idValue,
            type:'GET',
            success:function(result){
                console.log("result >>>" + result);
                var result =  $.parseJSON(result);
                $("#name").val(result.responsableName);
                $("#redirect [name=name]").val(result.responsableName);
                $("#redirect [name=id]").val(result.id);
                $("#company").val(result.companyName);
                $("#billing").val(result.invoiceAddress);
                $("#phone").val(result.phone);
                $("#city").val(result.city);
                if(result.user){
                    $("#username").val(result.user.user).prop('readonly',true);
                    $("#email").val(result.user.email);
                    $("#pass_oculto").val(result.user.password);
                }
                var li, a="";
                if(result.campaigns){
                    $.each(result.campaigns, function(index, value){
                        a=$("<a/>",{'href': '/campaigns/'+value.id});
                        li=$("<li/>", {'text': value.name});
                        $("#campaigns").append(a.append(li));
                    });
                }
                if(result.stateId)
                    state=result.stateId
                else
                    state=null;
                load_countries(result.country, state);
                $(".page-header h1").html("Edit-"+result.responsableName);
            },
            error: function(err){
                showError("error" + err);
            }
        });
        $("#redirect input[name=mode]").val("edit");
    } // end if



    $("#user-form").submit(function(evt) {

        // Detenemos el envío
        evt.preventDefault();

        // Validamos que los campos hayan sido capturado correctamente
        var form= formValidate(mode);
        console.log(form);
        if(form){
            var idValue = $("#id-value").val();
            var instanceId = (idValue == "") ? "" : "/" + idValue;
            var createOfferURL = "/createUser" + instanceId;
            var $submitButton = $('#submit');
            var mode=$("#mode").val();
            var password=$("#password").val();
            if(password==""){
                password=$("#pass_oculto").val();
            }
            var data = {
                companyName: Encode($('#company').val()),
                responsableName: Encode($("#name").val()),
                address: Encode($("#billing").val()),
                phone: $("#phone").val(),
                countryCode: $("#countries").val(),
                stateId: $("#states").val(),
                city: Encode($("#city").val()),
                user: Encode($("#username").val()),
                password: password,
                //pass: $("#pass_oculto").val(),
                email: Encode($("#email").val()),
                id: idValue
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
                .done(function (msg, textStatus, xhr) {
                    console.log(xhr.status)
                    var result = JSON.parse(msg);
                    console.log(result);
                    if(result.responsestatus==200 || result.responsestatus==201){
                        console.log(mode);
                        if(mode=="create" && buttonpressed == "saveAndCreate"){
                            $("#redirect").attr("action", "/campaigns-masterbroker/"+result.id);
                            //$("#redirect").attr("method", "GET");
                        }
                        $("#redirect").submit();
                    }else{
                        if(result.message)
                            showError(result.message);
                        else
                            showError("An error occured. Please try again");
                        $submitButton.attr("disabled", false).val("Save");
                    }
                })
                .error(function (err) {
                    console.log("Error: " + err);
                    $submitButton.attr("disabled", false).val("Save");
                }); // end ajax
        }
        //if(formValidate(mode)) {
        //    // Variable que representa al botón Save
        //    var $submitButton = $('#submit');
        //    var userNameValue = $("#user-userName").val();
        //    var userNameInit  = $("#user-name").data("init");
        //    console.log("userNameValue>>>" + userNameValue);
        //    console.log("$>>>" + $("#user-name").val());
        //
        //    // Parametros post que serán enviados por ajax
        //    var data = {
        //        idValue: idValue,
        //        perfil:     $("input[name=user-role]:checked").val(),
        //        userName:   userNameValue,
        //        userNameInit:   userNameInit,
        //        name:       $('#user-name').val(),
        //        email: $('#user-email').val(),
        //        password: $('#user-password').val(),
        //        isActivated: 1,
        //        tokenUser: "TSR33D19"
        //    };
        //
        //    // Deshabilitamos el botón Save y cambiamos el texto
        //    $submitButton.attr("disabled", true).val("creating....");
        //
        //    // Realizamos el envío del formulario con ajax
        //    $.ajax({
        //        type: "POST",
        //        url: createUserURL,
        //        data: data
        //    })
        //    .done(function (msg) { // Cuando termine el envío por ajax y se tenga una respuesta
        //        // Convertimos el string recibido en un objeto de javascript
        //        var result = msg;
        //            console.log("result>>>" + result);
        //        // Aquí evaluamos si hay un error
        //        if(result.message != undefined) {
        //            console.log(result.message.length);
        //            alert(result.message);
        //            //$submitButton.attr("disabled", false).val("Save");
        //        } else {
        //            // Tomamos lo que el usuario escribió en el campo name
        //            // Y asignamos el valor al input con nombre destinationName
        //            // Para que el nombre del usuario llegue al listado como parámetro post
        //            // para mostrarlo en la notificación de éxito.
        //            $("#redirect input[name=name]").val($("#user-name").val());
        //            // Para cuando llegamos a esta parte del código la creación o edición ya
        //            // está hecha. Solamente se envía este formulario para que al regresar al
        //            // listado se muestre la notificación conteniendo el nombre del nuevo usuario.
        //            $("#redirect").submit();
        //        }
        //    })
        //    .error(function (error) { // Si hay un error
        //        // Restablecemos el texto del botón save
        //            alert("error");
        //        $submitButton.attr("disabled", false).val("Save");
        //        console.log("error >>>" + error);
        //    }); // end ajax
        //}
    }); // end click
}); // end ready
