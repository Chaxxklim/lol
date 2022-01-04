package com.example.lolapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class UnitVO {
    private List<Integer> items;
    private String characterId;
    private String chosen;
    private String name;
    private int rarity;
    private int tier;
}
