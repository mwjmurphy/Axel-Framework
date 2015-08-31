/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */


package org.xmlactions.pager.actions;


import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class RangeAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(RangeAction.class);
	private String name;
	private int from;
	private int to;
	private boolean random = false;
	
	private int index; // internal use only

	public String execute(IExecContext execContext)
	{
		execContext.put(name, this);
		index = -1;
		return "";
	}
	
	public String toString() {
		int number = 0;
		if (random == true) {
			int max=to;
		    int min=from;
		    int diff=max-min;
		    Random rn = new Random();
		    number = rn.nextInt(diff+1);
		    number+=min;			
		} else {
			if (to > from) {
				// increment
				if (index < from || index > to) {
					index = from;
				} else {
					index ++;
					if (index > to) {
						index = from;
					}
				}
			} else {
				// decrement
				if (index > from || index < to) {
					index = from;
				} else {
					index --;
					if (index < to) {
						index = from;
					}
				}
			}
			number = index;
		}
		return "" + number;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/**
	 * @return the random
	 */
	public boolean isRandom() {
		return random;
	}

	/**
	 * @param random the random to set
	 */
	public void setRandom(boolean random) {
		this.random = random;
	}

	
}
