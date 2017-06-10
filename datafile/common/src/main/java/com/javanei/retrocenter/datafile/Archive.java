package com.javanei.retrocenter.datafile;

import com.javanei.retrocenter.logiqx.LogiqxArchive;
import java.io.Serializable;
import java.util.Objects;

public class Archive implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * logiqx.archive.name
     */
    private String name;

    public Archive() {
    }

    public Archive(String name) {
        this.name = name;
    }

    public static Archive fromLogiqx(LogiqxArchive p) {
        return new Archive(p.getName());
    }

    public LogiqxArchive toLogiqx() {
        return new LogiqxArchive(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Archive archive = (Archive) o;
        return Objects.equals(name, archive.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t<archive name=\"").append(this.name).append(" />\n");
        return sb.toString();
    }
}
