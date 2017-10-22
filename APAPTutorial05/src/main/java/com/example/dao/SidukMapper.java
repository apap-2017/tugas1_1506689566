package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.model.KelurahanModel;

@Mapper
public interface SidukMapper
{
	
	//nomor 1
	@Select ("select * from penduduk where nik=#{nik}")
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	
	@Select("select klg.alamat, klg.rt, klg.rw, kel.nama_kelurahan, kec.nama_kecamatan, kota.nama_kota "
			+ "from (select * from kelurahan) as kel JOIN "
			+ "(select * from kecamatan) as kec "
			+ "ON kel.id_kecamatan=kec.id JOIN"
			+ "(select * from kota) as kota "
			+ "on kec.id_kota=kota.id join"
			+ "(select alamat, rt, rw, id_kelurahan from keluarga where id=#{id_keluarga}) as klg "
			+ "on klg.id_kelurahan=kel.id"
			) 
	KeluargaModel alamatLengkap (@Param("id_keluarga") int id_keluarga);
	
	//nomor 2
	
	@Select ("select * from keluarga where nomor_kk=#{nomor_kk}")
	KeluargaModel selectKeluarga (@Param("nomor_kk") String nomor_kk);
	
	@Select ("select * "
			+ "from penduduk join keluarga "
			+ "on penduduk.id_keluarga=keluarga.id "
			+ "where keluarga.id=#{id_keluarga}")
	List<PendudukModel> selectAnggotaKeluarga(@Param("id_keluarga") int id_keluarga);
	
	@Select("select kel.nama_kelurahan, kel.kode_kelurahan, kec.nama_kecamatan, kec.kode_kecamatan, kota.kode_kota, kota.nama_kota "
			+ "from (select * from kelurahan) as kel JOIN "
			+ "(select * from kecamatan) as kec "
			+ "ON kel.id_kecamatan=kec.id JOIN"
			+ "(select * from kota) as kota "
			+ "on kec.id_kota=kota.id join"
			+ "(select id_kelurahan from keluarga where nomor_kk=#{nomor_kk}) as klg "
			+ "on klg.id_kelurahan=kel.id"
			) 
	KeluargaModel kelKecKot(@Param("nomor_kk") String nomor_kk);
	
	@Select("select kel.nama_kelurahan, kel.kode_kelurahan, kec.nama_kecamatan, kec.kode_kecamatan, kota.kode_kota, kota.nama_kota "
			+ "from (select * from kelurahan where id = #{id_kelurahan}) as kel JOIN "
			+ "(select * from kecamatan) as kec "
			+ "ON kel.id_kecamatan=kec.id JOIN"
			+ "(select * from kota) as kota "
			+ "on kec.id_kota=kota.id "
			) 
	KelurahanModel getKelKecKot(@Param("id_kelurahan") int id_kelurahan);
	
	
	//nomor 3
	
	@Insert("INSERT INTO penduduk (nik, nama, id_keluarga, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, golongan_darah, agama, status_perkawinan, pekerjaan, is_wafat, status_dalam_keluarga) "
			+ "VALUES ('${nik}', '${nama}', '${id_keluarga}', '${tempat_lahir}', '${tanggal_lahir}', '${jenis_kelamin}', '${is_wni}', '${golongan_darah}', '${agama}', '${status_perkawinan}', '${pekerjaan}', '${is_wafat}', '${status_dalam_keluarga}' )")
    void addPenduduk (PendudukModel penduduk);
	
	
	@Select ("SELECT nik from penduduk where nik LIKE CONCAT(#{nikBeforeNoUrut},'%') ORDER BY nik ASC LIMIT 1")
	String getNikWithSameData(@Param("nikBeforeNoUrut") String nikBeforeNoUrut);
	
	//nomor 4
	
