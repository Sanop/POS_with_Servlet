package lk.ijse.dep.web.pos.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDTO {
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;
}
