/**
 * @desc Cargar un listado dentro de un select box
 * @param url: Url del servicio, id: id del select, select: en caso de seleccionar alguno
 */
function load_catalog(url, id, select){
    id.html("");
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        success:function(result){
            var result = JSON.parse(result);
            id.append($('<option>', {
                value: "",
                html : ""
            }));
            $.each(result, function (index, value) {
                if( value.id==select){
                    id.append($('<option>', {
                        value: value.id,
                        html : value.name,
                        selected: 'selected'
                    }));
                }else {
                    id.append($('<option>', {
                        value: value.id,
                        html: value.name
                    }));
                }
            });
        },
        error: function(result){
            showError(result);
        }
    });
}/**
 * @desc Cargar un listado de hoteles dentro de un select box
 * @param url: Url del servicio, id: id del select, select: en caso de seleccionar alguno
 */
function load_sunset_hotels(url, id, select){
    id.html("");
    $.ajax({
        url:  '/bulkBank',
        type:'POST',
        data:{'url': url},
        success:function(result){
            var result = JSON.parse(result);
            id.append($('<option>', {
                value: "",
                html : ""
            }));
            $.each(result, function (index, value) {
                if( value.clubId==select){
                    id.append($('<option>', {
                        value: value.clubId,
                        html : value.name,
                        selected: 'selected',
                        'data-hotel': value.id
                    }));
                    $(".hotel-name").append(value.name);
                }else {
                    id.append($('<option>', {
                        value: value.clubId,
                        html: value.name,
                        'data-hotel': value.id
                    }));
                }
            });
        },
        error: function(result){
            showError("Hubo un error al cargar los hoteles");
        }
    });
}


function load_catalog_bulbank(url, id, select){
    id.html("");
    $.ajax({
        url:  '/bulkBank',
        type:'POST',
        data:{'url': url},
        success:function(result){
            var result = JSON.parse(result);
            id.append($('<option>', {
                value: "",
                html : ""
            }));
            $.each(result, function (index, value) {
                var name=value.name? value.name: value.program;
                if( value.id==select){
                    id.append($('<option>', {
                        value: value.id,
                        html : name,
                        selected: 'selected'
                    }));
                }else {
                    id.append($('<option>', {
                        value: value.id,
                        html: name
                    }));
                }
            });
        },
        error: function(result){
            showError("Hubo un error al comunicarse con la API");
        }
    });
}
/**
 * @desc Cargar el listado de servicios
 * @param url: Url del servicio, id: id del select, select: en caso de seleccionar alguno
 */
function load_subservices(url, id, select){
    id.html("");
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        success:function(result){
            var result = JSON.parse(result);
            id.append($('<option>', {
                value: "",
                html : ""
            }));
            $.each(result, function (index, value) {
                if( value.id==select){
                    id.append($('<option>', {
                        value: value.id,
                        html : value.name,
                        'data-amount': value.amount,
                        selected: 'selected'
                    }));
                }else {
                    id.append($('<option>', {
                        value: value.id,
                        'data-amount': value.amount,
                        html: value.name
                    }));
                }
            });
        },
        error: function(result){
            showError(result);
        }
    });
}

function load_user(id, select){
    $.ajax({
        url:  '/application/UsersGroupList/',
        type:'GET',
        success:function(result){
            var result = JSON.parse(result);
            id.append($('<option>', {
                value: "0",
                html : "Usuario"
            }));
            $.each(result, function (index, value) {
                if( value.id==select){
                    id.append($('<option>', {
                        value: value.id,
                        text : value.id,
                        selected: 'selected'
                    }));
                }else {
                    id.append($('<option>', {
                        value: value.id,
                        text: value.id
                    }));
                }
            });
        },
        error: function(result){
            showError(result);
        }
    });
}

function load_banks(url, id, select){
    id.html("");
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        success:function(result){
            var result = JSON.parse(result);
            id.append($('<option>', {
                value: "0",
                html : ""
            }));
            $.each(result, function (index, value) {
                if( value.id==select){
                    id.append($('<option>', {
                        value: value.id,
                        html : value.name,
                        selected: 'selected',
                        data_change: value.exchangeRate
                    }));
                }else {
                    id.append($('<option>', {
                        value: value.id,
                        html: value.name,
                        data_change: value.exchangeRate
                    }));
                }
            });
        },
        error: function(result){
            showError(result);
        }
    });
}

/**
 * @desc Cargar el listado de países dentro de un select box y mandar a cargar los estados del país seleccionado
 * @param country: en caso de estar seleccionada algun país, state: en caso de estar seleccionado algún estado
 */
function load_countries(country, state){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/countries'},
        success:function(result){
            var result = JSON.parse(result);
            $.each(result, function (index, value) {
                $("#countries").append("<option value='"+value.code+"'>"+Decode(value.name)+" </option>");
            });
            if (country) { $("#countries").val(country); }

            if(state){
                load_states(country, state);
            }else{
                country=$("#countries").val();
                load_states(country);
            }
        },
        error: function(err){
            showError(err);
        }
    });
}
/**
 * @desc Cargar el listado de estados dentro de un select box
 * @param country: de acuerdo a la que se cargaran los estados, state: en caso de estar seleccionado algún estado
 */
