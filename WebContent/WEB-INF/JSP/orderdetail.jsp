<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='vdab'%>
<!doctype html>
<html>
<head>
<vdab:head title="Orderdetail"></vdab:head>
</head>
<body>
	<c:if test="${not empty fout}">
		<h1>${fout}</h1>
	</c:if>
	<c:if test="${not empty order}">
		<h1>Order ${order.id}</h1>
		
		<dl>
			<dt>Ordered:</dt>
			<dd><fmt:formatDate value='${order.orderDate}' type="date" dateStyle='short' /></dd>
			
			<dt>Required:</dt>
			<dd><fmt:formatDate value='${order.requiredDate}' type="date" dateStyle='short' /></dd>
			
			<dt>Customer:</dt>
			<dd><ul>
					<li>${order.customer.name}</li>
					<li>${order.customer.streetAndNumber}</li>
					<li>${order.customer.postalCode} ${order.customer.city} ${order.customer.state}</li>
					<li>${order.customer.country.name}</li>
				</ul></dd>
			
			<dt>Comments:</dt>
			<dd>${order.comments}</dd>
			
			<dt>Details:</dt>
			<dd><c:if test="${not empty order.orderDetails}">
				<table>
				<tr>
					<th>Product</th>
					<th>Price each</th>
					<th>Quantity</th>
					<th>Value</th>
					<th>Deliverable</th>
			    </tr>
					<c:forEach var="orderDetail" items="${order.orderDetails}">
					<c:set value="${orderDetail.priceEach * orderDetail.quantityOrdered}" var="value"></c:set>
					
					<c:set value="${totalValue + value}" var="totalValue"></c:set>
					
					<tr>
						<td>${orderDetail.product.name}</td>
						<td class="number"><fmt:formatNumber value='${orderDetail.priceEach}' minFractionDigits='2'/></td>
						<td class="number"><fmt:formatNumber value='${orderDetail.quantityOrdered}'/></td>
						<td class="number"><fmt:formatNumber value='${value}' minFractionDigits='2'/></td>												
						<td class="center">${orderDetail.quantityOrdered <= orderDetail.product.quantityInStock ? '&check;' : '&cross;'}</td>
					</tr>
					</c:forEach>
				</table>
				</c:if></dd>
			
			<dt>Value:</dt>
			<dd><fmt:formatNumber value='${totalValue}' minFractionDigits='2'/></dd>
		</dl>
	</c:if>	
	<div><a href="<c:url value='/'/>">back</a></div>
</body>
</html>