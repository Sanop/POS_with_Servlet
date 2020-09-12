package lk.ijse.dep.web.pos.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

//@NamedQuery(name = "Item.getItemViaNamedQuery" ,query = "SElECT i FROM Item  i WHERE i.description LIKE CONCAT(?1,'%') AND i.qtyOnHand > ?2")
//@NamedQuery(name = "Item.getItemViaNamedQuery2" ,query = "SElECT i.code AS code,i.description AS description FROM Item  i WHERE i.description LIKE CONCAT(?1,'%') AND i.qtyOnHand > ?2")
//@NamedNativeQuery(name = "Item.getItemViaNamedNativeQuery" ,query = "SElECT * FROM Item AS i WHERE i.description LIKE CONCAT(:description,'%') AND i.qtyOnHand > :qty",
//resultClass = Item.class)
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item implements SuperEntity {
    @Id
    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
}
