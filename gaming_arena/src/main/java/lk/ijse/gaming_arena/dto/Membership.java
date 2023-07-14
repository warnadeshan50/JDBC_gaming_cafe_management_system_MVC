package lk.ijse.gaming_arena.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Membership {
    private String membership_id;
    private String customer_id;
    private String type_of_membership;
    private Double monthly_payment;
}
