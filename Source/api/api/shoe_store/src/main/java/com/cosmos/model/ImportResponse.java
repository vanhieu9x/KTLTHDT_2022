package com.cosmos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImportResponse {
    private int ImportId;
    private String nameManufacture;
    private String nameEmployee;
    private Date createday;
    private float totalprice;
    private int status;

}
