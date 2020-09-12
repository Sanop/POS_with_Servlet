package lk.ijse.dep.web.pos.api;

import lk.ijse.dep.web.pos.business.custom.CustomerBO;
import lk.ijse.dep.web.pos.business.custom.ItemBO;
import lk.ijse.dep.web.pos.dto.ItemDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "ItemServlet", urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    private ItemBO itemBO;

    @Override
    public void init() throws ServletException {
        itemBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(ItemBO.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("itemCode");
        String description = request.getParameter("description");
        String unitPrice = request.getParameter("unitPrice");
        String qty = request.getParameter("qty");

        if (!(code.trim().matches("I\\d{3}")) || description.trim().length() < 3 || !(unitPrice.trim().matches("\\d+")) || !(qty.trim().matches("\\d+"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            ItemDTO item = itemBO.getItem(code);
            if (item == null) {
                itemBO.saveItem(code, description, Integer.parseInt(qty), Double.parseDouble(unitPrice));
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println("Successfully Saved");
                return;
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("itemCode");

        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            if (code == null) {
                List<ItemDTO> allItems = itemBO.getAllItems();
                allItems.forEach(out::println);
            } else {
                try {
                    ItemDTO item = itemBO.getItem(code);
                    out.println(item);
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();
        String code = method(queryString, "itemCode");
        if(code == null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }else{
            try {
                itemBO.deleteItem(code);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                System.out.println("Successfully Deleted");
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                e.printStackTrace();
            }
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
        String itemCode = method(requestBody, "itemCode");
        String description = method(requestBody, "description");
        String unitPrice = method(requestBody, "unitPrice");
        String qty = method(requestBody, "qty");

        System.out.println("Item Code :" + itemCode);
        System.out.println("Item Description :" + description);
        System.out.println("Item UnitPrice :" + unitPrice);
        System.out.println("Item qty :" + qty);

        if (!(itemCode.trim().matches("I\\d{3}")) || description.trim().length() < 3 || !(unitPrice.trim().matches("\\d+")) || !(qty.trim().matches("\\d+"))) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");
        try {
            itemBO.getItem(itemCode);
            itemBO.updateItem(description, Integer.parseInt(qty), Double.parseDouble(unitPrice), itemCode);
            System.out.println("Successfully Updated");
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        } catch (NoSuchFieldException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
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
