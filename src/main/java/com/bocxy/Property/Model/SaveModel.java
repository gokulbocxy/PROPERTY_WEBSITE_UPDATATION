package com.bocxy.Property.Model;

import com.bocxy.Property.Entity.Allottee;
import com.bocxy.Property.Entity.UnitData;
import lombok.Data;

import java.util.List;
@Data
public class SaveModel {
    private List<UnitData> unitdata;
    private List<Allottee>allottee;
}
