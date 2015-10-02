/**
 * Created by Orlando on 25/02/2015.
 */
$(document).ready(function() {
    var dataSet = new Array();
    $.ajax({
        url:  '/TableList',
        data:{'url': '/destinations'},
        type:'POST',
        success:function(result){
            var result = JSON.parse(result);
            for(i=0; i < result.length; i++) {
                var myDate = new Date( result[i].dateCreated);
                var resultActive = (result[i].active == true) ? "Active" : "Inactive";
                var content = new Array(
                    "<a href='/destinations/" + result[i].id + "'>" + result[i].name + "</a>",
                    resultActive
                );
                dataSet.push(content);
            } //Termina el For

            $('#leads-datatable').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );

            $('#leads-datatable').dataTable( {
                "data": dataSet,
                "columns": [
                    { "title": "Name" },
                    { "title": "Active", bSortable: false }
                ],
                "order": [[ 0, "asc" ]]
            });
        },
        error: function(result){
            alert(result);
        },
        statusCode: {
            500: function() {
                alert("status 500")
            }
        }
    });
});