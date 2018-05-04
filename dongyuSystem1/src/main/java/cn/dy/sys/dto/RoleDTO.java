package cn.dy.sys.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import cn.dy.sys.dto.common.AbstractDTO;

public class RoleDTO extends AbstractDTO {
    /**
     * 角色名
     */
    @NotBlank
    @Length(max = 255)
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
