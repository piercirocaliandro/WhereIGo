package logic.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.beans.GroupBean;
import logic.controllers.ControllerFacade;

@WebServlet("/HomePageServlet")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChangePageServlet changeP = new ChangePageServlet();
		String act = request.getParameter("action");
		String page = null;
		if(act.equalsIgnoreCase("gohome")) {
			changeP.loadHomePageUserInfo(request);
			page = "HomePage.jsp";	
		}
		else if(act.equalsIgnoreCase("gobooktravel")) {
			page = "BookTravelStart.jsp";
			ControllerFacade fac = new ControllerFacade();
			List<String> cities = new ArrayList<>();
			List<GroupBean> beanList = new ArrayList<>();
			cities.addAll(fac.showLocations());
			fac.getGroups(beanList);
			request.setAttribute("grouplist", beanList);
			request.setAttribute("cities", cities);
		}
		else if(act.equalsIgnoreCase("rentAnAccomodation")) {
			page = "rent";
		}
		else if(act.equalsIgnoreCase("ChatRenter")) {
			page =	"chatRenter";
		}
		changeP.forwardPage(page, request, response);
	}
}
