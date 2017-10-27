
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello Varsha</title>
</head>
<body>
<%
String[] queries= (String[])request.getAttribute("queries");
ArrayList<ArrayList<String>>result= (ArrayList<ArrayList<String>>)request.getAttribute("result");
int i=0;
if(queries!=null){
for(String query:queries){
	out.println("<b>");
	out.println(query);
	out.println("</b>");
	out.println("<br/>");
	if(result!=null){
	ArrayList<String>val=result.get(i);
	if(val!=null){
		for(String innerVal:val){
		out.println(innerVal);
		out.println("<br/>");
		}
	}
	i++;
	out.println("<br/>");
	}
}
}
%>
</body>
</html>