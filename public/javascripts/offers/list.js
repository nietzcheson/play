/**
 * Created by Orlando on 16/02/2015.
 */
$(document).ready(function() {

    $('#demo').dataTable({
        "processing": true,
        "serverSide": true,
        "ajax":'/application/offerslist',
        "columnDefs": [

            {
                "targets":0,
                "render": function (td, cellData, rowData, row, col) {
                    return  '<a href="/offers/' + rowData.id + '">' + rowData.name + '</a>';
                }
            },
            { "targets": 1,
               "render":function(dateCreated){
                   return new Date(dateCreated).toDateString();
               }
            }
        ],
        "columns": [
            { "data": "name","title":"Offer"  },
            { "data": "dateUpdated","title":"Last Update" },
            { "data": "nights","title":"Nights", bSortable: false },
            { "data": "price","title":"Price", bSortable: false },
            { "data": "activationFee","title":"Activation Fee", bSortable: false },
            { "data": "status","title":"Active", bSortable: false }
        ],
        "order": [[ 1, "desc" ]]

    });
});