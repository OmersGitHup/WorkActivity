package com.omerfarukisik.erdemlibelediyesiisprogramlama.model;

public class Isler {

     public String sorumluPersonel;
     public String mahalle;
     public String sikayetTel;
     public String bitisTarihi;
     public String sikayetTC;

     public Isler(String sorumluPersonel, String mahalle, String sikayetTel, String bitisTarihi ,String sikayetTC) {
          this.sorumluPersonel = sorumluPersonel;
          this.mahalle = mahalle;
          this.sikayetTel = sikayetTel;
          this.bitisTarihi = bitisTarihi;
          this.sikayetTC = sikayetTC;
     }
}
