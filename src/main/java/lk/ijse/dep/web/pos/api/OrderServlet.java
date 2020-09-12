package lk.ijse.dep.web.pos.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet(name = "OrderServlet" , urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderID");
        String orderDate = request.getParameter("orderDate");
        String customerID = request.getParameter("customerID");


        if(!(orderId.trim().matches("OD\\d{3}")) || !(orderDate.trim().matches("\\d{4}[-]\\d{2}[-]\\d{2}")) || !(customerID.trim().matches("C\\d{3}"))){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] itemCodes = request.getParameterValues("itemCode");
        String[] qties = request.getParameterValues("qty");
        String[] unitPrices = request.getParameterValues("unitPrice");

        System.out.println("-----------------------ORDER DETAIL------------------------");

        try(PrintWriter out = response.getWriter()){
            for (int i = 0; i < itemCodes.length; i++) {
                out.println("Item Code "+itemCodes[i]+", QTY "+qties[i]+", UnitPrice "+unitPrices[i]);
            }
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        System.out.println("Mudalali Wade Goda");
    }

}
