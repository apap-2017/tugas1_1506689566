package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SidukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SidukServiceDatabase implements SidukService
{
    @Autowired
    private SidukMapper sidukMapper;


    @Override
    public PendudukModel selectPenduduk (String nik)
    {
        log.info ("select penduduk with nik {}", nik);
        return sidukMapper.selectPenduduk (nik);
    }
    
   
    @Override
    public KeluargaModel alamatLengkap (int id)
    {
        log.info ("get alamat lengkap penduduk by id kelurahan {}", id);
        return sidukMapper.alamatLengkap (id);
    }
    
    @Override
    public KeluargaModel selectKeluarga (String nomor_kk)
    {
        log.info ("view keluarga beserta seluruh anggotanya by nomor_kk{}", nomor_kk);
        return sidukMapper.selectKeluarga (nomor_kk);
    }
    
    @Override
    public KeluargaModel selectKeluargaById (int id_keluarga)
    {
        log.info ("Select keluarga menggunakan id_keluarga {}", id_keluarga);
        return sidukMapper.selectKeluargaById (id_keluarga);
    }
    
    
    
    @Override
    public List<PendudukModel> selectAnggotaKeluarga (int id_keluarga)
    {
        log.info ("view keluarga beserta seluruh anggotanya by nomor_kk{}", id_keluarga);
        return sidukMapper.selectAnggotaKeluarga (id_keluarga);
    }
    
    @Override
    public List<KotaModel> selectAllKota()
    {
        log.info ("view all kota");
        return sidukMapper.selectAllKota();
    }
    
   
    
    public List<KecamatanModel> selectKecamatanByIdKota (int id_kota){
    	log.info ("view all kecamatan");
    	return sidukMapper.selectKecamatanByIdKota(id_kota);
    }
    
    @Override
    public List<KelurahanModel> selectKelurahanByIdKecamatan(int id_kecamatan)
    {
        log.info ("view all kelurahan");
        return sidukMapper.selectKelurahanByIdKecamatan(id_kecamatan);
    }
    
    @Override
    public List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan)
    {
        log.info ("view all kelurahan");
        return sidukMapper.selectPendudukByIdKelurahan(id_kelurahan);
    }
   
    
    
    @Override
    public KeluargaModel kelKecKot (String nomor_kk)
    {
        log.info ("view keluarga beserta seluruh anggotanya by nomor_kk{}", nomor_kk);
        return sidukMapper.kelKecKot (nomor_kk);
    }
    

    @Override
    public int maxIdKeluarga ()
    {
        log.info ("max id keluarga");
        return sidukMapper.maxIdKeluarga ();
    }
    
    @Override
    public PendudukModel selectTua (int id_kelurahan)
    {
        log.info ("select penduduk tertua di kelurahan");
        return sidukMapper.selectTua (id_kelurahan);
    }
    
    @Override
    public PendudukModel selectPendudukTuaByIdKecamatan(int id_kecamatan)
    {
        log.info ("select penduduk tertua di kecamatan");
        return sidukMapper.selectTua (id_kecamatan);
    }
    
    @Override
    public PendudukModel selectPendudukMudaByIdKecamatan(int id_kecamatan)
    {
        log.info ("select penduduk termuda di kecamatan");
        return sidukMapper.selectTua (id_kecamatan);
    }
    
    
    @Override
    public PendudukModel selectMuda (int id_kelurahan)
    {
        log.info ("select penduduk termuda di kelurahan");
        return sidukMapper.selectMuda (id_kelurahan);
    }
    
    @Override
    public KelurahanModel getKelKecKot (int id_kelurahan)
    {
        log.info ("view by id_kelurahan{}", id_kelurahan);
        return sidukMapper.getKelKecKot (id_kelurahan);
    }
    
    @Override
    public void addPenduduk (PendudukModel penduduk)
    {
        sidukMapper.addPenduduk (penduduk);
        
        
    }
    
    @Override
    public void ubahIsWafat (String nik)
    {
    	 log.info ("mati ", nik);
        sidukMapper.ubahIsWafat (nik);
        
        
    }
    
    @Override
    public void kk_tidak_berlaku (int id_keluarga)
    {
    	 log.info ("tidak berlaku lagi kk dengan nomor ", id_keluarga);
        sidukMapper.kk_tidak_berlaku (id_keluarga);
        
        
    }
    
    @Override
    public String getNikWithSameData (String nikBeforeNoUrut)
    {
    	log.info ("nik sebelum pake nomor urut", nikBeforeNoUrut);
        return sidukMapper.getNikWithSameData (nikBeforeNoUrut);
        
    }
    
    @Override
    public void addKeluarga (KeluargaModel keluarga)
    {
        sidukMapper.addKeluarga (keluarga);
        
        
    }
    
    @Override
    public int getIdKelurahan (String nama_kelurahan)
    {
        return sidukMapper.getIdKelurahan (nama_kelurahan);
        
        
    }
    
    @Override
    public String getNkkWithSameData (String nkkBeforeNoUrut)
    {
    	log.info ("nkk sebelum pake nomor urut", nkkBeforeNoUrut);
        return sidukMapper.getNkkWithSameData (nkkBeforeNoUrut);
        
    }
    
    
    

    
    @Override
    public void ubahPenduduk (PendudukModel penduduk)
    {
    	log.info ("Mengubah data penduduk dengan nik {}", penduduk.getNik());
        sidukMapper.ubahPenduduk(penduduk);
    }
    
    @Override
    public void ubahKeluarga (KeluargaModel keluarga)
    {
    	log.info ("Mengubah data penduduk dengan nkk {}", keluarga.getId());
        sidukMapper.ubahKeluarga(keluarga);
    }
    
   
    
    
    
//    
//    @Override
//    public CourseModel selectCourse (String id_course)
//    {
//        log.info ("select course with id {}", id_course);
//        return studentMapper.selectCourse(id_course);
//    }
//    
//    @Override
//    public List<CourseModel> selectAllCourses ()
//    {
//        log.info ("select all courses");
//        return studentMapper.selectAllCourses ();
//    }
//    
//    @Override
//    public List<StudentModel> selectStudentCourses ()
//    {
//        log.info ("select all courses and students");
//        return studentMapper.selectStudentCourses();
//    }
//
//
//    @Override
//    public List<StudentModel> selectAllStudents ()
//    {
//        log.info ("select all students");
//        return studentMapper.selectAllStudents ();
//    }
//
//

//
//
//    @Override
//    public void deleteStudent (String npm)
//    {
//    	log.info ("student" + npm + "deleted");
//    	studentMapper.deleteStudent(npm);
//    }
//    
//    @Override
//    public void updateStudent (StudentModel student)
//    {
//    	log.info ("student" + student.getNpm() + "updated");
//    	studentMapper.updateStudent(student);
//    }

}
