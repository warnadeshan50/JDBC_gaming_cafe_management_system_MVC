package lk.ijse.gaming_arena.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CafeDTO {
    String computer_id;
    Double start_time;
    Double end_time;
    Double payment;
}
