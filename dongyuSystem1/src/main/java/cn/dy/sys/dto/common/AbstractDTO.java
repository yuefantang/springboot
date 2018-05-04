package cn.dy.sys.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractDTO {
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }
}
