<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel = "stylesheet" href="style2.css"/>
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script src="//cdn.datatables.net/plug-ins/1.13.7/i18n/pl.json"></script>
<title>Dane</title>
</head>
<body>

<div class="mainContent">
<h1>Lista wszystkich użytkowników</h1>
<br>
<table id="datatable">
    <thead>
        <tr>
            <th>Imię</th>
            <th style="word-wrap: break-word; word-break: break-all; white-space: normal;">Nazwisko</th>
            <th>Login</th>
        </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<script type="text/javascript">

$(document).ready(function() {
    $('#datatable').dataTable({
        ajax: {
            url: 'dane',
            dataSrc:''           
        },
        order: [
        	[ 0, "asc" ],
        	[ 1, "asc" ],
        	[ 2, "asc" ]
        ],
        columns: [
            { data: 'name', "sType": "polstring" },
            { data: 'surname', "sType": "polstring", render: function (data, type, row) {
                // Renderowanie danych w sposób dostosowany dla DataTables
                return type === 'display' ?
                    '<div style="white-space: normal;">' + data + '</div>' :
                    data;
            } },
            { data: 'login', "sType": "polstring" }
        ],
        columnDefs: [{
            targets: '_all',
            defaultContent: ""
        }],
        language: {
            url: '//cdn.datatables.net/plug-ins/1.13.7/i18n/pl.json',
        } 
    });
});


(function(){
	var alphabet = [
	    ' ', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+',
	    '0' ,'1', '2', '3', '4', '5', '6', '7', '8', '9',
	    'a', 'A', 'ą', 'Ą', 'b', 'B', 'c', 'C', 'ć', 'Ć', 'd', 'D', 'e', 'E', 'ę', 'Ę',
	    'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'ł', 'Ł',
	    'm', 'M', 'n', 'N', 'ń', 'Ń', 'o', 'O', 'ó', 'Ó', 'p', 'P', 'q', 'Q', 'r', 'R',
	    's', 'S', 'ś', 'Ś', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y',
	    'z', 'Z', 'ź', 'Ź', 'ż', 'Ż'
	];

	function polishSort(a, b, method) {
	    if(a == b){
		return 0;
	    }
	    var d = 0;
	    while(a[d] == b[d]) {
		d++;
	    }
	    a_position = 0; 
	    b_position = 0;

	    for(var i = 0; i < alphabet.length; i++) {
		    if(alphabet[i] == a[d]){
			a_position = i;
		    }

		    if(alphabet[i] == b[d]){
			b_position = i;
		    }
	    }
	    var less = -1;
	    var greater = 1;
	    
	    if (method == 'desc') {
		less = 1;
		greater = -1;
	    }
	    return a_position < b_position ? less : greater;
	}
	
	$.extend($.fn.dataTableExt.oSort, {

	    "polstring-asc": function ( a, b ) {
		
		return polishSort(a, b, 'asc');
	    },   

	    "polstring-desc": function (a, b) {
		
		return polishSort(a, b, 'desc');
	    },
	
	});
}());

</script>

<br>
</div>

</body>
</html>