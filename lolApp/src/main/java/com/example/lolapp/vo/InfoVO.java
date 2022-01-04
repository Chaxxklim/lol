package com.example.lolapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class InfoVO {
    private long gameDateTime;
    private float gameLength;
    private String gameVariation;
    private String gameVersion;
    private List<ParticipantVO> participantVOList;
    private int QueueId;
    private int tftSetNumber;

}
