/**
 * Created by desarrollo1 on 10/04/2015.
 */
function formValidate(mode){
    var result=resp = true;
    result=validateInputText($("#broker-form"));
    resp= validatePassword( mode);
    return (result && resp);
}

$(document).ready(function (){
    var idValue = $("#id-value").val();
    var master = $("#master").val();
    var mode = $("#mode").val();
    var instanceId = (idValue == "") ? "" : "/" + idValue;
    if(idValue != "") {
        $.ajax({
            url:  '/TableList',
            type:'POST',
            data: {'url': '/certLogin/'+idValue },
            success:function(result){
                var result =  $.parseJSON(result);
                console.log(result);
                $("#broker-name").val(result.name);
                console.log("campaign",result.campaign);
                if(result.campaign)
                    campaigns=load_campaign(master, result.campaign.id);
                $("#redirect [name=name]").val(result.user);
                $("#redirect [name=id]").val("sss" + result.id);
                $("#broker-email").val(result.email);
                $("#broker-brokerName").val(result.user);
                $("#pass_oculto").val(result.password);
                $("#password").closest("div").find("label").html("Password: "+result.password);
            },
            error: function(err){
                showError("error" + err);
            }
        });
        $("#redirect input[name=mode]").val("edit");
    } // end if

    if(master != "" && idValue=="") {
        campaigns=load_campaign(master);
    }

    $("#broker-form").submit(function(evt) {
        evt.preventDefault();
        var form= formValidate(mode);
        console.log(form);
        if(form){
            var idValue = $("#id-value").val();
            var instanceId = (idValue == "") ? "" : "/" + idValue;
            var createBrokerURL = "/createBroker" + instanceId;
            var $submitButton = $('#submit');
            console.log(createBrokerURL);
            var data=$(this).closest("form").serialize();
            console.log("data>>>" + JSON.stringify(data));
            $submitButton.attr("disabled", true).val("creating....");
            console.log("data>>>");
            console.log(data);
            $.ajax({
                type: "POST",
                url: createBrokerURL,
                data: data+ '&id=' + idValue
            })
                .done(function (msg) {
                    var result = JSON.parse(msg);
                    console.log(result);
                    if(result.responsestatus==200 || result.responsestatus==201){

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
                    if(err.message)
                        showError(err.message);
                    else
                        showError(err);
                    $submitButton.attr("disabled", false).val("Save");
                }); // end ajax
        }
    });
});
