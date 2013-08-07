/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2013 by Rapid-I and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapid-i.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.operator.nio.model;

import java.util.Observable;
import java.util.Observer;

import com.rapidminer.example.Attributes;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;


/** The meta data either guessed by RapidMiner or specified by the user for a
 *  column of an excel file, csv file, etc.
 * 
 * @author Simon Fischer
 *
 */
public class ColumnMetaData extends Observable {

	/** Attribute name as specified in the file, or generated by the source. */
	private String originalAttributeName;
	/** Attribute name as specified by the user. */
	private String userDefinedAttributeName;
	private int attributeValueType;
	private String role;
	private boolean selected;
	
	
	public ColumnMetaData() {
		
	}
	
	public ColumnMetaData(String originalAttributeName, String userDefinedAttributeName, int attributeValueType, String role, boolean selected) {
		super();
		this.originalAttributeName = originalAttributeName;
		this.userDefinedAttributeName = userDefinedAttributeName;
		this.attributeValueType = attributeValueType;
		this.role = role;
		this.selected = selected;
	}
	/** Used to inform the validator about the ColumnMetaData object **/
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
	}
	public String getOriginalAttributeName() {
		return originalAttributeName;
	}
	public void setOriginalAttributeName(String originalAttributeName) {
		if (equals(this.originalAttributeName, originalAttributeName)) return;
		this.originalAttributeName = originalAttributeName;
		setChanged();
		notifyObservers();
	}
	public String getUserDefinedAttributeName() {
		return userDefinedAttributeName;
	}
	public void setUserDefinedAttributeName(String userDefinedAttributeName) {
		if (equals(this.userDefinedAttributeName, userDefinedAttributeName)) return;
		this.userDefinedAttributeName = userDefinedAttributeName;
		setChanged();
		notifyObservers();
	}
	public int getAttributeValueType() {
		return attributeValueType;
	}
	public void setAttributeValueType(int attributeValueType) {
		if (attributeValueType == this.attributeValueType) return;
		this.attributeValueType = attributeValueType;
		setChanged();
		notifyObservers();
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		if (equals(role, this.role)) return;
		this.role = role;
		setChanged();
		notifyObservers();
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public String toString() {
		return getRole() + " "+getUserDefinedAttributeName() + " ("+getOriginalAttributeName()+")"+getAttributeValueType()+ " "+(isSelected()?"x":"-");
	}

	public AttributeMetaData getAttributeMetaData() {
		String roleId = getRole();
		if (!Attributes.ATTRIBUTE_NAME.equals(roleId))
			return new AttributeMetaData(getUserDefinedAttributeName(), getAttributeValueType(), roleId);
		else
			return new AttributeMetaData(getUserDefinedAttributeName(), getAttributeValueType());
	}

	/** Returns whether the user specified a name different from the default. */
	public boolean isAttributeNameSpecified() {
		if (userDefinedAttributeName == null) {
			return false;
		}
		return !userDefinedAttributeName.equals(originalAttributeName);
	}
	
	private static boolean equals(String s1, String s2) {
		return ((s1 == null) && (s2 == null)) || ((s1 != null) && s1.equals(s2));  
	}
}