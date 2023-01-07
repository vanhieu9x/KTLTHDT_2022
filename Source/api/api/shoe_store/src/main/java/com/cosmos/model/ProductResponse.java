package com.cosmos.model;

import com.cosmos.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int productId;
    private String productName;
    private int number;
    private float price;
    private String description;
    private List<Image> listimages;
}
