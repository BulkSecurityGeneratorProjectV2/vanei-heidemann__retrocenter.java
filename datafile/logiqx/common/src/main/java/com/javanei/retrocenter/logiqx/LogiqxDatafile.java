package com.javanei.retrocenter.logiqx;

import com.javanei.retrocenter.common.DuplicatedItemException;
import com.javanei.retrocenter.common.util.ValidValuesUtil;
import com.javanei.retrocenter.datafile.Datafile;
import com.javanei.retrocenter.datafile.DatafileObject;
import com.javanei.retrocenter.datafile.Game;
import com.javanei.retrocenter.datafile.GameFile;
import com.javanei.retrocenter.datafile.GameFileTypeEnum;
import com.javanei.retrocenter.datafile.Release;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <!ELEMENT datafile (header?, game+)>
 */
public class LogiqxDatafile implements DatafileObject, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * <!ATTLIST datafile build CDATA #IMPLIED>
     */
    private String build;

    /**
     * <!ATTLIST datafile debug (yes|no) "no">
     */
    private String debug = "no";

    private LogiqxHeader header;

    private Set<LogiqxGame> games = new HashSet<>();

    public LogiqxDatafile() {
    }

    public LogiqxDatafile(String build) {
        this.build = build;
    }

    public LogiqxDatafile(String build, String debug) {
        this.build = build;
        this.debug = debug;
    }

    public LogiqxDatafile(LogiqxHeader header) {
        this.header = header;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = ValidValuesUtil.validateValue(debug, ValidValuesUtil.YES_NO);
    }

    public LogiqxHeader getHeader() {
        return header;
    }

    public void setHeader(LogiqxHeader header) {
        this.header = header;
    }

    public Set<LogiqxGame> getGames() {
        return games;
    }

    public void setGames(Set<LogiqxGame> games) {
        this.games = games;
    }

    public void addGame(LogiqxGame game) {
        if (this.games.contains(game)) {
            throw new DuplicatedItemException("game (" + game.getName() + ")");
        }
        this.games.add(game);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<!DOCTYPE datafile PUBLIC \"-//Logiqx//DTD ROM Management Datafile//EN\" \"http://www.logiqx.com/Dats/datafile.dtd\">\n\n")
                .append("<datafile");
        if (this.build != null)
            sb.append(" build=\"").append(this.build).append("\"");
        if (this.debug != null && !this.debug.equals("no"))
            sb.append(" debug=\"").append(this.debug).append("\"");
        sb.append(">\n");

        if (this.header != null) {
            sb.append(this.header.toString());
        }

        for (LogiqxGame game : this.games) {
            sb.append(game.toString());
        }

        sb.append("<datafile>\n");
        return sb.toString();
    }

    @Override
    public Datafile toDatafile() {
        Datafile datafile = this.header != null ? new Datafile(this.header.getName(), this.header.getCategory(),
                this.header.getVersion(), this.header.getDescription(), this.header.getAuthor(), this.header.getDate(),
                this.header.getEmail(), this.header.getHomepage(), this.header.getUrl(),
                this.header.getComment()) : new Datafile();
        for (LogiqxGame game : this.getGames()) {
            Game rGame = new Game(game.getName(), game.getIsbios(), game.getDescription(), game.getYear(),
                    game.getManufacturer(), game.getCloneof(), game.getRomof(), game.getSampleof(), game.getComment());
            for (LogiqxRelease p : game.getReleases()) {
                rGame.addRelease(new Release(p.getName(), p.getRegion(), p.getLanguage(), p.getDate(), p.getDefault()));
            }
            for (LogiqxRom p : game.getRoms()) {
                rGame.addFile(new GameFile(GameFileTypeEnum.ROM.name(), p.getName(), p.getSize(), p.getCrc(), p.getSha1(), p.getMd5(),
                        p.getStatus(), p.getDate(), p.getMerge(), null));
            }
            for (LogiqxDisk p : game.getDisks()) {
                rGame.addFile(new GameFile(GameFileTypeEnum.DISK.name(), p.getName(), null, null, p.getSha1(), p.getMd5(),
                        p.getStatus(), null, p.getMerge(), null));
            }
            for (LogiqxSample p : game.getSamples()) {
                rGame.addFile(new GameFile(GameFileTypeEnum.SAMPLE.name(), p.getName()));
            }
            datafile.addGame(rGame);
        }
        return datafile;
    }

    @Override
    public String toFile() {
        return toString();
    }
}