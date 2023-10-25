package com.nexcodemm.lms.model.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotBlank
public class DamagedRequest {

	private List<String> generatedIds;
}
