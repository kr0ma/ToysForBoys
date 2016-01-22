package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.vdab.entities.Customer;

public class CustomerDAO extends AbstractDAO{
	private static final String SELECT_BY_ID = "SELECT id, name, streetAndNumber, city, state, "
			+ "postalCode, countryId FROM customers where id = ?";
	
	private CountryDAO countryDAO;
	
	public CustomerDAO(CountryDAO countryDAO){
		this.countryDAO = countryDAO;
	}
	
	public Customer findByID(int id){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				return (resultSet.next() ? resultRijNaarCustomer(resultSet) : null);
			}
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	private Customer resultRijNaarCustomer(ResultSet resultSet) throws SQLException{
		return new Customer(resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("streetAndNumber"),
							resultSet.getString("city"),
							resultSet.getString("state"),
							resultSet.getString("postalCode"),
							countryDAO.findByID(resultSet.getInt("countryId")));
	}


}
