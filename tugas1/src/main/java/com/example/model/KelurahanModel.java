package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel
{
    private Integer id;
    private String kode_kelurahan;
    private String nama_kelurahan;
    private Integer id_kecamatan;
    private String nama_kecamatan;
    private String kode_pos;
    private Integer id_kota;
    private String kode_kecamatan;
    private String nama_kota;
    private String kode_kota;
    
    
   

}
