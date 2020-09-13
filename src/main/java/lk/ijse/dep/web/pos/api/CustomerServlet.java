package lk.ijse.dep.web.pos.api;

import lk.ijse.dep.web.pos.business.custom.CustomerBO;
import lk.ijse.dep.web.pos.dto.CustomerDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    private CustomerBO customerBO;

    @Override
    public void init() throws ServletException {
        customerBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(CustomerBO.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        if (!(id.matches("C\\d{3}")) || name.trim().length() < 3 || address.trim().length() < 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customer : allCustomers) {
                if(id.equals(customer.getId())){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
            customerBO.saveCustomer(id,name,address);
            out.println("Success fully Deleted");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            if (id == null) {
                List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
                allCustomers.forEach(out::println);
            } else {
                try {
                    CustomerDTO customer = customerBO.getCustomer(id);
                    out.println(customer);
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String line = null;
        String requestBody = "";

        while ((line = reader.readLine()) != null) {
            requestBody += line;
        }

        String id = method(requestBody, "id");
        String name = method(requestBody, "name");
        String address = method(requestBody, "address");

        System.out.println("Customer ID :" + id);
        System.out.println("Customer Name :" + name);
        System.out.println("Customer Address :" + address);

        if ( name.trim().length() < 3 || address.trim().length() < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");
        try {
            List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO customer : allCustomers) {
                if(id.equals(customer.getId())){
                    customerBO.updateCustomer(name, address, id);
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    System.out.println("Customer has Been Updated Successfully");
                    return;
                }
            }
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (NoSuchFieldException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        String id = method(queryString, "id");

        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } else {
            try {
                customerBO.deleteCustomer(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                System.out.println("Successfully Deleted");
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    String method(String query, String letter) {

        if (query == null) {
            return null;
        }
        String id = null;
        String[] queryParameters = query.split("&");
        for (String queryParameter : queryParameters) {
            if (queryParameter.contains("=") && queryParameter.startsWith(letter)) {
                id = queryParameter.split("=")[1];
                break;
            }
        }
        if (id == null) {
            return null;
        }
        return id;
    }
}
