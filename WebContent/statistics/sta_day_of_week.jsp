<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../_inc/inc_head.jsp" %>
<%
request.setCharacterEncoding("utf-8");
String dow = (String)request.getAttribute("dayOfWeek");
String sch = (String)request.getAttribute("sch");
String[] dayOfWeek = dow.split(",");
int sunS = 0, monS = 0, tueS = 0, wedS = 0, thuS = 0, friS = 0, satS = 0;
int sunC = 0, monC = 0, tueC = 0, wedC = 0, thuC = 0, friC = 0, satC = 0;
for (int i = 0; i < dayOfWeek.length; i++) {	//3:2,4:2,5:3
	String[] tmp = dayOfWeek[i].split(":");
	if(tmp[0].equals("1")) {
		sunS = Integer.parseInt(tmp[1]);
		sunC = sunS - Integer.parseInt(tmp[2]);
	} else if(tmp[0].equals("2")) {
		monS = Integer.parseInt(tmp[1]);
		monC = monS - Integer.parseInt(tmp[2]);
	} else if(tmp[0].equals("3")) {
		tueS = Integer.parseInt(tmp[1]);
		tueC = tueS - Integer.parseInt(tmp[2]);
	} else if(tmp[0].equals("4")) {
		wedS = Integer.parseInt(tmp[1]);
		wedC = wedS - Integer.parseInt(tmp[2]);
	} else if(tmp[0].equals("5")) {
		thuS = Integer.parseInt(tmp[1]);
		thuC = thuS - Integer.parseInt(tmp[2]);
	} else if(tmp[0].equals("6")) {
		friS = Integer.parseInt(tmp[1]);
		friC = friS - Integer.parseInt(tmp[2]);
	} else if(tmp[0].equals("7")) {
		satS = Integer.parseInt(tmp[1]);
		satC = satS - Integer.parseInt(tmp[2]);
	}
}

String sd = "", ed = "";
if (sch != null && !sch.equals("")) {
	
	String[] arrSch = sch.split(",");
	for (int i = 0; i < arrSch.length; i++) {
		char c = arrSch[i].charAt(0);
		if (c == 'r') {
			sd = arrSch[i].substring(1, arrSch[i].indexOf(':'));
			ed = arrSch[i].substring(arrSch[i].indexOf(':') + 1);		
		}
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script>
var color = Chart.helpers.color;
var barChartData = {
	labels: ['?????????', '?????????', '?????????', '?????????', '?????????', '?????????', '?????????'],
	datasets: [{
		label: '?????????',
		backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
		borderColor: window.chartColors.red,
		borderWidth: 1,
		data: [<%=monS %>, <%=tueS %>, <%=wedS %>, <%=thuS %>, <%=friS %>, <%=satS %>, <%=sunS %>]
	}, {
		label: '?????????',
		backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
		borderColor: window.chartColors.blue,
		borderWidth: 1,
		data: [<%=monC %>, <%=tueC %>, <%=wedC %>, <%=thuC %>, <%=friC %>, <%=satC %>, <%=sunC %>]
	}
/* -- ????????? ?????? ??????
	, {
		label: '?????????2',
		backgroundColor: color(window.chartColors.green).alpha(0.5).rgbString(),
		borderColor: window.chartColors.blue,
		borderWidth: 1,
		data: [15, 20, 17, 13, 28, 22, 9]
	}
*/
	]
};
window.onload = function() {
	var ctx3 = document.getElementById('canvas1').getContext('2d');
	window.myBar = new Chart(ctx3, {
		type: 'bar',
		data: barChartData,
		options: {
			responsive: true,
			legend:{ position:'top' }, 
			title:{ display:true, text:'<%if (!sd.equals("") || !ed.equals("")) { out.print(sd + " ~ " + ed + "??????  ????????? ?????????"); } else {%>?????? 30??? ????????? ?????????<%}%>' }
		}
	});
	 var minutes = 10;

	  var fiveMinutes = (60 * minutes) - 1,
	    display = document.querySelector('#MyTimer');
	  startTimer(fiveMinutes, display);
};
function makeSch() {
	var frmM = document.memSchFrm;
	var frm = document.frmHidden;
	var sch = "";
	var sd = frmM.sd.value;
	var ed = frmM.ed.value;
	if (sd != "" && ed != "" && sd == ed) {
		alert("??????????????? ??????????????????.")
		history.href = "/cworld_admin/sta_day_of_week";
		return false;
	}
	if (sd != "" || ed != "") {
		if (sch != "") {
			sch += ","
		}
		sch += "r" + sd + ":" + ed;
	}

	frm.sch.value = sch;
	frm.submit();
}
$(document).ready(function() {
	$.datepicker.regional['ko'] = {
		closeText: '??????',
		prevText: '?????????',
		nextText: '?????????',
		currentText: '??????',
		monthNames: ['1???','2???','3???','4???','5???','6???',
		'7???','8???','9???','10???','11???','12???'],
		monthNamesShort: ['1???','2???','3???','4???','5???','6???',
		'7???','8???','9???','10???','11???','12???'],
		dayNames: ['???','???','???','???','???','???','???'],
		dayNamesShort: ['???','???','???','???','???','???','???'],
		dayNamesMin: ['???','???','???','???','???','???','???'],
		//buttonImage: "/images/kr/create/btn_calendar.gif",
		buttonImageOnly: true,
		// showOn :"both",
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		duration:200,
		showAnim:'show',
		showMonthAfterYear: false
		// yearSuffix: '???'
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	$( "#edusdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#eduedate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});
function resetSch() {
	var sch = "";
	var frm = document.frmHidden;
	frm.sch.value = sch;
	frm.submit();
}
</script>
<style>
#subnavi04 { display:block; }
</style>
</head>
<body>
<div class="contentDiv">
<table align="center" width="500">
<tr><td>
<form name="frmHidden">
	<input type="hidden" name="sch" value="<%=sch %>" />
</form>
<form name="memSchFrm" id="schFrm">
?????? : 
<input type="text" name="sd" id="edusdate" value="<%=sd %>" size="10" class="ipt" /> ~
<input type="text" name="ed" id="eduedate" value="<%=ed %>" size="10" class="ipt" />
<input type="button" value="??????" class="btn" onclick="makeSch();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="???????????????" class="btn resetSch" onclick="resetSch();" />
</form>
</td></tr>
</table>
<div id="container1">
	<canvas id="canvas1"></canvas>
</div>
</div>
</body>
</html>