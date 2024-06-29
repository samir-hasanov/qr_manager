package www.shelleyes.com.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Data
@Table(name = "qrManager", schema = "qr")
public class QrUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qrUser_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "qr_text")
    @Lob
    private String qrText;
    @ColumnDefault(value = "1")
    private Integer active;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;


}
