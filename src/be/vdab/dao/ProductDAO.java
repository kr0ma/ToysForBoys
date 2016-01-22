package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.vdab.entities.Product;

public class ProductDAO extends AbstractDAO{
	private static final String BEGIN_SELECT = "select id, name, scale, description, "
			+ "quantityInStock, quantityInOrder, buyPrice, productlineId from products";
	private static final String SELECT_BY_PRODUCTID = BEGIN_SELECT + " where id = ?";

	public Product findByID(int id){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_PRODUCTID)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				return (resultSet.next()? resultRijNaarProduct(resultSet): null);
			}
		} catch (SQLException ex){
			throw new DAOException(ex);
		}		
	}
	
	private Product resultRijNaarProduct(ResultSet resultSet) throws SQLException{
		return new Product(resultSet.getInt("id"), 
				resultSet.getString("name"), 
				resultSet.getString("scale"),
				resultSet.getString("description"), 
				resultSet.getInt("quantityInStock"), 
				resultSet.getInt("quantityInOrder"), 
				resultSet.getBigDecimal("buyPrice"),
				resultSet.getInt("productlineID"));
	}	

}
