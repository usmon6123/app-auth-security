package uz.yengilyechim.sign_in_sign_up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import uz.yengilyechim.sign_in_sign_up.entity.template.AbsLongEntity;
import uz.yengilyechim.sign_in_sign_up.enums.PermissionEnum;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role extends AbsLongEntity {

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<PermissionEnum> permissions;


}
