package com.boot.org;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class InsuranceProductRelation implements Serializable{
	
	static List<InsuranceProductRelation>relaList = Arrays.asList(
				new InsuranceProductRelation(1, "xiaoyao1", 1231, 01),
				new InsuranceProductRelation(2, "xiaoyao2", 1232, 02),
				new InsuranceProductRelation(3, "xiaoyao3", 1233, 03),
				new InsuranceProductRelation(4, "xiaoyao4", 1234, 04),
				new InsuranceProductRelation(5, "xiaoyao5", 1235, 05),
				new InsuranceProductRelation(6, "xiaoyao6", 1236, 06)
			);
	
	public static void main(String[] args) {
		Stream <InsuranceProductRelation> streams = relaList.stream().filter((rela) -> rela.getInsuranceProductRelationId()>3);
		streams.forEach(System.out::println);
		
	}
	

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
