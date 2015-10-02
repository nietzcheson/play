function lengthOk(element) {
    var lengthAttribute = element.attr("data-length");
    if (typeof lengthAttribute !== typeof undefined && lengthAttribute !== false) {
        return (element.val().length >= lengthAttribute)||(element.text().length >= lengthAttribute);
    }
    return true;
}
function validateInputText(form) {
    var result = true;
    form.find("[data-required=true]").each(function () {
        var dataRequired = $(this).attr("data-required");
        var dataLength = $(this).attr("data-length");
        var content = $.trim($(this).val()).length;
        if (dataRequired == "true" && !lengthOk($(this))) {
            // Validamos el nombre del usuario
            $(this).parent().removeClass("data_error").find("span").remove();
            $(this).parent().find("em").show();
            console.log("content es " + ((content < dataLength) ? "menor" : "mayor") + "que dataLength");
            if (content < dataLength) {
                console.log($(this).val().length);
                console.log($(this).attr("id"));
                var label = $(this).closest(".form-group").find("label").html();
                $(this).parent()
                    .addClass("data_error")
                    .append("<span>" + label.replace('*' ,'') + " is required</span>");
                $(this).parent().find("em").hide();

                result = false;
            }
        }
    });

    return result;

}