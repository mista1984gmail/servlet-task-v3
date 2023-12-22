package ru.clevertec.servlet.task.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UUIDToken {

	private Long id;
	private Long userId;
	private String token;

}
