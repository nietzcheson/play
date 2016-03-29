/**
 * Created by desarrollo1 on 06/04/2015.
 */
$(document).ready(function (){
    var dd_cal, year, month, day, num_year, num_month, num_days, i;
    var currentYear = (new Date).getFullYear();

    year = $('.year');
//    year.html("");
    dd_cal = year.closest(".form-group");
    month = dd_cal.find($('.month'));
    day = dd_cal.find($('.day'));
//    year.append('<option value="0">Year</option>');
    for (i = currentYear-18; i >currentYear-80; i--) {
        year.append('<option value=' + i + '>' + i + '</option>');
    }
    for (i = 1; i <=31; i++) {
        day.append('<option value=' + i + '>' + i + '</option>');
    }
    $("#age").keyup(function(e){
        var age=$(this).val();
        if(age.length>1){
            if(age>17 && age<80){
                var year = new Date().getFullYear();
                $("#year").val(year-age);
            }else{
                $("#age").val("");
                showError("Invalid Age");
            }
        }
    });

    $("#phone").mask("(999) ?999-9999");
    $("#countries").on('change', function() {
        var country=$(this).val();
        load_states(country);
    });
    $( "#birthdate" ).datepicker({
        changeMonth: true,
        changeYear: true,
        defaultDate: "-35Y",
        minDate: "-70Y",
        maxDate: "-18Y +5M"
    });

    $('body').on('change', '.month, .year, .day', function() {
        if($("#year").val()==0){
            $("#year").val(currentYear-18);
        }
        num_year = year.val();
        day_ant=day.val();
        if(!$(this).hasClass("day")){
            num_month = 13 - month[0].selectedIndex;
            num_days = Math.round(((new Date(num_year, num_month)) - (new Date(num_year, num_month - 1))) / 86400000);
            day.children('option:not(:first-child)').remove();
            for (i = 1; i <= num_days; i++) {
                day.append('<option value=' + i + '>' + i + '</option>');
            }
            if(num_days>day_ant)
                day.val(day_ant);
        }
        var d = new Date();
        var month_c = d.getMonth()+1;
        var year_c = d.getFullYear();
        var day_c = d.getDate();
        var data_day=$(".day").val();
        var data_month=$(".month").val();
        if((data_month > month_c) ||(data_month==month_c && (data_day > day_c || data_day==undefined)) ){
            age=year_c-num_year-1;
        }else{
            age=year_c-num_year;
        }
        $("#age").val(age);
    });

    $("#offer h3").click(function (e) {
        e.preventDefault();
        if($('#offer p:visible').length){
            $('#offer p').fadeOut();
            $(this).find("a>span").removeClass("glyphicon glyphicon-menu-up").addClass("glyphicon glyphicon-menu-down");
        }else{
            $('#offer p').fadeIn();
            $(this).find("a>span").removeClass("glyphicon glyphicon-menu-down").addClass("glyphicon glyphicon-menu-up");
        }
    });
    var arr = [
        {val : "phone", text: 'HOME'},
        {val : "phone2", text: 'CELLPHONE'},
        {val : "phone3", text: 'WORK'}
    ];
    var div;
    var div2;
    var type_phones=[];
    var newarray=[];
    $("#add-phone").click(function(e){
        //Agregar telefono
        var size=$( "#phones .col-sm-4").size()
        console.log(arr);
        if(size<3){
            var select_phone = $('<select />', {'class': 'form-control', 'id': 'type-phone'+size, 'name': 'type-phone'+size});
            var input=$("<input/>",{'class':'form-control', 'id':'phone'+size,'name':'phone'+size});
            $(arr).each(function() {
                select_phone.append($("<option>").attr('value',this.val).text(this.text));
            });
            div=$("<div/>",{'class':'col-sm-4'}).append(select_phone);
            div2=$("<div/>",{'class':'col-sm-8'}).append(input);
            div3=$("<div/>",{'class':'clearfix'});
            var array1 = ["phone","phone2", "phone3"];
            type_phones = [];
            $("#phones select").each(function (index, value) {
                if(size==2)
                    $(this).prop('disabled', 'disabled');
                var val=$(this).val();
                type_phones.push($(this).val());
                array1=$.grep(array1, function(value) {
                    return value != val;
                });
            });
            $("#phones").append(div3, "<br/>", div, div2);
            $("#type-phone"+size).val(array1[0]);
            if(size==2)
                $("#type-phone"+size).prop('disabled', 'disabled');
            type_phones.push(array1[0]);
            console.log(array1);

        }else{
            showError("You can only add 3 phones");
        }
        $("[id^=phone]").mask("(999) ?999-9999");
    });

    $(document).on('change', '#phones select', function() {
        var size=$("#phones select").size();
        newarray=[];
        var newval=$(this).val();
        cont=0;
        if(size==2){
            $("#phones select").each(function (){
                newarray.push($(this).val());
                if(newval==$(this).val()){
                    cont++;
                }
            });
            if(cont==2){
                //pos=$(this).index();
                pos=$("#phones select").index(this);
                $(this).val(type_phones[pos]);
            }else
                type_phones=newarray;
        }
    });
    var idValue = $("#id-value").val();
    //var instanceId = (idValue == "") ? "" : "/" + idValue;
    if(idValue!=""){
        $.ajax({
            url:  '/application/leadslist/' + idValue,
            type:'GET',
            success:function(result){
                var result =  $.parseJSON(result);
                console.log(result);
                $("#firstname").val(Decode(result.firstName));
                $("#lastname").val(Decode(result.lastName));
                $("#certificate").closest(".form-group").remove();
                $("#email").val(Decode(result.email));
                $("#email2").val(Decode(result.email2));
                d= new Date(result.birthdate);
                da= d.getUTCMonth()+"/"+d.getUTCDate()+"/"+d.getUTCFullYear();
                $("#birthdate").val(da);
                $("#year").val(result.dobyear);
                $("#month").val(result.dobmonth);
                $("#day").val(result.dobday);
                //$("#day").val(da);
                if(result.maritalStatus)
                    load_marital(result.maritalStatus.id);
                else
                    load_marital();
                if(result.state)
                    state=result.state.id
                else
                    state=null;
                $("#city").val(Decode(result.city));
                $("#zipcode").val(Decode(result.postalCode));
                $("#street").val(Decode(result.address));
                lim=$("#street").attr("maxlength");
                $("#cont").html(lim-count_text($("#street")));
                $("#occupation").val(Decode(result.occupation));
                if(result.country)
                    country=result.country.code;
                else
                    country="";
                load_countries(country, state);
                $("#age").val(result.age);
                if(result.title)
                    load_title(result.title.id);
                else
                    load_title();
                arrf=[];
                if(result.featuresesLst){
                    $.each(result.featuresesLst, function(index, value){
                        arrf.push(value.customerFeaturesId.features.id)
                    });
                }
                load_feature(arrf);
                var array_phone = ["phone","phone2", "phone3"];
                $.each([result.phone1, result.movil, result.phone3], function(index, value){
                    if(value != null  ){
                        ind=$("#phones select").length;
                        if($("#phones input").eq(ind-1).val()==""){
                            $("#phones input").eq(ind-1).val(value);
                            $("#phones select").eq(ind-1).val(array_phone[index]);
                        }else{
                          $("#add-phone").click();
                            $("#phones input").eq(ind).val(value);
                            $("#phones select").eq(ind).val(array_phone[index]);
                        }
                    }
                });
            },
            error: function(err){
                showError("error" + err);
            }
        });
    }else{
        load_title();
        load_marital();
        load_feature();
        load_countries();
    }
    $(document).on('click', '#info-campaign a:eq(1), #info-certificate a:eq(1)', function() {
        var offer=$(this).closest(".info-certificate").find("p.offer").html();
        $("#offer-details .modal-body").html(Decodehtml(offer));
    });
    $(document).on('click', '#info-campaign a:eq(0), #info-certificate a:eq(0)', function() {
        var terms=$(this).closest(".info-certificate").find("p.terms").html();
        $("#terms .modal-body").html(Decodehtml(terms));
    });

    $('#lead-form').submit(function (evt){
        evt.preventDefault();
        var idValue = $("#id-value").val();
        var instanceId = (idValue == "") ? "" : "/" + idValue;
        var createOfferURL = "/createLead" + instanceId;
        var validcertificate;
        var certificate=$("#valid-certificate").val();
        var campaign=$('.selectpicker').find("option:selected").val();
        if(idValue!="" || campaign || certificate!="")
            validcertificate=true;
        else
            validcertificate=false;
        var valid_phone=validphone();
        if(!validcertificate)
            showError("Please Enter a valid certificate");
        else if(!valid_phone)
            showError("Please Enter a phone");
        else{
            var form= formValidate($("#lead-form"));
            if(form && valid_phone){
                var $submitButton = $('#submit');
                var myform= $(this).closest("form");
                var disabled = myform.find(':input:disabled').removeAttr('disabled');
                var data= $(this).closest("form").serialize();
                var street=Encode($("#street").val());
                var interested = $("#features input:checkbox:checked").map(function(){
                    return $(this).val();
                }).get();

                interested=interested.join(", ");
                var data = {
                    title: $("#title").val(),
                    firstname: Encode($('#firstname').val()),
                    lastname: Encode($("#lastname").val()),
                    //birthdate: $("#birthdate").val(),
                    year: $("#year").val(),
                    month: $("#month").val(),
                    day: $("#day").val(),
                    certificate: certificate,
                    campaign: $('.selectpicker').find("option:selected").val(),
                    phone: $("#phone").val(),
                    type_phone: $("#type-phone").val(),
                    phone1: $("#phone1").val(),
                    type_phone1: $("#type-phone1").val(),
                    phone2: $("#phone2").val(),
                    type_phone2: $("#type-phone2").val(),
                    email: Encode($("#email").val()),
                    email2: Encode($("#email2").val()),
                    countries: $("#countries").val(),
                    states: $("#states").val(),
                    city: Encode($("#city").val()),
                    zipcode: Encode($("#zipcode").val()),
                    occupation: Encode($("#occupation").val()),
                    //age: $("#age").val(),
                    street: Encode($("#street").val()),
                    status: $("#marital input:checked").val(),
                    features: interested

                };
                disabled.attr('disabled','disabled');
                console.log("se envía:");
                console.log(data);
                $submitButton.attr("disabled", true).val("creating....");
                $.ajax({
                        type: "POST",
                        url: createOfferURL,
                        //data: data+ '&features='+interested+ '&street='+street
                        data: data
                    })
                    .done(function (msg) {
                        var result = JSON.parse(msg);
                        console.log("result>>>");
                        console.log(result);
                        if(result.responsestatus==200 || result.responsestatus==201){
                            $submitButton.attr("disabled", false).val("Save");
                            $("#redirect").attr("action", "/clients/"+result.id);
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
                        showError(err);
                        $submitButton.attr("disabled", false).val("Save");
                    }); // end ajax
            }
        }

    }); // end click


});
