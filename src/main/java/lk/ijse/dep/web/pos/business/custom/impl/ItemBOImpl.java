package lk.ijse.dep.web.pos.business.custom.impl;

import lk.ijse.dep.web.pos.business.custom.ItemBO;
import lk.ijse.dep.web.pos.dto.ItemDTO;
import lk.ijse.dep.web.pos.repository.ItemRepository;
import lk.ijse.dep.web.pos.entity.Item;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ItemBOImpl implements ItemBO {

    // Dependency Declaration
    private ItemRepository itemRepository;

    public ItemBOImpl(ItemRepository itemRepository) {
        // Constructor Injection
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public String getNewItemCode() throws Exception {
        String lastItemCode = itemRepository.getFirstLastItemCodeByOrderByCodeDesc().getCode();

        if (lastItemCode == null) {
            return "I001";
        } else {
            int maxId = Integer.parseInt(lastItemCode.replace("I", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }
    }

    @Transactional(readOnly = true)
    public ItemDTO getItem(String code) throws Exception {
        Item item = itemRepository.findById(code).get();
        return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> getAllItems() throws Exception {
        List<Item> allItems = itemRepository.findAll();

        List<ItemDTO> items = new ArrayList<>();
        for (Item item : allItems) {
            items.add(new ItemDTO(item.getCode(), item.getDescription(),
                    item.getUnitPrice(),item.getQtyOnHand()));
        }
        return items;
    }

    public void saveItem(String code, String description, int qtyOnHand, double unitPrice) throws Exception {
        itemRepository.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));

    }

    public void deleteItem(String itemCode) throws Exception {

        itemRepository.deleteById(itemCode);

    }

    public void updateItem(String description, int qtyOnHand, double unitPrice, String itemCode) throws Exception {
        itemRepository.save(new Item(itemCode, description,
                BigDecimal.valueOf(unitPrice), qtyOnHand));
    }
}
