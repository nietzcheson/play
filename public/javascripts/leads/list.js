/**
 * Created by desarrollo1 on 13/04/2015.
 */
$(document).ready(function() {
    var days = ['Sun', 'Mon', 'Tues', 'Wed', 'Thurs', 'Fri', 'Sat'];
    var months = ['January', 'February', 'March', 'May', 'June', 'July', 'August','September', 'October', 'November','December'];
    var context;
    var dtable=$('#leads-datatable').dataTable({
    "processing": true,
    "serverSide": true,
    "ajax":'/application/leadslist',
    "columnDefs": [
        {
            "targets":0,
            "render": function (td, cellData, rowData, row, col) {
                        return  '<a href="/clients/' + rowData.idcliente + '">' + rowData.name + '</a>';
                    }
        },
        {
            "targets":4,
            "render": function (datecreated) {
                        d= new Date(datecreated);
                        da=days[d.getUTCDay()]+" "+months[d.getUTCMonth()-1]+" "+d.getUTCDate()+" "+d.getUTCFullYear();
                        return da;
                    }
        },
        {
            "targets":5,
            "render": function (dateupdated) {
                d= new Date(dateupdated);
                da=days[d.getUTCDay()]+" "+months[d.getUTCMonth()-1]+" "+d.getUTCDate()+" "+d.getUTCFullYear();
                return da;
            }
        },
        {
            "targets":6,
            "render": function (td, cellData, rowData, row, col) {
                console.log(rowData);
                return "Info";
            }
        }

        ],
        "columns": [
            { "data": "name","title":"Client"},
            { "data": "idbooking","title":"Id Booking" },
            { "data": "numcert","title":"Certificate"},
            { "data": "campaign","title":"Campaign"},
            { "data": "datecreated","title":"Date Created"},
            { "data": "dateupdated","title":"Update Created" },
            { "data": "info","title":"Info" }
        ]
        ,"order": [[ 5, "desc" ]]
    });

    $('#myInput').on( 'keyup', function (e) {
        //if(this.value.length >= 3 && e.keyCode == 13) {
        if(e.keyCode == 13) {
            dtable.fnFilter(this.value);
        }else if(this.value == "") {
            dtable.fnFilter("");
        }
        return;
    } );

    $("#search-client").click(function (e) {
        e.preventDefault();
        var value_search=$("#myInput").val();
        dtable.fnFilter(value_search);
    });
});
