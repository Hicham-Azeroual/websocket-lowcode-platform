package com.hicham.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Getter
@Setter

@AllArgsConstructor


@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerationRequest {
    private String architectureType;
    private String parameters;

    public GenerationRequest() {} // <-- Add this default constructor



} 


