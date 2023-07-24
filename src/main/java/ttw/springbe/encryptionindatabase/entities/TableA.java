package ttw.springbe.encryptionindatabase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

@Getter
@Setter
@Entity
@Table(name = "table_a")
public class TableA {
    @Id
    @Column(name = "id")
    private int id;

    @ColumnTransformer(forColumn = "current_value",
            read = "pgp_sym_decrypt(current_value, '${password}')",
            write = "pgp_sym_encrypt(?, '${password}')")
    @Column(name = "current_value", columnDefinition = "bytea")
    private String currentValue;
} 