function load_states(country, state){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/states/country/'+country},
        success:function(result){
            var result = JSON.parse(result);
            result= result.states;
            $("#states").html("");
            $.each(result, function (index, value) {
                if(value.country.code==country)
                    $("#states").append("<option value='"+value.id+"'>"+ Decode(value.name)+" </option>");
            });
            if (state) { $("#states").val(state);console.log(state); }
            //console.log(state);
        },
        error: function(err){
            console.log(err);
        }
    });
}
/**
 * @desc Cargar el listado de hook en radio buttons
 * @param
 */
function load_hook(hooked){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/hooks'},
        error: function (err) {
            console.log(err);
        },
        success: function (data) {
            var data = JSON.parse(data);
            var select = $('#hook');
            var hookHTML = '<label class="radio">Hook Type*</label>';

            if (data.length > 0) {
                $.each(data, function(i, hook) {
                    var hookId = 'hook' + i;
                    if(hook.id==hooked){
                        hookHTML += '<label for="' + hookId + '" class="radio-inline">' +
                        '<input type="radio" checked name="hook" id="' + hookId + '" value="' + hook.id + '">' +
                        hook.name + '</label>';
                    }else{
                        hookHTML += '<label for="' + hookId + '" class="radio-inline">' +
                        '<input type="radio" name="hook" id="' + hookId + '" value="' + hook.id + '">' +
                        hook.name + '</label>';
                    }

                }); // end each
                select.html(hookHTML);
            } else {
                console.log("No hooks found");
            }
        }
    });
}

function loadImages(url, page, select ) {
    $("#cert-images, #site-images").html("");
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/ecert/images/?page=' + page},
        error: function (err) {
            showError(err);
        },
        success: function (data) {
            data= $.parseJSON(data);
            console.log("petición: " + url + '/ecert?page=' + page);
            currentPage = data.currentPage;
            totalElements = data.totalElements;
            totalPages= data.totalPages;
            data=data.elements;
            //console.log("data elements>>>");
            //console.log(data);
            if (data.length > 0) {
                $(".pagination").find(".disabled").removeClass("disabled");
                $.each(data, function (i, image) {
                    div = $("<div />",{'class':'col-sm-3 thumb'});
                    label = $("<label />",{'class':'thumbnail'});

                    img = div.append(
                        label.append(
                            $("<img/>", {'src': image.url, 'title': image.image, 'class':'img-responsive'}),
                            $("<input/>", {'type': 'radio', 'name': 'sites-image-input', 'class': 'site-image', 'value': image.image}).appendTo(div)
                        ));
                    $("#cert-images").append(img);
                }); // end each
                //$("#site-images img").each(function( ) {
                //    $(this).error(function(data){
                //        $(this).parent().parent().remove();
                //    });
                //});
                //$("#cert-images img").each(function( ) {
                //    $(this).error(function(data){
                //        $(this).parent().parent().remove();
                //    });
                //});

                $("#campaigns-datatable_info").html("Showing " + (((currentPage - 1) * 10) + 1) + " to " + currentPage * 10 + " of " + totalElements + " entries")
                var pagesArray ;
                if(totalPages > 7) {
                    if(currentPage < 5) {
                        pagesArray=[1, 2, 3, 4, 5, "..", totalPages];
                    } else if(currentPage > totalPages - 4) {
                        pagesArray=[1, "..",totalPages-4, totalPages-3, totalPages-2,totalPages-1 ,totalPages];
                    } else {
                        pagesArray=[1,"..", currentPage-1, currentPage,currentPage+1, "..", totalPages]
                    }
                    $(".pagination li").each(function (index, value) {
                        if(index>0 && index<$(".pagination li").size()){
                            $(this).find("a").html(pagesArray[index-1]);
                            if(pagesArray[index-1]=="..")
                                $(this).addClass("disabled");
                        }
                    });

                }else if(totalPages < 7 && $(".paginate_button").size()==9){
                    for ( var i = totalPages; i < 7; i++ ) {
                        $(".paginate_button").eq(totalPages+1).remove();
                    }

                }

            } else {
                console.log("No transportation found");
            }
            //$(".site-image").filter(function() {
            //    return this.value == select
            //}).prop("checked","true");

            $(".pagination").find(".active").removeClass("active");
            $(".pagination li a").filter(function() {
                //console.log($(this).text(), page);
                return $(this).text() == page;
            }).parent().addClass("active");
            if(currentPage==1)
                $(".previous").addClass("disabled");
            else if (currentPage==totalPages)
                $(".next").addClass("disabled");
            //$(".pagination li a:contains('"+page+"')").parent().addClass("active");
        }
    });
}

/**
 * @desc Cargar el listado de planes alimenticios en radio select box
 * @param
 */
function load_mealplan(selected, url){
    var url=url?url: '/mealplans';
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        error: function (err) {
            console.log(err);
        },
        success: function (data) {
            var data = JSON.parse(data);
            var select = $('#meal-plan');
            var mealPlanHTML = '';
            if (data.length > 0) {
                $.each(data, function(i, mealPlan) {
                    if(mealPlan.id==selected){
                        mealPlanHTML += '<option selected=selected value="' + mealPlan.id + '">' + Decode(mealPlan.plan) + '</option>'
                    }else{
                        mealPlanHTML += '<option value="' + mealPlan.id + '">' + Decode(mealPlan.plan) + '</option>'
                    }
                }); // end each
                select.html(mealPlanHTML);
            } else {
                console.log("No meal plans found");
            }
        }
    });
}
/**
 * @desc Cargar los servicios enlistados en los pagos
 * @param servicios seleccionados por un pago
 */
