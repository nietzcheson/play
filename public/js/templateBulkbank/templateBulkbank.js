$(function(){

    $( "#hotel" ).change(function() {
        var hotel=$('option:selected', this).attr("data-hotel");
        load_catalog_bulbank("/hotelUnits?hotelId="+hotel, $("#unit, #split"));
    });
    
    $("#template-form").validate({
        rules: {
            rate:{
                required: true,
                number: true
            }
        },
        submitHandler: function (form) {
            var $submitButton = $('#submit');
            $submitButton.attr("disabled", true).val("creating....");
            data = {
                name: $("#name").val(),
                clubId: $("#hotel").val(),
                unitId: $("#unit").val(),
                splitId: $("#split").val(),
                mealPlanId: $("#meal-plan").val(),
                callCenterId: $("#callCenter").val(),
                adults: $("#adults").val(),
                rate: $("#rate").val(),
                children: $("#children").val(),
                observations: $("#observations").val()
            }
            var url=$("#templateId").val()!="" ? '/templateBulkbank/${template}': '/createTemplate';
            $.ajax({
                url: url,
                type: 'POST',
                data: data,
                success: function(msg){
                    result = JSON.parse(msg);
                    console.log(result);
                    if(result.name){
                        $("#id-value").val(result.id);
                        $("#templateName").val(result.name);
                        $("#redirect").submit();
                    }else{
                        if(result.property){
                            showError(result.message);
                        }else{
                            showError("Hubo un error en la aplicación");
                        }
                    }
                    $submitButton.attr("disabled", false).val("Save");

                }, error: function(result){
                    showError("Hubo un error en la aplicación");
                    $submitButton.attr("disabled", false).val("Save");
                }
            });

        }
    });
});