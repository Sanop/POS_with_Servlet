package lk.ijse.dep.web.pos.entity;

import java.math.BigDecimal;
import java.sql.Date;

public interface CustomEntity2 {

    String getOrderID();

    Date getOrderDate();

    String getCustomerID();

    String getCustomerName();

    BigDecimal getOrderTotal();
}