function load_services(idbooking, services, amounts){
    console.log("Load Services");
    $("#payment").find(".sk-chasing-dots").show();
    $("#payment").find("table tbody").hide();
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/sales/'+idbooking+'/providedServices'},
        success: function (result) {
            var result = $.parseJSON(result);
            console.log(result);
            if(result.length>0){
                var payments=$("#payment").find("tbody").html("");
                $.each(result, function(index, value){
                    var tr=$("<tr/>");
                    var id="", description="", amount="", remaining="", amountPaid="";
                    console.log(services);
                    if(value.id!=null)
                        id=value.id;

                    if(value.description){
                        description= value.description;
                    }
                    if(value.amount){
                        amount=value.amount;
                    }
                    if(value.remaining!=null){
                        remaining=value.remaining;
                    }
                    if($.inArray(parseInt(value.id), services) !=-1){
                        td=$("<td/>").append($("<input/>", {'type': 'checkbox', value: id, 'checked': 'checked', 'data-importe': amount}));
                        check=1;
                    }else{
                        check=0;
                        td=$("<td/>").append($("<input/>", {'type': 'checkbox', value: id, 'data-importe': remaining}));
                    }

                    tr=tr.append(td);

                    pos=$.inArray(parseInt(value.id), services);
                    inputgroup=$("<div/>", {'class': 'input-group'});
                    input_group=$("<div/>", {'class': 'input-group-addon', 'text': '$'});
                    if(pos !=-1){
                        amountPaid=amounts[pos];
                        amountPaid= $("<input/>", {'type': 'text', 'value': amountPaid, 'class': 'text-right form-control input-sm'}).prop('readonly', false);
                    }else{
                        if(check){
                            amountPaid= $("<input/>", {'type': 'text', 'class': 'text-right form-control input-sm'}).prop('readonly', false);
                        }else{
                            amountPaid= $("<input/>", {'type': 'text', 'class': 'text-right form-control input-sm'}).prop('readonly', true);
                        }
                    }
                    amountPaid= inputgroup.append(input_group, amountPaid);
                    tr=tr.append($("<td/>", {'text': description+' $ '+amount}));
                    tr=tr.append($("<td/>", {'text': '$ '+amount}));
                    tr=tr.append($("<td/>", {'text': description}));
                    tr=tr.append($("<td/>", {'text': '$ '+remaining}));
                    tr=tr.append($("<td/>", {'class': 'col-sm-2'}).append(amountPaid));

                    payments.append(tr);
                });
                $("#payment").find(".sk-chasing-dots").hide();
                payments.show();
            }else{
                 $("#payment").find(".sk-chasing-dots").hide();
                 $("#payment").find(".ibox-content").append($("<p/>", {'text': 'No existen Servicios'}))
            }
        },
        error: function (err) {
            showError("error" + err);
        }
    });
}
function load_title(title){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/titles'},
        success:function(result){
            var result = JSON.parse(result);
            $("#title").html("");
            var option;
            $.each(result, function (index, value) {
                option=$('<option/>', {
                    'value': value.id,
                    'text': value.name
                });
                $("#title").append(option);
                if(title)
                    $("#title").val(title);
            });
        },
        error: function(err){
            alert(err);
        }
    });
}
/**
 * @desc Cargar los estatus maritales
 * @param status: en caso de haber alguno seleccionado
 */
function load_marital(status){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/maritalstatus'},
        success:function(result){
            var result = JSON.parse(result);
            $("#marital").html("");
            var input;
            var label;
            $.each(result, function (index, value) {
                label=$('<label/>', { 'class': 'radio-inline col-xs-12',  'text': value.civilEng , 'style': 'margin-left: 10px;'})
                input=$('<input/>', {
                    'type': 'radio',
                    'name': "status",
                    'value': value.id
                }).prependTo(label);
                $("#marital").append(label);
            });
            if(status)
                $("#marital input[value="+status+"]").attr('checked', true);
            else
                $("#marital input").eq(0).attr('checked', true);
        },
        error: function(err){
            alert(err);
        }
    });
}

/**
 * @desc Cargar los features
 * @param features: seleccionados en un cliente
 */
function load_feature(features){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': '/features'},
        success:function(result){
            var result = JSON.parse(result);
            $("#features .checkbox").html("");
            var input;
            var label;
            $.each(result, function (index, value) {
                label=$('<label/>', { 'text': value.name, 'class': 'col-xs-12' });
                if($.inArray(parseInt(value.id), features) !=-1){
                    input=$('<input/>', {
                        'type': 'checkbox',
                        'name': "interested[]",
                        'value': value.id,
                        'checked':'checked'
                    }).prependTo(label);
                }else{
                    input=$('<input/>', {
                        'type': 'checkbox',
                        'name': "interested[]",
                        'value': value.id
                    }).prependTo(label);
                }
                $("#features .checkbox").append(label);
            });
        },
        error: function(err){
            showError(err);
        }
    });
}

