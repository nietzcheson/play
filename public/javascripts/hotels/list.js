/**
 * Created by Orlando on 16/02/2015.
 */
$(document).ready(function() {
    var dataSet = new Array();
    $.ajax({
        url:  '/TableList',
        data:{'url': '/hotels'},
        type:'POST',
        success:function(result){
            console.log(result);
            var result = JSON.parse(result);
            for(var i=0; i < result.length; i++) {
                var content = new Array(
                    '<a href="/hotels/' + result[i].id + '">' + result[i].name + '</a>',
                    result[i].city,
                    result[i].zipCode
                );
                dataSet.push(content);
            } //Termina el For

            $('#hotels-datatable').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );

            $('#hotels-datatable').dataTable( {
                "data": dataSet,
                "columns": [
                    { "title": "Name" },
                    { "title": "City" , "bSortable": false},
                    { "title": "Zip Code", "bSortable": false }
                ]
            } );
        },
        error: function(err){
            showError(err);
        }
    });
});
/**
 * Created by Orlando on 16/02/2015.
 */
