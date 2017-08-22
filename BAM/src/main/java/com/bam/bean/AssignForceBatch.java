package com.bam.bean;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component
public class AssignForceBatch {
		
		private String name;
		private Timestamp startDate;
		private Timestamp endDate;
		private AssignForceCurriculum curriculum;
		private AssignForceTrainer trainer;
		private Integer ID;
		
		public AssignForceBatch() {}

		public AssignForceBatch(String name, Timestamp startDate, Timestamp endDate, AssignForceCurriculum curriculum,
				AssignForceTrainer trainer, Integer iD) {
			super();
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.curriculum = curriculum;
			this.trainer = trainer;
			ID = iD;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Timestamp getStartDate() {
			return startDate;
		}

		
		public void setStartDate(Timestamp startDate) {
			this.startDate = startDate;
		}

		public Timestamp getEndDate() {
			return endDate;
		}

		public void setEndDate(Timestamp endDate) {
			this.endDate = endDate;
		}

		public AssignForceTrainer getTrainer() {
			return trainer;
		}

		public void setTrainer(AssignForceTrainer trainer) {
			this.trainer = trainer;
		}

		public AssignForceCurriculum getCurriculum() {
			return curriculum;
		}

		public void setCurriculum(AssignForceCurriculum curriculum) {
			this.curriculum = curriculum;
		}

		public Integer getID() {
			return ID;
		}

		public void setID(Integer iD) {
			ID = iD;
		}

		@Override
		public String toString() {
			return "AssignForceBatch [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
					+ ", curriculum=" + curriculum + ", trainer=" + trainer + ", ID=" + ID + "]";
		}
		
}
