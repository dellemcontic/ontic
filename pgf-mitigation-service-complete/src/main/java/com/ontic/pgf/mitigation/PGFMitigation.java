package com.ontic.pgf.mitigation;

import java.util.List;

import com.ontic.pgf.mitigation.interfaces.Plan;

public interface PGFMitigation {
	
   boolean executePlans(List <Plan>plans);

}
