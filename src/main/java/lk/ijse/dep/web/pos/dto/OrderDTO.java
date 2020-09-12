package lk.ijse.dep.web.pos.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private String orderID;
    private String orderDate;
    private String customerID;
}
