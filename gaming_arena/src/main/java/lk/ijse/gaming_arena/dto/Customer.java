package lk.ijse.gaming_arena.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Customer {
    private String customer_id;
    private String employee_id;
    private String customer_name;
    private String customer_address;
    private String customer_contact;
    private String customer_email;
}
