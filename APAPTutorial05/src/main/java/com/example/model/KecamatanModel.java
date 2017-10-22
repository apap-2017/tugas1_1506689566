package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel
{
    private Integer id;
    private String kode_kecamatan;
    private Integer id_kota;
    private String nama_kecamatan;
    
   

}
