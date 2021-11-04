package com.example.zmart.storeproducer.validation.violation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViolationResponse {

    private List<Violation> violations = new ArrayList<>();
}
