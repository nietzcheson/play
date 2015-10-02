/**
 * Created by desarrollo1 on 03/08/2015.
 */
$(document).ready(function (){
    $( document ).on( "click", "a.delete-img", function(e){
        var papa= $(this).closest(".col-sm-6");
        var id=$("#id-value").val();
        var token= $(this).attr("data-token");
        e.preventDefault();
        $.ajax({
            url: '/application/deleteImghotel/'+id+'/'+token+'/',
            type: 'POST',
            success: function(msg){
                var result = JSON.parse(msg);
                if(result.message != undefined){
                    showError(result.message);
                }else{
                    $(papa).remove();
                }
            }, error: function(result){
                showError(result.responseJSON.message);
            },
            processData: false,
            contentType: false
        } );
    });

    $( document ).on( "click", "a.flaticon-microsoft-excel", function(e){
        $(this).append($('<img/>', {
                'src': 'images/ajax-loader.gif'
            })
        );
    });

    $('#hotel-form').submit(function (evt){
        console.log("Hotel Form");
        evt.preventDefault();
        var idValue = $("#id-value").val();
        var instanceId = (idValue == "") ? "" : "/" + idValue;
        var createOfferURL = "/createHotel" + instanceId;
        var form= formValidate($("#form-hotel-part"));
        var content =formTerms();
        var url=$("#hotel-url").val();
        var validurl= isUrl(url);
        if(!validurl){
            $("#hotel-url").parent().find("span").remove();
            $("#hotel-url").parent()
                .addClass("data_error")
                .append("<span>Url invalid</span>")
                .find("em").hide();
        }
        if(content && !(form&&validurl)){
            $(".tabs-main li").eq(0).find("a").click();
        }else if((form&&validurl) && !content){
            $(".tabs-main li").eq(1).find("a").click();
        }else if((form&&validurl) && content){
            var $submitButton = $('#submit');
            var data = {
                name: Encode($('#hotel-name').val()),
                destinationId: getInt($("#hotel-destination").val()),
                city: Encode($("#hotel-city").val()),
                zipCode: Encode($("#hotel-zip").val()),
                address: Encode($("#hotel-address").val()),
                website: Encode($("#hotel-url").val()),
                englishDescription: Encode($(".description-translation:eq(0)").val()),
                spanishDescription: Encode($(".description-translation:eq(1)").val()),
                portugueseDescription: Encode($(".description-translation:eq(2)").val())
            };
            $submitButton.attr("disabled", true).val("creating....");
            $.ajax({
                type: "POST",
                url: createOfferURL,
                data: data
            })
                .done(function (msg) {
                    var result = JSON.parse(msg);
                    console.log(result);
                    if(result.responsestatus==200 || result.responsestatus==201){
                        if($(".tabs-main li:eq(2)").is(":visible")){
                            $("#redirect input[name=hotelName]").val($("#hotel-name").val());
                            console.log("result");
                            console.log(result);
                            $("#redirect").submit();
                        }else{
                            $(".tabs-main li:eq(2)").show().find("a").click();
                            $submitButton.attr("disabled", false).val("Save");
                            $("#id-value").val(result.id);
                        }
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
    });

    $(".tabs-main li a").click(function(){
        var eq=$(this).parent("li").index();
        if(eq==2)
            $("#submit").parent("div").hide();
        else
            $("#submit").parent("div").show();
    });
});
