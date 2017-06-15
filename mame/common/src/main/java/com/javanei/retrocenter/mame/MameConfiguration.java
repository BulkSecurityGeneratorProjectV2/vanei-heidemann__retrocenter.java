package com.javanei.retrocenter.mame;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.javanei.retrocenter.common.util.StringUtil;

public class MameConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String tag;
    private Integer mask;

    private List<MameConfsetting> confsettings = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public void setMask(String mask) {
        this.mask = new Integer(mask);
    }

    public List<MameConfsetting> getConfsettings() {
        return confsettings;
    }

    public void setConfsettings(List<MameConfsetting> confsettings) {
        this.confsettings = confsettings;
    }

    public void addConfsetting(MameConfsetting confsetting) {
        this.confsettings.add(confsetting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MameConfiguration that = (MameConfiguration) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(mask, that.mask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tag, mask);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t<configuration");
        if (this.name != null) {
            sb.append(" name=\"").append(StringUtil.escapeXMLEntities(this.name)).append("\"");
        }
        if (this.tag != null) {
            sb.append(" tag=\"").append(this.tag).append("\"");
        }
        if (this.mask != null) {
            sb.append(" mask=\"").append(this.mask).append("\"");
        }
        sb.append(">").append(StringUtil.LINE_SEPARATOR);

        for (MameConfsetting confsetting : this.confsettings) {
            sb.append(confsetting.toString());
        }

        sb.append("\t\t</configuration>").append(StringUtil.LINE_SEPARATOR);
        return sb.toString();
    }
}