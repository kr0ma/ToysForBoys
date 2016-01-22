package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.vdab.entities.Country;

public class CountryDAO extends AbstractDAO{
	private static final String BEGIN_SELECT = "select id, name from countries";
	private static final String SELECT_BY_ID = BEGIN_SELECT + " where id = ?";
	
	
	public Country findByID(int id){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)){
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()){
				return (resultSet.next() ? resultRijNaarCountry(resultSet) : null);
			}
		} catch (SQLException ex){
			throw new DAOException(ex);
		}
	}
	
	private Country resultRijNaarCountry(ResultSet resultSet) throws SQLException{
		return new Country(resultSet.getInt("id"), resultSet.getString("name"));
	}
}
