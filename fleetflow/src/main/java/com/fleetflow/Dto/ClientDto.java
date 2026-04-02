package com.fleetflow.Dto;


import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String name;
    private String email;
    private String ville;
    private String telephone;
}
