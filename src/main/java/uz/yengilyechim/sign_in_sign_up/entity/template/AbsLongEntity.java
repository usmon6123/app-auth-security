package uz.yengilyechim.sign_in_sign_up.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbsLongEntity extends AbsDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
