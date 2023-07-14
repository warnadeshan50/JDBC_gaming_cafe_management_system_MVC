package lk.ijse.gaming_arena.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderTM {

        private String item_id;
        private String description;
        private Integer qty;
        private Double onePrice;
        private Double total;
        private Button btn;
}
