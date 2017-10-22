package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel
{
    private Integer id;
    private String nomor_kk;
    private String alamat;
    private String rt;
    private String rw;
    private Integer id_kelurahan;
    private Integer is_tidak_berlaku;
    private String nama_kelurahan;
    private String kode_kelurahan;
    private String nama_kecamatan;
    private String kode_kecamatan;
    private String nama_kota;
    private String kode_kota;
   
}
