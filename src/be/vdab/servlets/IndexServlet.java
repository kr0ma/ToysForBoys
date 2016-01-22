package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.CountryDAO;
import be.vdab.dao.CustomerDAO;
import be.vdab.dao.OrderDAO;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private static final String REDIRECT_URL = "%s/index.htm?";
	
	private static final String ORDERS = "orders";
	private static final String STOCK_ERROR_MESSAGE = "Shipping failed for order(s) %s, not enough stock";
	private static final String SHIPMENT_ERROR_MESSAGES = "shipmentErrorMessages";
	private final static Logger logger = Logger.getLogger(IndexServlet.class.getName());

	
	private static transient CountryDAO countryDAO = new CountryDAO();
	private static transient CustomerDAO customerDAO = new CustomerDAO(countryDAO);
	private static transient OrderDAO orderDAO = new OrderDAO(customerDAO);
	
	@Resource(name = OrderDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		countryDAO.setDataSource(dataSource);
		customerDAO.setDataSource(dataSource);
		orderDAO.setDataSource(dataSource);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("mislukt") != null){
			String[] failedIDS = request.getParameter("mislukt").split(",");
			List<String> errorMessages = new ArrayList<>();
			for (String failedID : failedIDS){				
				try {
					if (Integer.parseInt(failedID) > 0){
						errorMessages.add(String.format(STOCK_ERROR_MESSAGE, failedID));
					}					
				} catch (NumberFormatException ex){
					logger.log(Level.SEVERE, "Some hacker did something!", ex);
				}				
			}			
			request.setAttribute(SHIPMENT_ERROR_MESSAGES, errorMessages);
		}
		request.setAttribute(ORDERS, orderDAO.findOpenOrders());
		request.getRequestDispatcher(VIEW).forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder redirectURL = new StringBuilder(REDIRECT_URL);
		if (request.getParameterValues("id") != null){
			List<Integer> ids = new ArrayList<>();
			for (String idString :request.getParameterValues("id")){
				try {
					int id = Integer.parseInt(idString);	
					ids.add(id);
				} catch (NumberFormatException ex){					
					logger.log(Level.SEVERE, "Some hacker did something!", ex);	
				}
			}
			if (!ids.isEmpty()){
				List<Integer> failedShipmentIDS = orderDAO.setAsShipped(ids);
				if (!failedShipmentIDS.isEmpty()){					
					redirectURL.append("&mislukt=");				
					for (int id : failedShipmentIDS){
						redirectURL.append(id).append(",");
					}
					redirectURL.deleteCharAt(redirectURL.length() - 1);						
				}				
			}
		}	
		response.sendRedirect(String.format(redirectURL.toString(), request.getContextPath()));
	}

}
