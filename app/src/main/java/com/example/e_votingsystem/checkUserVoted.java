package com.example.e_votingsystem;

import java.util.HashMap;
import java.util.Map;

public class checkUserVoted {
        Map<String,Integer> map=new HashMap<>();
        String electionName;
        checkUserVoted(Map<String,Integer> map,String electionName){
            this.electionName=electionName;
            this.map=map;
        }
}
