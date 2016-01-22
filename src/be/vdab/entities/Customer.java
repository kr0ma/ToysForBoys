package be.vdab.entities;

public class Customer {
	private int id;
	private String name, streetAndNumber, city, state, postalCode;	
	private Country country;
	
	public Customer(int id, String name, String streetAndNumber,
			String city, String state, String postalCode, Country country) {
		this.id = id;		
		this.name = name;
		this.streetAndNumber = streetAndNumber;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStreetAndNumber() {
		return streetAndNumber;
	}
	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	
}
