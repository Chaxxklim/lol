package com.example.lolapp.vo;

import lombok.Data;

import java.util.List;
@Data
public class MetadataVO {
    private String dataVersion;
    private String matchId;
    private List<String> participants;
}
