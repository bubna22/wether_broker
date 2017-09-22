<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<title>weather broker</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/update" method="GET">
<em>update</em>
<input name="townName">
<input type="submit">
</form>
</body>
</html>
