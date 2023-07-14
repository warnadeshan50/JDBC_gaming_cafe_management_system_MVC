package lk.ijse.gaming_arena.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Attendence {
    private String attendent_id;
    private String attendent_type;
    private Integer attendent_hours;
    private Double attendent_salary;
}
