package com.example.lvtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportHocSinhDTO {
    private List<HocSinhDTO> successList = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();
    private Map<String, List<String>> errorByHS = new HashMap<>();
}