function load_campaign(master, id_cam){
    var campaigns=[ { value: 'Andorra', data: 'AD' }];
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data: {'url': '/certCustomer/'+ master},
        success:function(result){
            console.log("result >>>" + result);
            var result =  $.parseJSON(result);
            var option="";
            var countries = [
                { value: 'Andorra', data: 'AD' },
                { value: 'Zimbabwe', data: 'ZZ' }
            ];
            option=$("<option/>", {'value': '', 'text': 'Select a campaign'});
            $("#campaigns").append(option);
            $.each(result.campaigns, function(index, value){
                data={ value: value.id, data:  value.name};
                campaigns.push(data);

                if(value.id==id_cam)
                    option=$("<option/>", {'value': value.id, 'text': Decode(value.name), 'selected':'selected'});
                else
                    option=$("<option/>", {'value': value.id, 'text': Decode(value.name)});
                $("#campaigns").append(option);
            });

        },
        error: function(err){
            showError("error" + err);
        }
    });
    $("#redirect input[name=mode]").val("edit");
    return campaigns;
}

function load_master_brokers(masterBroker){
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data: {'url': '/certCustomer'},
        success:function(result){
            var result = JSON.parse(result);
            result = result.elements;
            console.log("result");
            console.log(result);
            $("#loading").fadeOut(function() {
                $.each(result, function (index, value) {
                    $("#master-broker").append("<option value='"+value.id+"'>"+value.companyName+" </option>");
                });
                if(masterBroker) {
                    var mbOption = $("#master-broker option[value='" + Decode(masterBroker) + "']");
                    if(mbOption.length > 0) {
                        mbOption.prop("selected", true);
                    } else {
                        console.log("El master broker con id " + masterBroker + " no existe.");
                    }
                }
                $("#master-broker").fadeIn();
            });
        },
        error: function(err){
            showError(err);
            console.log("err");
            console.log(err);

        }
    });
}

function load_multiple_brokers(masterBrokerId, campaignId){
    console.log("masterBrokerId>>>" + masterBrokerId);
    $("#id-master").val(masterBrokerId);
    if(masterBrokerId == null) {
        console.log("mb null");
    }
    if(masterBrokerId != null) {
        console.log("diferente de null");
    }
    $.ajax({
        url:  '/application/brokers-list/' +masterBrokerId,
        type:'GET',
        success:function(result){
            var brokers = [];
            var result = JSON.parse(result);

            result = result.elements;
            console.log("result lmb");
            console.log(result);
            $("#broker").html("");
            // Aquí se seleccionan los brokers que tienen esta campaña
            console.log(result);
            campaignId=$("#id-value").val();

            $.each(result, function (index, value) {
                console.log(campaignId);
                if(campaignId && value.campanaId == campaignId) {
                    brokers.push(value.user);
                }
                $("#broker").append("<option value='" + value.user + "'>" + Decode(value.user) + " </option>");

            });
            console.log("brokers>>>" +
            "" + brokers);
            $('#broker').multipleSelect({
                width: '100%'
            });
            if(brokers.length > 0) {
                $("#broker").multipleSelect("setSelects", brokers);
            }
            //}else{
            //    $(".ms-choice").prop("disabled", "disabled");
            //}
        },
        error: function(err){
            alert(err);
        }
    });
}

function stringFormat(array) {
    var result      = "";
    for (var index = 0; index < array.length; ++index) {
        separation = (index + 1 < array.length) ? "," : "";
        result += "\"" + array[index] + "\"" + separation;
        console.log(array[index]);
    }
    return result;
}

function truncateString(str, length) {
    return str.substring(0, length);
}

/**
 * @desc Comprueba que el tamaño de la cadena sea el mínimo requerido
 * @param recibe el objeto input o textarea
 * @return bool - Verdadero si la cadena es mayor al tamaño requerido o falso en caso contrario
 */
function lengthOk(element) {
    var lengthAttribute = element.attr("data-length");
    if (typeof lengthAttribute !== typeof undefined && lengthAttribute !== false) {
        return element.val().length >= lengthAttribute;
    }
    return true;
}

/**
 * @desc Verfica el tamaño del password y que sea igual a verificar password;
 * @param minLength- Tamaño mínimo requerido
 * @return bool - Verdadero, en caso de que el password cumpla con los requerimientos o Falso, en caso contrario.
 */
function passwordLength(minLength) {
    var userPassword             = $.trim($("#password").val());
    var userPasswordLength       = userPassword.length;
    var repeatUserPassword       = $.trim($("#repeat-password").val());
    var repeatUserPasswordLength = repeatUserPassword.length;
    return ( userPasswordLength >= minLength && repeatUserPasswordLength >= minLength ) && ( userPassword == repeatUserPassword );
}

/**
 * @desc Marcar error en password en caso de que sea nuevo usuario
 * @param mode- Editación o Creación
 * @return bool - Verdadero, en caso de que el password cumpla con los requerimientos o Falso, en caso contrario.
 */
