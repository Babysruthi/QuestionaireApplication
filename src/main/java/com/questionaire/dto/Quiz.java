package com.questionaire.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Quiz {

	private Long autoId;

	private String name;

	private String subCode;

	private Long id;
}