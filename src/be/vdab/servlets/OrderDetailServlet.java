package be.vdab.servlets;

import java.io.IOException;
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
import be.vdab.dao.OrderDetailDAO;
import be.vdab.dao.ProductDAO;

/**
 * Servlet implementation class OrderDetailServlet
 */
@WebServlet("/orderdetail.htm")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/orderdetail.jsp";	
	private static final String ORDER = "order";	
	
	private final static Logger logger = Logger.getLogger(OrderDetailServlet.class.getName());
	
	private static transient CountryDAO countryDAO = new CountryDAO();
	private static transient CustomerDAO customerDAO = new CustomerDAO(countryDAO);
	private static transient ProductDAO productDAO = new ProductDAO();
	private static transient OrderDetailDAO orderDetailDAO = new OrderDetailDAO(productDAO);
	private static transient OrderDAO orderDAO = new OrderDAO(customerDAO, orderDetailDAO);
	
	@Resource(name = OrderDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource){
		countryDAO.setDataSource(dataSource);
		customerDAO.setDataSource(dataSource);
		productDAO.setDataSource(dataSource);
		orderDetailDAO.setDataSource(dataSource);
		orderDAO.setDataSource(dataSource);
	}
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("orderID") != null){
			try {
				int orderID = Integer.parseInt(request.getParameter("orderID"));
				if (orderID > 0){
					request.setAttribute(ORDER, orderDAO.findByIdWithDetails(orderID));
				}				
			} catch (NumberFormatException ex){
				request.setAttribute("fout", "Geen correct orderID");
				logger.log(Level.SEVERE, "Some hacker did something!", ex);				
			}
		}else {
			request.setAttribute("fout", "Geen correct orderID");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
