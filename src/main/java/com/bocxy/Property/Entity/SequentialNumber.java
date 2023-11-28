package com.bocxy.Property.Entity;

import javax.persistence.*;

@Entity
@Table(name = "SequentialNumber")
public class SequentialNumber {

    @Id
    private Integer id;

    private Integer sequenceValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequenceValue() {
        return sequenceValue;
    }

    public void setSequenceValue(Integer sequenceValue) {
        this.sequenceValue = sequenceValue;
    }
}