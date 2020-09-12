package lk.ijse.dep.web.pos.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDTO {
    private String itemCode;
    private String description;
    private BigDecimal unitPrice;
    private int qty;
}
