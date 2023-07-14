package lk.ijse.gaming_arena.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductTM {
    private String item_id;
    private String description;
    private Double onePrice;
    private String status;
}
