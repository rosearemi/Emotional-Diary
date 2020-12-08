package com.example.diary.domain.board;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.example.diary.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String bno;
	
	@Column(length = 500)
	private String title;
	
	@Column(length = 10000)
	private String contents;
	
	@CreationTimestamp
	private Timestamp dateTime;
	
	@JsonIgnoreProperties({"board"})
	@JoinColumn(name="memberId")
	@ManyToOne
	private Member member;
	
	
	
	
}