<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.FileInputStream"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>索引结果：<%=request.getParameter("searchText")%></title>
<style>
a {
	font-size: 20px;
}
</style>
</head>

<body>


	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<%
		String search = request.getParameter("searchText");
	%>
	<%!SearchTest st = new SearchTest();%>

	<%
		st.setUp();
		Set<String> resultDir = st.testTermQuery(search);
	%>
	<%
		out.println("共搜索到关于\"" + search + "\"的" + resultDir.size() + "个文档\n");
	%>
	<br>
	<table>
		<%
			for (String s : resultDir) {
		%>

		<tr align="left">
			<a href=<%=s%>><%=s%></a>
		</tr>
		<br>
		<%
			File f = new File(s);
				InputStreamReader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
				BufferedReader br = new BufferedReader(reader);
				String line = "";
				line = br.readLine();

				while (line != null) {
		%>

		<tr>
			<%=line%>
		</tr>
		<br>
		<%
			line = br.readLine();
				}
				br.close();
				reader.close();
			}
		%>
	</table>

</body>
</html>