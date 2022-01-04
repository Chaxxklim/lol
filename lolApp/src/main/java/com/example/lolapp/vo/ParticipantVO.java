package com.example.lolapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class ParticipantVO {
    private CompanionVO companionVO;
    private int goldLeft;
    private int lastRound;
    private int level;
    private int placement;
    private int playersEliminated;
    private String puuid;
    private float timeEliminated;
    private int totalDamageToPlayers;
    private List<TraitVO> traitVOList;
    private List<UnitVO> unitVOList;
}
