/**
 * Created by desarrollo1 on 10/08/2015.
 */
var days = ['Sun', 'Mon', 'Tues', 'Wed', 'Thurs', 'Fri', 'Sat'];
var months = ['January', 'February', 'March', 'May', 'June', 'July', 'August','September', 'October', 'November','December'];

//Cuando se seleccione algun servicio
$( document ).on( "change", "#payment tbody td input:checkbox", function(e){
    //Si seleccionan un servicio
    if($(this).is(":checked")) {
        importe_total=$("#payment .tag span").text();
        sum=$("#payment tbody tr").sumaEle();
        //Obtener el saldo del servicio como default
        //saldo=$(this).closest("tr").find("td:eq(4)").text().replace("$ ", "");
        saldo=$(this).attr("data-importe");
        //En caso de que el saldo sea mayor al importe a pagar, entonces obtener el importe sobrante de acuerdo a los servicios seleccionados
        console.log("Saldo: ", saldo);
        if(saldo > (importe_total-sum) )
            saldo=(importe_total-sum);
        console.log(" Saldo: "+saldo+" Sum: "+sum+" Importe Total: "+importe_total);
        if(saldo ==0 ){
            $(this).prop("checked", false);
            showModalError($("#payment"), "El importe ingresado ha sido ocupado");
        }

        else{
            //Dejar cambiarlo por un monto menos
            $(this).closest("tr").find("td:eq(5) input").prop("readonly", false).val(saldo);
        }
    }else{
        //En caso de deseleccionarlo quitar el monto y no dejar editar.
        $(this).closest("tr").find("td:eq(5) input").prop("readonly", true).val(0);
    }
});

//Cuando se seleccione algun subservicio
$( document ).on( "change", "#servicios", function(e){
    var amount=$('option:selected', $(this)).attr('data-amount');
    console.log(amount);
    if(amount){
        $("#amount_service").val(amount);
    }
});

//$( document ).on( "change", "#tarjeta_pago", function(e){
//    var result=$(this).validateCreditCard({accept:['visa','mastercard','visa_electron',
//        'amex','jcb','discover','diners_club_carte_blanche','diners_club_international']});
//
//    $(".alert-danger, .alert-success").remove();
//    if(!result.valid)
//        showModalError($("#payment"), "Tarjeta no válida ");
//});

//Cuando cambie el importe de un servicio
$( document ).on( "change", "#payment tbody td input:text", function(e){
    // Importe a pagar del servicio
    importe= parseFloat($(this).val());
    // Saldo del servicio
    saldo=parseFloat($(this).closest("tr").find("td:eq(4)").text().replace("$ ", ""));
    //Importe total en dolares
    importe_total=$("#payment .tag span").text();
    //Suma del importe de los servicios que cubrira el pago
    sum=$("#payment tbody tr").sumaEle();
    console.log("Importe: "+importe+" Saldo: "+saldo+" Sum: "+sum+" Importe Total: "+importe_total);
    // Validación de númerico
    if(!$.isNumeric(importe)){
        showModalError($("#payment"), "El importe debe ser númerico");
        $(this).val(saldo);
    }else if (importe>saldo){
        //Validación que sea menor o igual al saldo del servicio
        showModalError($("#payment"), "El importe debe ser menos o igual al saldo");
        $(this).val(saldo);
    }else if(sum > importe_total){
        //Validación de que la suma del servicio no sea mayor al importe a pagar
        showModalError($("#payment"), 'El importe de pago no concide con los pagos de los servicios');
        $(this).val(parseFloat(importe_total-(sum-importe)));
    }
});


$(document).ready(function () {
    //Validar tarjeta
    $('#tarjeta_pago').validateCreditCard(function(result) {
        result.card_type;
        if(result.length_valid){
            //alert(result.card_value);
            $("#tipo_pago").val(result.card_value);
        }else {
            $("#tipo_pago").val(0);
        }
        //var ccnum = $('#tarjeta_pago').val();
        //if(ccnum != ''){
        //    if(result.length_valid){
        //        $("#TTC").val(result.card_value);
        //    }else {
        //        $("#TTC").val(0);
        //    }
        //}
        //else{
        //    $('#TTC option[value="'+$('#TTC_ORIGINAL').val()+'"]').attr("selected", "selected");
        //}
    },{
        accept: ['visa','mastercard','visa_electron','amex','jcb','discover','diners_club_carte_blanche',
            'diners_club_international']
    });

    //Formato fecha
    $('#fecha_pago').datepicker({
        format: 'mm/dd/yyyy',
        beforeShow: function(i) {
            alert("Prueba");
            if ($(i).attr('readonly')) { return false; }
        }
        //startDate: '-10d'
    });

    $("#vencimiento_pago").mask("99/99");
    //Cargar catalogo de Bancos
    load_banks('/banks', $("#terminal_pago"));

    //Cargar catalogo de tipos de tarjetas
    load_catalog('/cardTypes', $("#tipo_pago"));

    //Convertir de dolares a pesos, o de pesos a dolares
    $("#payment .checkbox input").change(function() {
        var exchange=$("#cambio_pago").val();
        data_exchange=$('option:selected', $("#terminal_pago")).attr('data_change');
        if (exchange==1 && data_exchange)
            exchange=$('option:selected', $("#terminal_pago")).attr('data_change');
        if(exchange){
            var importe=$("#importe_pago").val();
            if($(this).is(":checked")) {
                $("#importe_pago").val((importe/exchange).toFixed(2));
                $("#payment .tag span").html((importe/exchange).toFixed(2));
                $("#cambio_pago").val(1).attr('readonly', true);
            }else{
                $("#importe_pago").val((importe*exchange).toFixed(2));
                $("#cambio_pago").val(exchange).attr('readonly', false);
            }
        }
    });

    //Cuando se cambie de tipo de cambio
    $("#terminal_pago").change(function() {
        var importe=$("#payment .tag span").html();
        if(!$("#payment .checkbox input").is(":checked")) {
            exchange=$('option:selected', $("#terminal_pago")).attr('data_change');
            importe=importe*exchange;
            $("#importe_pago").val(importe);
            $("#cambio_pago").val(exchange);
        }
    });

    //Cuando se cambie la terminal
    $("#cambio_pago").change(function() {
        var importe=$("#payment .tag span").html();
        if(!$("#payment .checkbox input").is(":checked")) {
            var exchange=$(this).val();
            importe=importe*exchange;
            $("#importe_pago").val(importe);
        }
    });

    //Cuando se cambie el importe de pago
    $("#importe_pago").change(function() {
        var importe=$("#importe_pago").val();
        if($("#payment .checkbox input").is(":checked")) {
            $("#payment .tag span").html(importe);
        }else{
            var exchange=$("#cambio_pago").val();
            importe=importe/exchange;
            $("#payment .tag span").html(importe);
        }
    });
});