package cn.edu.nuaa.aadl2.generator.utils;

import java.util.ArrayList;
import java.util.List;

import org.osate.aadl2.Mode;
import org.osate.aadl2.ModeTransition;

public class AadlModeVisitors {
	/**
	 * 返回源模式为mode的ModeTransition列表
	 * @param mode
	 * @param transitions
	 * @return
	 */
	public static List<ModeTransition> getTransitionsWhereSrc(Mode mode, List<ModeTransition> transitions){
		List<ModeTransition> resulTransitions = new ArrayList<ModeTransition>();
		for(ModeTransition modeTransition : transitions) {
			if(modeTransition.getSource().equals(mode)) {
				resulTransitions.add(modeTransition);
			}
		}
		return resulTransitions;
	}
}
