package com.javanei.retrocenter.mame.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MAME_ROM", indexes = {
        @Index(name = "MAME_ROM_0001", unique = false, columnList = "MACHINE_ID,NAME,BIOS,OFFSET")
})
//WARN: Parece haver duplicidades no xml
public class MameRomEntity implements Serializable, Comparable<MameRomEntity> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MAME_ROM_ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 80, nullable = false)
    private String name;

    @Column(name = "BIOS", length = 16, nullable = true)
    private String bios;

    @Column(name = "SIZE", nullable = true)
    private Long size;

    @Column(name = "CRC", length = 8, nullable = true)
    private String crc;

    @Column(name = "SHA1", length = 40, nullable = true)
    private String sha1;

    @Column(name = "MERGE", length = 48, nullable = true)
    private String merge;

    @Column(name = "REGION", length = 40, nullable = true)
    private String region;

    @Column(name = "OFFSET", length = 8, nullable = true)
    private String offset;

    @Column(name = "STATUS", length = 8, nullable = true)
    private String status;

    @Column(name = "OPTIONAL", length = 3, nullable = true)
    private String optional;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "MACHINE_ID")
    private MameMachineEntity machine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBios() {
        return bios;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getMerge() {
        return merge;
    }

    public void setMerge(String merge) {
        this.merge = merge;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public MameMachineEntity getMachine() {
        return machine;
    }

    public void setMachine(MameMachineEntity machine) {
        this.machine = machine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MameRomEntity entity = (MameRomEntity) o;
        return Objects.equals(name, entity.name) &&
                Objects.equals(bios, entity.bios) &&
                Objects.equals(offset, entity.offset) &&
                Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bios, offset, id);
    }

    @Override
    public int compareTo(MameRomEntity o) {
        if (this == o)
            return 0;

        int i = 0;
        if (this.id != null && o.id == null)
            i = 1;
        else if (this.id == null && o.id != null)
            i = -1;
        else if (this.id != null)
            i = this.id.compareTo(o.id);
        if (i == 0)
            i = this.name.compareTo(o.name);
        if (i == 0) {
            if (this.bios != null && o.bios == null)
                i = 1;
            else if (this.bios == null && o.bios != null)
                i = -1;
            else if (this.bios != null)
                i = this.bios.compareTo(o.bios);
        }
        if (i == 0) {
            if (this.offset != null && o.offset == null)
                i = 1;
            else if (this.offset == null && o.offset != null)
                i = -1;
            else if (this.offset != null)
                i = this.offset.compareTo(o.offset);
        }
        return i;
    }
}