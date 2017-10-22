package com.example.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
    private Integer id;
    private String nik;
    private String niklama;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    
    private Integer jenis_kelamin;
    private Integer is_wni;
    private Integer id_keluarga;
    private Integer id_keluarga_lama;
    private String agama;
    private String pekerjaan;
    private String status_perkawinan;
    private String status_dalam_keluarga;
    private String golongan_darah;
    private Integer is_wafat;
   

}
