<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!doctype html>
<html>
<head>
<vdab:head title="Orders"></vdab:head>
</head>
<body>
	<h1>Unshipped orders</h1>
	<c:if test="${not empty shipmentErrorMessages}">
		<div>
		<ul class="usermessage">
			<c:forEach var="message" items="${shipmentErrorMessages}"> 
				<li>${message}</li>
			</c:forEach>
		</ul>
		</div>
	</c:if>
	<c:if test="${not empty orders}">
		<div>	
		<form method="post">	
		<table>
			<tr>
			<td><input type='submit' value='Set as shipped'></td>
			</tr>
			<tr>
				<th>ID</th>
				<th>Ordered</th>
				<th>Required</th>
				<th>Customer</th>
				<th>Comments</th>
				<th>Status</th>
				<th>Ship</th>
			</tr>
			<c:forEach var="order" items="${orders}">
				<tr>
					<c:url value='/orderdetail.htm' var='detailURL'>
						<c:param name='orderID' value="${order.id}" />
					</c:url>
					<td><a href="<c:out value='${detailURL}'/>">${order.id}</a></td>
					<td><fmt:formatDate value='${order.orderDate}' type="date" dateStyle='short' /></td>
					<td><fmt:formatDate value='${order.requiredDate}' type="date" dateStyle='short' /></td>
					<td class="number">${order.customer.name}</td>
					<td>${order.comments}</td>
					<td class="capitalize"><img alt="${order.status}" src="<c:url value='/images/${order.status}.png'/>"/> ${order.status.toLowerCase()}</td>
					<td><input type='checkbox' name='id' value='${order.id}'></td>									
				</tr>
			</c:forEach>
		</table>
		</form>
		</div>
	</c:if>
</body>
</html>