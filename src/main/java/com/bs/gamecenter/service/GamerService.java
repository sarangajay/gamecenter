package com.bs.gamecenter.service;

import com.bs.gamecenter.model.GamerDTO;
import com.bs.gamecenter.model.GamerInput;


public interface GamerService {
    GamerDTO enrollGamer(GamerInput gamerInput);

}
