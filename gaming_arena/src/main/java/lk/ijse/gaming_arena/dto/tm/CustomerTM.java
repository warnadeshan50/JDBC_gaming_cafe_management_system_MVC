package lk.ijse.gaming_arena.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerTM {
    String customer_id;
    String employee_id;
    String customer_name;
    String customer_address;
    String customer_contact;
    String customer_email;
}
