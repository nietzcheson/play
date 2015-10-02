/**
 * Created by Orlando on 01/04/2015.
 */
function validateCertCode(element) {
    if(element.val() == "correcto") {

        alert("correcto");
    } else {

    }
}

$(document).ready(function () {
    $(document).on("click", "#add-sale-button", function (event) {
        event.preventDefault();
        $("#add-sale-container").slideToggle("600");
    });
    $(document).on("click", "#new-offer-link", function (event) {
        event.preventDefault();
        $("#new-offer-details").slideToggle("600");
    });
    $(document).on("click", "#toggle-sale-information", function (event) {
        event.preventDefault();
        $("#information-container").slideToggle("600");
    });
    $(document).on("click", "#toggle-personal-information", function (event) {
        event.preventDefault();
        $("#personal-information-container").slideToggle("600");
    });
    $( "#certificate-code" ).keyup(function() {
        var length = $(this).val().length;
        if(length >= 8) {
            console.log("length");
            validateCertCode($(this));
        }
    });
});