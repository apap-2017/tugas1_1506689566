package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.model.KelurahanModel;

public interface SidukService
{
    PendudukModel selectPenduduk (String nik);
    
    
    
    KeluargaModel alamatLengkap (int id);
    
    KeluargaModel selectKeluarga (String nomor_kk);
    
    KeluargaModel selectKeluargaById (int id_keluarga);
    
    int maxIdKeluarga();
    
    List<PendudukModel> selectAnggotaKeluarga (int id_keluarga);
    
    List<KotaModel> selectAllKota();
    
    List<KelurahanModel> selectKelurahanByIdKecamatan(int id_kecamatan);
    
    List<KecamatanModel> selectKecamatanByIdKota (int id_kota);
    
    List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);
    
    KeluargaModel kelKecKot (String nomor_kk);
    
    KelurahanModel getKelKecKot (int id_kelurahan);
    
    void ubahPenduduk (PendudukModel penduduk);

    void ubahKeluarga (KeluargaModel keluarga);

    void addPenduduk (PendudukModel penduduk);
    
    void ubahIsWafat (String nik);
    
    void kk_tidak_berlaku (int id_keluarga);
    
    int getIdKelurahan (String nama_kelurahan);
    
    String getNikWithSameData (String nikBeforeNoUrut);
    
    void addKeluarga (KeluargaModel keluarga);
    
    String getNkkWithSameData (String nkkBeforeNoUrut);
    
    PendudukModel selectTua(int id_kelurahan);
    
    PendudukModel selectMuda(int id_kelurahan);
    
    PendudukModel selectPendudukTuaByIdKecamatan(int id_kecamatan);
	
    PendudukModel selectPendudukMudaByIdKecamatan(int id_kecamatan);
   
    
//
//
//    void deleteStudent (String npm);
//    
//    void updateStudent (StudentModel student);
//    
//    
//    CourseModel selectCourse (String id_course);
//    
//    List<CourseModel> selectAllCourses ();
//    
//    List<StudentModel> selectStudentCourses ();
}
