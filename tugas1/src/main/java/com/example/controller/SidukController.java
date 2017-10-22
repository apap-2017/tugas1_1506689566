package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.model.KelurahanModel;
import com.example.service.SidukService;

@Controller
public class SidukController
{
    @Autowired
    SidukService sidukDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }
    
    
    
    @RequestMapping("/penduduk-page")
    public String pendudukPage ()
    {
        return "penduduk-page";
    }
    
    @RequestMapping("/kk-tidak-valid")
    public String kkTidakValid (Model model)
    {
    	List<KeluargaModel> kk_not_valid = sidukDAO.kkTidakValid();
    	model.addAttribute("kk_not_valid", kk_not_valid);
    	for (int i = 0; i < kk_not_valid.size(); i++) {
    		KeluargaModel klg = kk_not_valid.get(i);
    		model.addAttribute("keluarga", klg);
    	}
        return "keluarga-tidak-valid";
    }
    
    @RequestMapping(value = "/kepala-keluarga")
	 public String cariKepalaKeluarga (Model model,
			 @RequestParam(value = "kt", defaultValue = "0") int id_kota,
	         @RequestParam(value = "kc", defaultValue = "0") int id_kecamatan,
	         @RequestParam(value = "kl", defaultValue = "0") int id_kelurahan){
   	
   	List<KotaModel> select_kota = sidukDAO.selectAllKota();
   	model.addAttribute("kota_list", select_kota);
   	
   	if (id_kota != 0) {
   		model.addAttribute("id_kota", id_kota);
   		for (int i = 0; i < select_kota.size() ; i++) {
   			if(id_kota == select_kota.get(i).getId()) {
   				String nama_kota = select_kota.get(i).getNama_kota();
   				model.addAttribute("nama_kota", nama_kota);
   			}
   		}
   		
   		List<KecamatanModel> select_kecamatan = sidukDAO.selectKecamatanByIdKota(id_kota);
   		model.addAttribute("kecamatan_list", select_kecamatan);
   		for (int i = 0; i < select_kecamatan.size() ; i++) {
   			if(id_kecamatan == select_kecamatan.get(i).getId()) {
   				String nama_kecamatan = select_kecamatan.get(i).getNama_kecamatan();
   				model.addAttribute("nama_kecamatan", nama_kecamatan);
   			}
   		}
   		
   		
   	}
   	
   	if (id_kecamatan != 0) {
   		model.addAttribute("id_kecamatan", id_kecamatan);
   		List<KelurahanModel> select_kelurahan = sidukDAO.selectKelurahanByIdKecamatan(id_kecamatan);
   		model.addAttribute("kelurahan_list", select_kelurahan);
   		for (int i = 0; i < select_kelurahan.size() ; i++) {
   			if(id_kelurahan == select_kelurahan.get(i).getId()) {
   				String nama_kelurahan = select_kelurahan.get(i).getNama_kelurahan();
   				model.addAttribute("nama_kelurahan", nama_kelurahan);
   			}
   		}
   		List<PendudukModel> kepala_keluarga = sidukDAO.selectKepalaKeluarga();
   		model.addAttribute("kepala_keluarga", kepala_keluarga);
   		for (int i = 0; i < kepala_keluarga.size(); i++) {
   			PendudukModel kepala = kepala_keluarga.get(i);
   			model.addAttribute("kepala", kepala);
   		}
   	}
   	
   	
   	
    return "kepala-keluarga";
		
   	
   }

    

    
    @RequestMapping("/keluarga-page")
    public String keluargaPage ()
    {
        return "keluarga-page";
    }
    
    
	@RequestMapping("/penduduk")
    public String viewPenduduk (Model model,
            @RequestParam(value = "nik", required = false) String nik)
    {
		if (nik == null) {
			model.addAttribute("nik", nik);
			return "validasi-form/nik-kosong";
		} else {
			PendudukModel pend = sidukDAO.selectPenduduk (nik);
	        
		      
	    	   if (pend != null) {
	        	   KeluargaModel alamat = sidukDAO.alamatLengkap(pend.getId_keluarga());
	        	   model.addAttribute ("penduduk", pend);
	               model.addAttribute("keluarga", alamat);
	               model.addAttribute("nik", nik);
	               return "view-data-penduduk";
	           } else {
	        	   model.addAttribute("nik", nik);
	               return "not-found";
	           } 
		}
        
    }
	
	@RequestMapping("/keluarga")
    public String viewKeluarga (Model model,
            @RequestParam(value = "nomor_kk", required = false) String nomor_kk)
    {
		if(nomor_kk == null) {
			return "validasi-form/nkk-kosong";
		} else {
			KeluargaModel keluarga = sidukDAO.selectKeluarga(nomor_kk);
			
			if(keluarga != null) {
				List<PendudukModel> anggota = sidukDAO.selectAnggotaKeluarga(keluarga.getId());
		    	int mati = 0;
		    	for (int i = 0; i < anggota.size(); i++) {
		    		if (anggota.get(i).getIs_wafat() == 1) {
		    			mati++;
		    			
		    			
		    		}
		    	}
		    	
		    	if (mati == anggota.size()) {
					sidukDAO.kk_tidak_berlaku(keluarga.getId());
				}
			       
				KeluargaModel alamat = sidukDAO.kelKecKot(nomor_kk);
		            model.addAttribute ("keluarga", keluarga);
		            model.addAttribute("alamat", alamat);
		            model.addAttribute("anggota", anggota);
		            return "view-data-keluarga";
			} else {
				model.addAttribute("nomor_kk", nomor_kk);
				return "not-found";
			}
		}
		
		
        
    }
	
	
	@RequestMapping(value = "/penduduk/tambah")
    public String addPenduduk (Model model, @ModelAttribute PendudukModel penduduk)
    {
		model.addAttribute("penduduk", new PendudukModel());
		return "form-add-penduduk";      
    }
	
	@RequestMapping(value = "/penduduk/tambah/submit", method = RequestMethod.POST)
    public String addPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk)
    {
		if (penduduk.getId_keluarga() == 0 || penduduk.getTanggal_lahir() == null) {
			model.addAttribute("id_keluarga", penduduk.getId_keluarga());
			model.addAttribute("tanggal_lahir", penduduk.getTanggal_lahir());
			return "validasi-form/gagal";
		} else {
			KeluargaModel keluarga = sidukDAO.selectKeluargaById(penduduk.getId_keluarga());
	    	
	    	if (keluarga != null) {
	    		KeluargaModel alamat = sidukDAO.kelKecKot(keluarga.getNomor_kk());
	    		
	    		model.addAttribute("keluarga", keluarga);
	    		model.addAttribute("alamat", alamat);
	        	String prov_kota = alamat.getKode_kota();
	        	String kec = alamat.getKode_kecamatan().substring(4, 6);
	        	String tanggal_lahir = penduduk.getTanggal_lahir();
	        	
	        	String tanggal = tanggal_lahir.substring(8, 10);
	        	String bulan = tanggal_lahir.substring(5, 7);
	        	String tahun = tanggal_lahir.substring(2, 4);
	        	
	        	int tgl = 0;
	        	if (penduduk.getJenis_kelamin() == 1) {
	        		tgl = 40 + Integer.parseInt(tanggal);
	        		tanggal = Integer.toString(tgl);
	        	}
	        	
	        	String nikBeforeNoUrut = prov_kota + kec + tanggal + bulan + tahun;
	        	
	        	String callNik = sidukDAO.getNikWithSameData(nikBeforeNoUrut);
	        	
	        	String nikAkhir = "";
	        	Long nik= 0000000000000000L;
	        	
	        	if (callNik == null) {
	        		nik = Long.parseLong(nikBeforeNoUrut + "0001");
	            	nikAkhir = Long.toString(nik);
	        		
	        	} else {
	        		nik = Long.parseLong(callNik) + 1;
	        		nikAkhir = Long.toString(nik);
	        	}
	        	
	        	penduduk.setNik(nikAkhir);
	        	sidukDAO.addPenduduk (penduduk);
	        	model.addAttribute("penduduk", penduduk);
	        	
	    		return "success-add-penduduk";
	    	} else {
	    		return "not-found";
	    	}
		}
			
	    	
		
		
            
      
    }
	
	@RequestMapping(value = "/keluarga/tambah")
    public String addKeluarga (Model model, @ModelAttribute KeluargaModel keluarga)
    {
		model.addAttribute("keluarga", new KeluargaModel());
		
			return "form-add-keluarga";
    }
	
	@RequestMapping(value = "/keluarga/tambah/submit", method = RequestMethod.POST)
    public String addKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga)
    {
		int id = sidukDAO.getIdKelurahan(keluarga.getNama_kelurahan());
		int idMax = sidukDAO.maxIdKeluarga();
		keluarga.setId(idMax + 1);
		keluarga.setId_kelurahan(id);
		
		KelurahanModel kelurahan = sidukDAO.getKelKecKot(id);
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String date2 = dateFormat.format(date);
		String tanggal = date2.toString().substring(0, 4) + date2.toString().substring(6, 8);
		
		String prov_kota = kelurahan.getKode_kota();
		String kec = kelurahan.getKode_kecamatan().substring(4,6);
		
		model.addAttribute("keluarga", keluarga);
		
    	String nkkBeforeNoUrut = prov_kota + kec + tanggal ;
    	
    	String callNkk = sidukDAO.getNkkWithSameData(nkkBeforeNoUrut);
    	
    	String nkkAkhir = "";
    	Long nkk= 0000000000000000L;
    	
    	if (callNkk == null) {
    		nkk = Long.parseLong(nkkBeforeNoUrut + "0001");
        	nkkAkhir = Long.toString(nkk);
    		
    	} else {
    		nkk = Long.parseLong(callNkk) + 1;
    		nkkAkhir = Long.toString(nkk);
    	}
    	
    	keluarga.setNomor_kk(nkkAkhir);
		sidukDAO.addKeluarga(keluarga);
      	
   		return "success-add-keluarga";
       
    }
	
	@RequestMapping(value = "/penduduk/ubah/{nik}")
    public String ubahPenduduk (Model model, @PathVariable(value = "nik") String nik)
    {
    	PendudukModel penduduk = sidukDAO.selectPenduduk (nik);
    	
    	if (penduduk != null) {
    		model.addAttribute ("penduduk", penduduk);
            return "form-update-penduduk";
    	} else {
    		model.addAttribute("nik", nik);
    		return "not-found";
    	} 
      
    }
    
    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String ubahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk, @PathVariable(value = "nik") String nik )
    {
    		
    		PendudukModel penduduk_lama = sidukDAO.selectPenduduk(nik);
    		
    		penduduk.setId(penduduk_lama.getId());
    		model.addAttribute("nik", penduduk.getNik());
    		
    		KeluargaModel keluarga = sidukDAO.selectKeluargaById(penduduk.getId_keluarga());
    		
    		
    	if ((!penduduk_lama.getTanggal_lahir().equals(penduduk.getTanggal_lahir())) || (!penduduk_lama.getId_keluarga().equals(penduduk.getId_keluarga()))) {
    		KeluargaModel alamat = sidukDAO.kelKecKot(keluarga.getNomor_kk());
    		
    		model.addAttribute("keluarga", keluarga);
    		model.addAttribute("alamat", alamat);
        	String prov_kota = alamat.getKode_kota();
        	String kec = alamat.getKode_kecamatan().substring(4, 6);
        	String tanggal_lahir = penduduk.getTanggal_lahir();
        	
        	String tanggal = tanggal_lahir.substring(8, 10);
        	String bulan = tanggal_lahir.substring(5, 7);
        	String tahun = tanggal_lahir.substring(2, 4);
        	
        	int tgl = 0;
        	if (penduduk.getJenis_kelamin() == 1) {
        		tgl = 40 + Integer.parseInt(tanggal);
        		tanggal = Integer.toString(tgl);
        	}
        	
        	String nikBeforeNoUrut = prov_kota + kec + tanggal + bulan + tahun;
        	
        	String callNik = sidukDAO.getNikWithSameData(nikBeforeNoUrut);
        	
        	String nik_baru = "";
        	Long nikk1= 0000000000000000L;
        	
        	if (callNik == null) {
        		nikk1 = Long.parseLong(nikBeforeNoUrut + "0001");
            	nik_baru = Long.toString(nikk1);
        		
        	} else {
        		nikk1 = Long.parseLong(callNik) + 1;
        		nik_baru = Long.toString(nikk1);
        	}
        		
        	
        		penduduk.setNik(nik_baru);
        
        	
        	sidukDAO.ubahPenduduk(penduduk);
        	model.addAttribute("penduduk", penduduk);
        	model.addAttribute("keluarga", keluarga);
    		return "success-update-penduduk";
 	} else {
 		sidukDAO.ubahPenduduk(penduduk);
 		model.addAttribute("penduduk", penduduk);
    	model.addAttribute("keluarga", keluarga);
		return "success-update-penduduk";
    }
    	
      
    }
    
    @RequestMapping(value = "/keluarga/ubah/{nomor_kk}")
    public String ubahKeluarga (Model model, @PathVariable(value = "nomor_kk") String nomor_kk)
    {
		
    	KeluargaModel keluarga = sidukDAO.selectKeluarga(nomor_kk);
    	if (keluarga != null) {
    		KelurahanModel alamat = sidukDAO.getKelKecKot(keluarga.getId_kelurahan());
    		keluarga.setId_kelurahan(alamat.getId());
    		keluarga.setNama_kecamatan(alamat.getNama_kecamatan());
    		keluarga.setNama_kelurahan(alamat.getNama_kelurahan());
    		keluarga.setNama_kota(alamat.getNama_kota());
    		model.addAttribute("nama_kecamatan", keluarga.getNama_kecamatan());
    		model.addAttribute("nama_kelurahan", keluarga.getNama_kelurahan());
    		model.addAttribute("nama_kota", keluarga.getNama_kota());
    		
    		model.addAttribute ("keluarga", keluarga);
            return "form-update-keluarga";
    	} else {
    		model.addAttribute("nomor_kk", nomor_kk);
    		return "not-found";
    	}  
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nomor_kk}", method = RequestMethod.POST)
    public String ubahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga, 
    		@PathVariable(value = "nomor_kk") String nomor_kk)
    {
			int id_kelurahan = sidukDAO.getIdKelurahan(keluarga.getNama_kelurahan());
			KeluargaModel keluarga_lama = sidukDAO.selectKeluarga(nomor_kk);
			model.addAttribute("nomor_kk", keluarga_lama.getNomor_kk());
			keluarga.setId(keluarga_lama.getId());
			keluarga.setId_kelurahan(id_kelurahan);
			
			if(keluarga_lama.getId_kelurahan() != (keluarga.getId_kelurahan()) || 
				keluarga_lama.getId_kecamatan() != (keluarga.getId_kecamatan())	||
				keluarga_lama.getId_kota() != keluarga.getId_kota()
					) {
				KelurahanModel kelurahan = sidukDAO.getKelKecKot(keluarga.getId_kelurahan());
					
				DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
				Date date = new Date();
				String date2 = dateFormat.format(date);
				String tanggal = date2.toString().substring(0, 4) + date2.toString().substring(6, 8);
				
				String prov_kota = kelurahan.getKode_kota();
				String kec = kelurahan.getKode_kecamatan().substring(4,6);
				
				model.addAttribute("keluarga", keluarga);
				
		    	String nkkBeforeNoUrut = prov_kota + kec + tanggal ;
		    	
		    	String callNkk = sidukDAO.getNkkWithSameData(nkkBeforeNoUrut);
		    	
		    	String nkkAkhir = "";
		    	Long nkk= 0000000000000000L;
		    	
		    	if (callNkk == null) {
		    		nkk = Long.parseLong(nkkBeforeNoUrut + "0001");
		        	nkkAkhir = Long.toString(nkk);
		    		
		    	} else {
		    		nkk = Long.parseLong(callNkk) + 1;
		    		nkkAkhir = Long.toString(nkk);
		    	}
		    	
		    	keluarga.setNomor_kk(nkkAkhir);
				
			} 
				
			sidukDAO.ubahKeluarga(keluarga);
			model.addAttribute("keluarga", keluarga);
	   		return "success-update-keluarga";
    }
	
    
    
    @RequestMapping(value="penduduk/mati", method = RequestMethod.POST)
    public String ubahIsWafat(Model model, 
    		@RequestParam(value="nik", required=true) String nik) {
    	
    	PendudukModel penduduk = sidukDAO.selectPenduduk(nik);
    	sidukDAO.ubahIsWafat(penduduk.getNik());
    	
    	
    	sidukDAO.ubahIsWafat(nik);
    	model.addAttribute("nik_kematian", nik);
    	return "success-wafat";
    }
    
    
    
    @RequestMapping(value = "/penduduk/cari")
	 public String cariPendudukKota (Model model,
			 @RequestParam(value = "kt", defaultValue = "0") int id_kota,
	         @RequestParam(value = "kc", defaultValue = "0") int id_kecamatan,
	         @RequestParam(value = "kl", defaultValue = "0") int id_kelurahan){
    	
    	List<KotaModel> select_kota = sidukDAO.selectAllKota();
    	model.addAttribute("kota_list", select_kota);
    	
    	if (id_kota != 0) {
    		model.addAttribute("id_kota", id_kota);
    		for (int i = 0; i < select_kota.size() ; i++) {
    			if(id_kota == select_kota.get(i).getId()) {
    				String nama_kota = select_kota.get(i).getNama_kota();
    				model.addAttribute("nama_kota", nama_kota);
    			}
    		}
    		
    		List<KecamatanModel> select_kecamatan = sidukDAO.selectKecamatanByIdKota(id_kota);
    		model.addAttribute("kecamatan_list", select_kecamatan);
    		for (int i = 0; i < select_kecamatan.size() ; i++) {
    			if(id_kecamatan == select_kecamatan.get(i).getId()) {
    				String nama_kecamatan = select_kecamatan.get(i).getNama_kecamatan();
    				model.addAttribute("nama_kecamatan", nama_kecamatan);
    			}
    		}
    		
    		
    	}
    	
    	if (id_kecamatan != 0) {
    		model.addAttribute("id_kecamatan", id_kecamatan);
    		List<KelurahanModel> select_kelurahan = sidukDAO.selectKelurahanByIdKecamatan(id_kecamatan);
    		model.addAttribute("kelurahan_list", select_kelurahan);
    		for (int i = 0; i < select_kelurahan.size() ; i++) {
    			if(id_kelurahan == select_kelurahan.get(i).getId()) {
    				String nama_kelurahan = select_kelurahan.get(i).getNama_kelurahan();
    				model.addAttribute("nama_kelurahan", nama_kelurahan);
    			}
    		}
    	}
    	
    	
			List<PendudukModel> anggota = sidukDAO.selectPendudukByIdKelurahan(id_kelurahan);
    		
			PendudukModel penduduk_muda = sidukDAO.selectMuda(id_kelurahan);
			PendudukModel penduduk_tua = sidukDAO.selectTua(id_kelurahan);
			model.addAttribute("anggota", anggota);
    		model.addAttribute("penduduk_muda", penduduk_muda);
    		model.addAttribute("penduduk_tua", penduduk_tua);
    		return "cari-penduduk";
		
			
		
    	
    }

    
    @RequestMapping(value = "/penduduk/cari-penduduk")
	 public String cariPendudukTuaMuda (Model model,
			 @RequestParam(value = "kt", defaultValue = "0") int id_kota,
	         @RequestParam(value = "kc", defaultValue = "0") int id_kecamatan){
   	
   	List<KotaModel> select_kota = sidukDAO.selectAllKota();
   	model.addAttribute("kota_list", select_kota);
   	
   	if (id_kota != 0) {
   		model.addAttribute("id_kota", id_kota);
   		for (int i = 0; i < select_kota.size() ; i++) {
   			if(id_kota == select_kota.get(i).getId()) {
   				String nama_kota = select_kota.get(i).getNama_kota();
   				model.addAttribute("nama_kota", nama_kota);
   			}
   		}
   		
   		List<KecamatanModel> select_kecamatan = sidukDAO.selectKecamatanByIdKota(id_kota);
   		model.addAttribute("kecamatan_list", select_kecamatan);
   		for (int i = 0; i < select_kecamatan.size() ; i++) {
   			if(id_kecamatan == select_kecamatan.get(i).getId()) {
   				String nama_kecamatan = select_kecamatan.get(i).getNama_kecamatan();
   				model.addAttribute("nama_kecamatan", nama_kecamatan);
   				
   				
   			}
   		}
   	}
   	if(id_kecamatan != 0) {
   		model.addAttribute("id_kecamatan", id_kecamatan);
   	}
   		PendudukModel tua_kc = sidukDAO.selectPendudukTuaByIdKecamatan(id_kecamatan);
		PendudukModel muda_kc = sidukDAO.selectPendudukMudaByIdKecamatan(id_kecamatan);
	
		model.addAttribute("muda_sekc", muda_kc);
		model.addAttribute("tua_sekc", tua_kc);
	 	
   	return "cari-penduduk-umur";
    }

}
