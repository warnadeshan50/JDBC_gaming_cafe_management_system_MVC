package lk.ijse.gaming_arena.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierTM {
    private String supplier_id;
    private String supplier_name;
    private String supplier_company;
    private String supplier_contact;
    private String supplier_email;
}