function validatePassword(mode){
    var userPassword  = $.trim($("#password").val());
    if(mode == "create" || userPassword.length>=1){
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

/**
 * @desc Muestra mensaje de error en los formulario de creación y edición
 * @param errorMessage- Texto que mostrara el error
 * @return Nada
 */
function showError(errorMessage){
    $(".alert-danger, .alert-success").remove();
    var span=$("<span/>", {'aria-hidden':'true', 'text':'x'});
    var button=$("<button/>", {'type':'button', 'class':'close', 'data-dismiss':'alert', 'aria-label':'Close'});
    var div= $("<div/>", {'class':'alert alert-danger alert-dismissible', 'role':'alert', 'text': errorMessage});
    $("h1").after(div.prepend(button.append(span)));
    $('html, body').animate({
            scrollTop: 0
        }, 300, function(){
    });
}
/**
 * @desc Muestra mensaje de error para los modal
 * @param errorMessage- Texto que mostrara el error
 * @return Nada
 */
function showModalError(modal, errorMessage){
    $(".alert-danger, .alert-success").remove();
    var span=$("<span/>", {'aria-hidden':'true', 'text':'x'});
    var button=$("<button/>", {'type':'button', 'class':'close', 'data-dismiss':'alert', 'aria-label':'Close'});
    var div= $("<div/>", {'class':'alert alert-danger alert-dismissible', 'role':'alert', 'text': errorMessage});
    modal.find(".modal-body").prepend(div.prepend(button.append(span)));
}
/**
 * @desc Muestra mensaje de éxito en los formulario de creación y edición
 * @param errorMessage- Texto que mostrara el error
 * @return Nada
 */
function showSuccess(errorMessage){
    $(".alert-danger, .alert-success").remove();
    var span=$("<span/>", {'aria-hidden':'true', 'text':'x'});
    var button=$("<button/>", {'type':'button', 'class':'close', 'data-dismiss':'alert', 'aria-label':'Close'});
    var div= $("<div/>", {'class':'alert alert-success alert-dismissible', 'role':'alert', 'text': errorMessage});
    $("h1").after(div.prepend(button.append(span)));
    $('html, body').animate({
            scrollTop: 0
        }, 300, function(){
    });
}
/**
 * @desc Muestra mensaje de éxito para los modal
 * @param errorMessage- Texto que mostrara el error
 * @return Nada
 */
function showModalSuccess(modal, errorMessage){
    $(".alert-danger, .alert-success").remove();
    var span=$("<span/>", {'aria-hidden':'true', 'text':'x'});
    var button=$("<button/>", {'type':'button', 'class':'close', 'data-dismiss':'alert', 'aria-label':'Close'});
    var div= $("<div/>", {'class':'alert alert-success alert-dismissible', 'role':'alert', 'text': errorMessage});
    modal.find(".modal-body").prepend(div.prepend(button.append(span)));
}

/**
 * @desc Limpia cadena de enters y los manda a la función htmlEntities,
 * que lo limpia de caracteres especiales
 * @param encodedText- Cadena
 * @return Cadena sin enters
 */
function Encode(encodedText){
    return  htmlEntities(encodedText.replace(/\n/g, "|"));
}

/**
 * @desc Regresa enters a la cadena y los manda a la función htmlEntities,
 * que le regresa los caracteres especiales
 * @param encodedText- Cadena
 * @return Cadena con enters
 */
function Decode(encodedText){
    if(encodedText==null)
        return null;
    else
        return returnhtmlEntities(encodedText.replace(/\|/g, "\n"));
}

/**
 * @desc Regresa caracteres especiales a la cadena
 * @param encodedText- Cadena
 * @return Cadena con caracteres especiales
 */
function Decodehtml(encodedText){
    if(encodedText==null)
        return null;
    else
        return returnhtmlEntities(encodedText.replace(/\|/g, "<br />"));
}

$(document).on("keyup", ".data_error input, .data_error textarea", function (event) {
    if(lengthOk($(this))){
        $(this).parent().removeClass("data_error").find("span").remove();
        $(this).parent().find("em").show();
    }
});
function count_text(obj){
    tam= $(obj).val().length;
    var newLines = $(obj).val().match(/(\r\n|\n|\r)/g);
    var addition = 0;
    if (newLines != null)
        addition = newLines.length;
    tam+=addition;
    return tam;
}
function limit(obj){
    tam= count_text(obj);
    lim=$(obj).attr("maxlength");
    if(tam >= lim){
        $(obj).closest(".form-group").find("#cont").html(0);
        //showError("Error");
        return false;
    }else{
        $(obj).closest(".form-group").find("#cont").html(lim-tam);
        return true;
    }
}

function focusCursor(control){
    $('body,html').animate({
        scrollTop: $(control).offset().top - 30
    }, 800);

    $(control).focus();
}
function getInt(value) {
    if(value == "") {
        return 0;
    } else {
        return parseInt(value.replace(/[A-Za-z$-]/g, ""));
    }
}

/**
 * @desc Limpia cadena de caracteres especiales
 * @param encodedText- Cadena
 * @return Cadena sin caracteres especiales
 */
function htmlEntities(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

/**
 * @desc Regresa caracteres especiales a la cadena
 * @param encodedText- Cadena
 * @return Cadena con caracteres especiales
 */
function returnhtmlEntities(str) {
    return String(str).replace(/&quot;/g, '"').replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, '>');
}
function validphone(){
    flag=0;
    $("#phones input[type='text']").each(function () {
        if($(this).val() != "" ){
            flag=1;
            return;
        }
    });
    if(flag==1)
        return true
    else
        return false;
}

function show_images(images){
    var clearflix;
    var img;
    $.each(images, function( index, value ){
        clearflix= $('<div/>', {'class': 'clearfix'});
        img=$('<img/>', {
            'src':value.url,
            'class': 'img-responsive'
        });
        img= $('<div/>', {'class':'img-cut'}).append(img);
        var caption= $('<div/>', {'class':'caption'})
            .append($('<a/>', {'class':'delete-img pull-right flaticon-rubbish7',
                'data-token': value.token,
                'html': 'Delete'
            }).easyconfirm({text: 'Are you sure you want to delete this image?'}));
        $('<div/>', {'class':'col-sm-6 col-md-3'})
            .append(
            $('<div/>', {
                'class':'thumbnail'
            }).append(img,caption,clearflix)
        ).appendTo('#images');
        $("#images img").each(function( ) {
            $(this).error(function(data){
                $(this).parent("div").parent("div").remove();
            });
        });
    });
}

// Valida que los campos se hayan capturado correctamente
function formValidate(id){
    var result = true;
    result = validateInputText(id);
    return result;
}

function isUrl(s) {
    var regexp =  /(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/;
    return regexp.test(s);
}

function get_certificate_type(CampaignId){
    //console.log("Campaña Id: "+CampaignId);
    var certificateType=""
    $.ajax({
        success:function(result){
            var result =  $.parseJSON(result);
            certificateType=result.certificate;
            //console.log(certificateType);
        },
        url:  '/application/certconfiglist/'+CampaignId,
        type: 'GET',
        async: false,
        error: function(err){
            console.log(err);
        }
    });
    return certificateType;
}
function formTerms(){
    var result = true;
    var cont=0;
    $(".description-translation").each(function(index){
        if($.trim($(this).val()) == "" || $(this).val().length < 2 ){
            cont+=1;
            $(this).parent().removeClass("data_error").find("span").remove();
            $(this).parent()
                .addClass("data_error")
                .append("<span>Description is required</span>");
        }
    });
    if(cont>2){
        result=false;
        $("#tab2default .description-translation").closest(".panel-heading").find(".data_error").remove();
        $("#tab2default .description-translation").closest(".panel-heading").after("<span> Description is required </span>").addClass("data_error");
    }
    return result;
}
function replace_phone(phone){
    var ast=phone.length-3;
    phone=phone.substring(ast);
    phone="*".repeat(ast)+phone;
    return phone;
}
function daysByweek(w){
    var n1 = new Date(w);
    var n2 = new Date(w + 604800000)
    var months=["Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sep", "Oct", "Nov", "Dec"]
    n1=n1.getDate()+"/"+months[n1.getUTCMonth()]+"/"+n1.getFullYear();
    n2=n2.getDate()+"/"+months[n2.getUTCMonth()]+"/"+n2.getFullYear();
    return (n1+" - "+n2)
}

function getFirstDay(year, day, week){
    plusdays = [0,6,5,4,3,2,1];
    firstDay = new Date(year, 0, 1).getDay();
    var d = new Date("Jan 01, "+year+" 01:00:00");
    var w = d.getTime() -(3600000*24*(firstDay+plusdays[day-1])) + 604800000 * (week);
    return w;
}

$.fn.load_weeks = function (year, day) {
    var tr=$("<tr/>");
    $(this).find("tbody").html("");
    var today= new Date();
    for(i=1; i<=53; i++){
        var module= i % 9;
        if(module == 0 || i == 9)
            $(this).find("tbody").append(tr);
        var day1=getFirstDay(year, day, i);
        class_name="";
        if(today > new Date(day1)){
            input=$("<input/>", {'type': 'number', 'class': 'form-control', value: 0, min: 0, name: 'quantity', disabled: "disabled"});
        }else{
            input=$("<input/>", {'type': 'number', 'class': 'form-control', value: 0, min: 0, name: 'quantity'})
            class_name="active-week";
        }
        var n2 = new Date(day1 + 604800000);
        if(new Date(day1).getFullYear()==year || n2.getFullYear()==year){
            var td=$("<td/>").append(
                i > 9 ? i :"0"+i,
                input,
                $("<input/>", {'type': 'hidden', 'class': 'form-control', value: 0, name: 'ids'}),
                $("<input/>", {'type': 'hidden', 'class': 'form-control', value: i, name: 'weekDayId'}),
                $("<i/>", {'class': 'glyphicon glyphicon-info-sign '+class_name, 'data-toggle':"tooltip",
                    'data-placement':"top", 'title': daysByweek(day1) })
            );
            tr=tr.append(td);
        }
        if(module == 0)
            var tr=$("<tr/>");
    }
    $(this).find("tbody").append(tr);
    $('[data-toggle="tooltip"]').tooltip({html:true});
}

$.fn.load_years = function (year) {
    var today=new Date();
    year= year ? year : today.getFullYear();
    for (i=year-3; i<= year+15; i++){
        $(this).append($("<option/>", {'value': i, html: i}));
    }
    $(this).val(year);
}

$.fn.load_days = function (day) {
    var days=["","SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
    for(i=1; i<=7; i++){
        $(this).append($("<option/>", {'value': i, html: days[i]}));
    }
    $(this).val(day);
}

$.fn.load_templates = function (url, template) {
    var template_select=$(this);
    $(this).html("");
    $.ajax({
        url:  '/bulkBank',
        type:'POST',
        data:{'url': url},
        success:function(result){
            var result = JSON.parse(result);
            template_select.append($('<option/>', {
                value:"",
                html : ""
            }));
            $.each(result, function (index, value) {
                if( value.id==template){
                    template_select.append($('<option/>', {
                        value: value.id,
                        html : value.name,
                        selected: 'selected'
                    }));
                }else {
                    template_select.append($('<option/>', {
                        value: value.id,
                        html: value.name
                    }));
                }
            });

        },
        error: function(result){
            showError("Hubo un error al comunicarse con la API");
        }
    });
}

$.fn.sumaEle = function () {
    sum=parseFloat(0);
    $.each(this, function(index, value){
        if($(this).find("td:eq(0)").find("input").is(":checked")){
            val=$(this).find("td:eq(5)").find("input").val();
            if(val !="")
                sum+=parseFloat(val);
        }
    });
    return sum;
}

$.fn.load_template = function (id, year, day) {
    var div=$(this);
    div.html("");
    $.ajax({
        url: '/bulkBank',
        type: 'POST',
        data: {'url': '/templateBulkBank/'+id},
        success: function (result) {
            var result =  $.parseJSON(result);
            div.display_template(result, year, day);
            div.find(".sk-chasing-dots").hide();
            load_sunset_hotels("/hotel", $("#hotel"), result.clubId);
            $("#template").load_templates("/templateBulkBank/list?clubId="+result.clubId, id);
        }
    });
}
$.fn.load_bulkbank = function (id, year, day) {
    var div=$(this);
    div.find(".sk-chasing-dots").show();
    $.ajax({
        url: '/bulkBank',
        type: 'POST',
        data: {'url': '/bulkBank/'+id},
        success: function (result) {
            div.html("");
            var result =  $.parseJSON(result);
            div.display_template(result.templateBulkBank, year, day);
            div.find(".sk-chasing-dots").hide();
            load_sunset_hotels("/hotel", $("#hotel"), result.templateBulkBank.clubId);
            $("#quantity").val(result.quantity);
            $("#bulkBankId").val(result.id);
            console.log(result.checkIn);
            var checkin= new Date(result.checkIn);
            checkin = checkin.getFullYear()+ '-'
                + ('0' + (checkin.getMonth()+1)).slice(-2) + '-'
                + ('0' + checkin.getDate()).slice(-2);
            $("#checkin").val(checkin);
            var checkout= new Date(result.checkOut);
            checkout = checkout.getFullYear()+ '-'
                + ('0' + (checkout.getMonth()+1)).slice(-2) + '-'
                + ('0' + checkout.getDate()).slice(-2);
            $("#checkout").val(checkout);
            $("#template").load_templates("/templateBulkBank/list?clubId="+result.templateBulkBank.clubId, id);
        }
    });
}
$.fn.display_template = function (result, year, day) {
    var days=["","SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
    var year_day="";
    if(year && day){
        year_day=$("<div/>", {class: 'col-sm-6'}).append($("<div/>",{class: 'row'})
            .append($("<p/>", {class: 'col-sm-6', text: year})
                .prepend($("<strong/>", {html: "Year: "}) ),
                $("<p/>", {class: 'col-sm-6', text: days[day]})
                    .prepend($("<strong/>", {html: "Day: "}))
            )
        );
    }
    $(this).append($("<p/>", {class: 'col-sm-6', text: result.name})
        .prepend($("<strong/>", {html: "Template: "}) ), year_day);
    var split_name=result.split? result.split.name: "";

    var unit=result.unit ? result.unit.name : "";
    var unit_split=$("<div/>", {class: 'col-sm-6'}).append($("<div/>",{class: 'row'})
        .append($("<p/>", {class: 'col-sm-6', text: unit})
            .prepend($("<strong/>", {html: "Unit: "}) ),
            $("<p/>", {class: 'col-sm-6', text: split_name})
                .prepend($("<strong/>", {html: "Split: "}))
        )
    );

    $(this).append($("<p/>", {class: 'col-sm-6 hotel-name', text: ''})
        .prepend($("<strong/>", {html: "Hotel: "}) ), unit_split);
    var plan= result.plan ? result.plan.plan : "";
    $(this).append(
        $("<p/>", {class: 'col-sm-3', text: plan}).prepend($("<strong/>", {html: "Plan: "}) ),
        $("<p/>", {class: 'col-sm-3', text: result.adults}).prepend($("<strong/>", {html: "Adults: "}) ),
        $("<p/>", {class: 'col-sm-3', text: result.children}).prepend($("<strong/>", {html: "Children: "}) ),
        $("<p/>", {class: 'col-sm-3', text: result.rate}).prepend($("<strong/>", {html: "Rate: "}) ),
        $("<p/>", {class: 'col-sm-12', text: result.observations}).prepend($("<strong/>", {html: "Observations: "}) )
    );
}

$.fn.alerts = function (user) {
    //Obtener alertas
    var url="/alerts/"+user;
    var alerts=$(this);
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        success:function(result){
            result = JSON.parse(result);
            var number = result ? result.length : 0;
            $("#number-alerts").html(number);
            var divider= $("<li/>",{'class': 'divider'});
            if(number>0){
                $.each(result, function (index, value) {
                    var alertDate=$.format.date(value.date, "dd/MM/yyyy");
                    var span=$("<span/>",
                        {'class': 'pull-right text-muted'})
                        .append($("<em/>",
                            {'text': alertDate}));
                    var strong = $("<strong/>", {'text': value.sourceUser});
                    var div=$("<div/>").append(strong, span);
                    var maxLength = 100;
                    var comment_text = value.comments.substring(1, maxLength);
                    comment_text=comment_text.substr(0,Math.min(comment_text.length, comment_text.lastIndexOf(" ") ));
                    var comment = $("<div/>", {'text': comment_text+"..."});
                    var a=$("<a/>", {'href': value.url}).append(div, comment);
                    var li=$("<li/>").append(a);
                    if(index!=0)
                        $(alerts).prepend(li, $("<li/>", {'class': 'divider'}));
                    else
                        $(alerts).prepend(li);
                    return index<3;
                });
            }else{
                $(alerts).closest(".dropdown").remove();
            }
        },
        error: function(result){
            showError(result);
        }
    });
}
$.fn.notifications = function (user) {
    //Obtener Follow Ups
    var url="/customernotes/"+user;
    var alerts=$(this);
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        success:function(result){
            result = JSON.parse(result);
            var number = result ? result.length : 0;
            $("#number-noti").html(number);
            var divider= $("<li/>",{'class': 'divider'});
            if(number>0){
                $.each(result, function (index, value) {
                    var alertDate=$.format.date(value.fecha, "dd/MM/yyyy");
                    var span=$("<span/>",
                        {'class': 'pull-right text-muted'})
                        .append($("<em/>",
                            {'text': alertDate}));
                    var strong = $("<strong/>", {'text': value.iduser});
                    var div=$("<div/>").append(strong, span);
                    var maxLength = 300;
                    var comment_text = value.nota.substring(0, maxLength);
                    comment_text=comment_text.substr(0, Math.min(comment_text.length, comment_text.lastIndexOf(" ") ));
                    var comment = $("<div/>", {'text': comment_text+"..."});
                    var a=$("<a/>", {'href': value.url}).append(div, $("<br/>"), comment );
                    var li=$("<li/>").append(a);
                    if(index!=0)
                        $(alerts).prepend(li, $("<li/>", {'class': 'divider'}));
                    else
                        $(alerts).prepend(li);
                    return index<3;
                });
            }else{
                $(alerts).closest(".dropdown").remove();
            }
        },
        error: function(result){
            showError(result);
        }
    });
}
$.fn.foliosByCampaign = function () {
    //Obtener Follow Ups
    var url="/certificates/getFoliosbyActiveCampaigns";
    var alerts=$(this);
    $.ajax({
        url:  '/TableList',
        type:'POST',
        data:{'url': url},
        success:function(result){
            result = JSON.parse(result);
            var number = result ? result.length : 0;
            $("#number-campaigns").html(number);
            var divider= $("<li/>",{'class': 'divider'});
            if(number>0){
                $.each(result, function (index, value) {
                    var span = $("<span/>", {'class': 'pull-right text-muted', 'text':value.counter+ ' Folios'});
                    var strong = $("<strong/>", {'text': value.campania_NAME});
                    var div = $("<div/>").append($("<p/>").append(strong, span));
                    var a = $("<a/>", {'href': '/serials/'+value.campania_ID}).append(div);
                    var li = $("<li/>").append(a);
                    if(index!=0)
                        $(alerts).prepend(li, $("<li/>", {'class': 'divider'}));
                    else
                        $(alerts).prepend(li);
                });
            }else{
                $(alerts).closest(".dropdown").remove();
            }
        },
        error: function(result){
            showError(result);
        }
    });
}


$.fn.clearForm = function() {
    return this.each(function() {
        var type = this.type, tag = this.tagName.toLowerCase();
        if (tag == 'form')
            return $(':input',this).clearForm();
        if (type == 'text' || type == 'password' || tag == 'textarea')
            this.value = '';
        else if (type == 'checkbox' || type == 'radio')
            this.checked = false;
        else if (tag == 'select')
            this.selectedIndex = -1;
    });
};

$(document).ready(function() {

    //Download Ecert
    $( document ).on( "click", ".ecert_link", function(e){
        var cert=$(this).attr("data-type");
        var booking=$(this).attr("data-booking");
        var url=$(this).attr("data-url");
        if(cert !='null' && cert != '')	{
            //window.open("http://bpo.m4sunset.com:8080/M4CApp/reportes/requestReportes.jsp?REPORTE="+cert+"&BookingNumber="+booking);
            console.log(url+"/M4CApp/reportes/requestReportes.jsp?REPORTE="+cert+"&BookingNumber="+booking);
            window.open(url+"/M4CApp/reportes/requestReportes.jsp?REPORTE="+cert+"&BookingNumber="+booking);
        }
    });

    $(".change_language").click(function (e) {
            e.preventDefault();
            var lan=$(this).attr("data-lan");
            $.ajax({
                url:  '/changeLanguage/' +lan,
                type:'GET',
                success:function(result){
                    location.reload();
                },
                error: function(err){
                    alert(err);
                }
            });
        });

        $("textarea, input[type='text'] ").each(function () {
            val= limit($(this));
        });
        $("textarea, input[type='text']").keyup(function(e) {
            val=true;
            $(this).closest(".form-group").removeClass("data_error").find("span").remove();
            if(!$(this).hasClass("terms-translation")){
                val= limit($(this));
            }
            if(!val){
                tam=$(this).attr("maxlength");
                $(this).closest(".form-group")
                    .addClass("data_error")
                    .append("<span>Limit is "+tam+" characters</span>");
            }
            return val;
        });

        $("input[type='number']").keydown(function (e) {
            // Allow: backspace, delete, tab, escape, enter and .
            //if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13]) !== -1 ||
                    // Allow: Ctrl+A
                (e.keyCode == 65 && e.ctrlKey === true) ||
                    // Allow: Ctrl+C
                (e.keyCode == 67 && e.ctrlKey === true) ||
                    // Allow: Ctrl+X
                (e.keyCode == 88 && e.ctrlKey === true) ||
                    // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                // let it happen, don't do anything
                return;
            }
            // Ensure that it is a number and stop the keypress
            tam=$(this).attr("maxlength");
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105) || $(this).val().length>tam) {
                e.preventDefault();
            }


        });

        //Get Alerts


    });


