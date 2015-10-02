/**
 * Created by Orlando on 16/02/2015.
 */
$(document).ready(function() {
    var context;
    $('#campaigns-datatable').dataTable({
        "processing": true,
        "serverSide": true,
        "ajax":'/application/campaignslist',
        "columnDefs": [
            {
                "targets":0,
                "render": function (td, cellData, rowData, row, col) {
                    return  '<a href="/campaigns/' + rowData.id + '">' + rowData.name + '</a>'; }
            },
            { "targets": 1,
                "render":function(dateCreated){
                    return new Date(dateCreated).toDateString();
                    //return dateCreated;
                }
            },
            {
                "targets":2,
                "render": function (td, cellData, rowData, row, col) {
                    var brokerCRMName = (rowData.certCustomer != null) ? rowData.certCustomer : '';
                    return brokerCRMName; }
            },
            {
                "targets":3,
                "render": function (td, cellData, rowData, row, col) {
                    var offerName = (rowData.offer != null) ? rowData.offer : '-';
                    return offerName; }
            },
            {
                "targets":4,
                "render": function (td, cellData, rowData, row, col) {
                    return  '<a href="/serials/' + rowData.id + '">Serial Numbers</a>'; }
            }

        ],
        "columns": [
            { "data": "idcampania","title":"Campaign"  },
            { "data": "dateUpdated","title":"Updated" },
            { "data": "brokerCRM","title":"Master Broker", bSortable: false },
            { "data": "offer.name","title":"Offer", bSortable: false },
            { "data": "activationFee","title":"Certificate Serial", bSortable: false }
        ],
        "order": [[ 1, "desc" ]]

    });
});