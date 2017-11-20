package com.universalbank.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.universalbank.entities.ScheduleTypeEnum;

@Named
@SessionScoped
public class EnumProviderController implements Serializable{

	public ScheduleTypeEnum [] getScheduleTypes() {
		return ScheduleTypeEnum.values();
	}
}
