package lk.ijse.gaming_arena.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String item_id;
    private String item_description;
    private Integer item_on_hand_qty;
    private Double item_one_qty_price;
    private String type;
    public String setStatus(int qty){
        if (qty<1){
            String status;
            return status="Out of Stock";
        }
        String status;
        return status="In Stock";
    }
}
