<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<my:header>
    <jsp:attribute name="first">
    FIRST
    </jsp:attribute>
    <jsp:attribute name="second">
    SECOND
    </jsp:attribute>
    <jsp:attribute name="third">
    THIRD
    </jsp:attribute>
</my:header>
${third}
<ctg:simple>SIMPLE TAG</ctg:simple>
<ctg:classic>CLASSIC TAG

</ctg:classic>
<font color="red">${HomeMessage}</font>
<jsp:include page="footer.jsp"/>