	@Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) "
			+ "VALUES ('${id}', '${nomor_kk}', '${alamat}', '${rt}', '${rw}', '${id_kelurahan}', 0)")
    void addKeluarga (KeluargaModel penduduk);
	
	@Select ("SELECT id from keluarga ORDER BY id ASC LIMIT 1")
	int maxIdKeluarga();
	
	@Select ("SELECT nomor_kk from keluarga where nomor_kk LIKE CONCAT(#{nkkBeforeNoUrut},'%') ORDER BY nomor_kk ASC LIMIT 1")
	String getNkkWithSameData(@Param("nkkBeforeNoUrut") String nkkBeforeNoUrut);
	
	@Select ("SELECT id from kelurahan where nama_kelurahan=#{nama_kelurahan}")
	int getIdKelurahan(@Param("nama_kelurahan") String nama_kelurahan);
	
	
	
	@Select ("select * from keluarga where id=#{id}")
	KeluargaModel selectKeluargaById (@Param("id") Integer id);
	
	//nomor 5
	
	@Update("update penduduk set nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "id_keluarga=#{id_keluarga}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} "
			+ "where id = #{id}")
    void ubahPenduduk (PendudukModel penduduk);
	
	//nomor 6
	
	@Update("update keluarga set alamat = #{alamat}, nomor_kk = #{nomor_kk}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "where id = #{id}")
    void ubahKeluarga (KeluargaModel keluarga);
	
	//nomor 7
	
	@Update("UPDATE penduduk SET is_wafat = 1 WHERE nik = #{nik}")
	   void ubahIsWafat(@Param("nik") String nik);
	 
	@Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id_keluarga}")
	   void kk_tidak_berlaku(@Param("id_keluarga") int id_keluarga);
	 
	
	
	//nomor 8
	@Select ("SELECT * from kota")
	List<KotaModel> selectAllKota();
	
	@Select ("SELECT * from kelurahan")
	List<KelurahanModel> kelurahan_list(@Param("nama_kecamatan") String nama_kecamatan);
	
	@Select ("SELECT * from kecamatan")
	List<KecamatanModel> kecamatan_list();
	
//	 @Select("select * from penduduk JOIN "
//	    		+ "(select id from keluarga where id_kelurahan = #{id_kelurahan}) AS keluarga "
//	    		+ "ON keluarga.id = penduduk.id_keluarga")
//	 List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);
	 
	 @Select ("select p.nik, p.nama, p.tanggal_lahir from penduduk p, keluarga klg, kelurahan kl where p.id_keluarga=klg.id AND klg.id_kelurahan=kl.id AND kl.id=#{id_kelurahan}")
	 List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);
	
	 @Select("select * from kecamatan, kota where kecamatan.id_kota=kota.id and kota.id=#{id_kota}")
		 List<KecamatanModel> selectKecamatanByIdKota(@Param("id_kota")int id_kota);
	 
	 @Select("select * from kelurahan, kecamatan where kelurahan.id_kecamatan=kecamatan.id and kecamatan.id=#{id_kecamatan}")
	 List<KelurahanModel> selectKelurahanByIdKecamatan(@Param("id_kecamatan")int id_kecamatan);
	 
	 
	
	 //bonus cari tertua
	 
	 @Select ("SELECT p.nama as nama, p.nik as nik, p.tanggal_lahir as tanggal_lahir from penduduk p, keluarga klg, kelurahan kl where p.id_keluarga=klg.id AND klg.id_kelurahan=kl.id and kl.id=#{id_kelurahan} ORDER BY tanggal_lahir ASC LIMIT 1")
	PendudukModel selectTua(@Param("id_kelurahan")int id_kelurahan);
	 
//bonus cari termuda
	 
	 @Select ("SELECT p.nama as nama, p.nik as nik, p.tanggal_lahir as tanggal_lahir from penduduk p, keluarga klg, kelurahan kl where p.id_keluarga=klg.id AND klg.id_kelurahan=kl.id and kl.id=#{id_kelurahan} ORDER BY tanggal_lahir DESC LIMIT 1")
	PendudukModel selectMuda(@Param("id_kelurahan")int id_kelurahan);
	 
	
	
	
	
	   
}
