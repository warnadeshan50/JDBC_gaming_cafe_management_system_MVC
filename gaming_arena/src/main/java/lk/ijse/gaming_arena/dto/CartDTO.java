package lk.ijse.gaming_arena.dto;


import lombok.*;

@Data
@AllArgsConstructor
public class CartDTO {
    String item_id;
    Integer qty;
    Double total_price;
}
