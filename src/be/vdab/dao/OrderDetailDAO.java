package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.vdab.entities.OrderDetail;

public class OrderDetailDAO extends AbstractDAO{
	private static final String BEGIN_SELECT = "select orderId, productId, quantityOrdered, priceEach from orderdetails";
	private static final String SELECT_BY_ORDERID = BEGIN_SELECT + " where orderId = ?";	
	
	
	protected ProductDAO productDAO;
	
	public OrderDetailDAO(ProductDAO productDAO){
		this.productDAO = productDAO;
	}
	
	public List<OrderDetail> findByOrderID(int id){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_ORDERID)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				List<OrderDetail> orderDetails = new ArrayList<>();
				while (resultSet.next()){
					orderDetails.add(resultRijNaarOrderDetail(resultSet));
				}
				return orderDetails;
			}
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	private OrderDetail resultRijNaarOrderDetail(ResultSet resultSet) throws SQLException{
		return new OrderDetail(resultSet.getInt("orderId"),
							   productDAO.findByID(resultSet.getInt("productId")),
							   resultSet.getInt("quantityOrdered"),
							   resultSet.getBigDecimal("priceEach"));
	}		
	
	
}
