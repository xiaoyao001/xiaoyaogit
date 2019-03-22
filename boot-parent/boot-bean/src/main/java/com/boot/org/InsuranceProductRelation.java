package com.boot.org;

import java.io.Serializable;

public class InsuranceProductRelation implements Serializable{

	private static final long serialVersionUID = -958771031893579136L;
	/**投保人与被保人关系表id*/
	private Integer insuranceProductRelationId;
	/**关系名称*/
	private String relationName;
	/**关系编码*/
	private Integer relationNumber;
	/**删除标识*/
	private Integer deleteFlag;
	
	public InsuranceProductRelation(){}

	public InsuranceProductRelation(Integer insuranceProductRelationId, String relationName, Integer relationNumber,
			Integer deleteFlag) {
		this.insuranceProductRelationId = insuranceProductRelationId;
		this.relationName = relationName;
		this.relationNumber = relationNumber;
		this.deleteFlag = deleteFlag;
	}

	public Integer getInsuranceProductRelationId() {
		return insuranceProductRelationId;
	}

	public void setInsuranceProductRelationId(Integer insuranceProductRelationId) {
		this.insuranceProductRelationId = insuranceProductRelationId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public Integer getRelationNumber() {
		return relationNumber;
	}

	public void setRelationNumber(Integer relationNumber) {
		this.relationNumber = relationNumber;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
