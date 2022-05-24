package uz.yengilyechim.sign_in_sign_up.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbsIntegerEntity extends AbsDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
