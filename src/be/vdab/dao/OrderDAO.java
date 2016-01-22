package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.vdab.entities.Order;
import be.vdab.entities.Order.Statussen;

public class OrderDAO extends AbstractDAO {
	private static final String BEGIN_SELECT = "SELECT id, orderDate, requiredDate, shippedDate, comments, "
			+ "customerId, status FROM orders"; 
	private static final String SELECT_UNSHIPPED = BEGIN_SELECT + " where status not in ('SHIPPED','CANCELLED')";
	private static final String SELECT_BY_ID = BEGIN_SELECT + " where id = ?";	
	
	private static final String UPDATE_AS_SHIPPED = "UPDATE products "
			+ "INNER JOIN orderdetails on products.id = orderdetails.productId "
			+ "INNER JOIN orders on orders.id = orderdetails.orderId SET "
			+ "quantityInStock = quantityInStock - quantityOrdered, "
			+ "quantityInOrder = quantityInOrder - quantityOrdered, "			
			+ "status = '" + Statussen.SHIPPED  + "', "
			+ "shippedDate = CURDATE() "
			+ "WHERE quantityOrdered <= quantityInStock and "
			+ "orderId = ?";
	
	private static final String SELECT_PRODUCT_COUNT = "select count(*) from orderdetails where orderId = ?";
	
	
	private CustomerDAO customerDAO;
	private OrderDetailDAO orderDetailDAO;
	
	public OrderDAO(CustomerDAO customerDAO){
		this.customerDAO = customerDAO;
	}
	
	public OrderDAO(CustomerDAO customerDAO, OrderDetailDAO orderDetailDAO){
		this(customerDAO);
		this.orderDetailDAO = orderDetailDAO;
	}
	
	public List<Order> findOpenOrders(){
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_UNSHIPPED)){
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()){
				orders.add(resultRijNaarOrder(resultSet));
			}
			return orders;
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	public Order findById(int id){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				return (resultSet.next() ? resultRijNaarOrder(resultSet): null);
			}
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	public Order findByIdWithDetails(int id){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				return (resultSet.next() ? resultRijNaarOrderMetOrderDetail(resultSet): null);
			}
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	public List<Integer> setAsShipped(List<Integer> orderIds){
		List<Integer> failedIDS = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_AS_SHIPPED);
				PreparedStatement statementCountProducts = connection.prepareStatement(SELECT_PRODUCT_COUNT)){
			for (int orderID : orderIds){
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				statement.setInt(1, orderID);
				if (statement.executeUpdate() >= 1){
					statementCountProducts.setInt(1, orderID);
					try (ResultSet resultSet = statementCountProducts.executeQuery()){
						if (resultSet.next()){
							int aantal = resultSet.getInt(1);							
							if (aantal == (statement.getUpdateCount() - 1)){
								connection.commit();
							} else {
								connection.rollback();
								failedIDS.add(orderID);
							}
						}
					}
				}else {
					connection.rollback();
					failedIDS.add(orderID);
				}
			}
			return failedIDS;
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}	
	
	private Order resultRijNaarOrder(ResultSet resultSet) throws SQLException{
		return new Order(resultSet.getInt("id"), 
						 resultSet.getDate("orderDate"),
						 resultSet.getDate("requiredDate"),
						 resultSet.getDate("shippedDate"),
						 resultSet.getString("comments"),
						 customerDAO.findByID(resultSet.getInt("customerId")),
						 Order.Statussen.valueOf(resultSet.getString("status")));
	}
	
	private Order resultRijNaarOrderMetOrderDetail(ResultSet resultSet) throws SQLException{
		return new Order(resultSet.getInt("id"), 
						 resultSet.getDate("orderDate"),
						 resultSet.getDate("requiredDate"),
						 resultSet.getDate("shippedDate"),
						 resultSet.getString("comments"),
						 customerDAO.findByID(resultSet.getInt("customerId")),
						 Order.Statussen.valueOf(resultSet.getString("status")),
						 orderDetailDAO.findByOrderID(resultSet.getInt("id")));
	}
}
