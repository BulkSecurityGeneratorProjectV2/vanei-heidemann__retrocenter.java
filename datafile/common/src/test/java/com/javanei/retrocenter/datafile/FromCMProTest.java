package com.javanei.retrocenter.datafile;

import com.javanei.retrocenter.clrmamepro.CMProDatafile;
import com.javanei.retrocenter.clrmamepro.CMProDisk;
import com.javanei.retrocenter.clrmamepro.CMProGame;
import com.javanei.retrocenter.clrmamepro.CMProHeader;
import com.javanei.retrocenter.clrmamepro.CMProRom;
import com.javanei.retrocenter.common.DatafileCategoryEnum;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FromCMProTest {
    private static CMProDatafile cmpro;
    private static Datafile datafile;

    @BeforeClass
    public static void initialize() throws Exception {
        cmpro = new CMProDatafile(new CMProHeader("name 01", "description 01", "no-intro",
                "1.00", "javanei", "javanei.com.br", "www.javanei.com.br",
                "split", "yes"));
        CMProGame game = new CMProGame("game name 01", "game description 01", "2017",
                "manufacturer 01", "cloneof 01", "romof 01");
        cmpro.addGame(game);

        CMProRom rom = new CMProRom("rom name 01", Long.valueOf(1l), "12345678", "1234567890123456789012345678901234567890",
                "12345678901234567890123456789012", "region 01", "verified");
        game.addRom(rom);

        CMProDisk disk = new CMProDisk("disk name 01", "1234567890123456789012345678901234567890",
                "12345678901234567890123456789012");
        game.addDisk(disk);

        game.addSample("sample 01");
        game.addSampleOf("sampleof 01");

        datafile = Datafile.fromCMPro(cmpro);
    }

    @Test
    public void testName() {
        Assert.assertEquals("Name", "name 01", datafile.getName());
    }

    @Test
    public void testVersion() {
        Assert.assertEquals("Version", "1.00", datafile.getVersion());
    }

    @Test
    public void testCategory() {
        Assert.assertEquals("Category", DatafileCategoryEnum.NoIntro.name(), datafile.getCategory());
    }

    @Test
    public void testGame() {
        Assert.assertEquals("Games", 1, datafile.getGames().size());

        Game game = datafile.getGames().iterator().next();
        Assert.assertEquals("Game : Name", "game name 01", game.getName());
        Assert.assertEquals("Game : cloneof", "cloneof 01", game.getCloneof());
        Assert.assertEquals("Game : description", "game description 01", game.getDescription());
        Assert.assertNull("Game : isbios", game.getIsbios());
        Assert.assertEquals("Game : manufacturer", "manufacturer 01", game.getManufacturer());
        Assert.assertEquals("Game : romof", "romof 01", game.getRomof());
        Assert.assertEquals("Game : sampleof", "sampleof 01", game.getSampleof());
        Assert.assertEquals("Game : year", "2017", game.getYear());
    }

    @Test
    public void testRom() {
        Game game = datafile.getGames().iterator().next();
        Iterator<GameFile> it = game.getFiles().iterator();
        GameFile gameFile = it.next();
        while (gameFile != null && !gameFile.getType().equals(GameFileTypeEnum.ROM.name()) && it.hasNext()) {
            gameFile = it.next();
        }

        Assert.assertNotNull("ROM", gameFile);
        Assert.assertEquals("Name", "rom name 01", gameFile.getName());
        Assert.assertEquals("Size", Long.valueOf(1l), gameFile.getSize());
        Assert.assertEquals("CRC", "12345678", gameFile.getCrc());
        Assert.assertEquals("SHA1", "1234567890123456789012345678901234567890", gameFile.getSha1());
        Assert.assertEquals("MD5", "12345678901234567890123456789012", gameFile.getMd5());
        Assert.assertEquals("Status", "verified", gameFile.getStatus());
        Assert.assertNull("Date", gameFile.getDate());
        Assert.assertNull("Merge", gameFile.getMerge());
        Assert.assertEquals("Region", "region 01", gameFile.getRegion());
    }

    @Test
    public void testDisk() {
        Game game = datafile.getGames().iterator().next();
        Iterator<GameFile> it = game.getFiles().iterator();
        GameFile gameFile = it.next();
        while (gameFile != null && !gameFile.getType().equals(GameFileTypeEnum.DISK.name()) && it.hasNext()) {
            gameFile = it.next();
        }

        Assert.assertNotNull("Disk", gameFile);
        Assert.assertEquals("Name", "disk name 01", gameFile.getName());
        Assert.assertNull("Size", gameFile.getSize());
        Assert.assertNull("CRC", gameFile.getCrc());
        Assert.assertEquals("SHA1", "1234567890123456789012345678901234567890", gameFile.getSha1());
        Assert.assertEquals("MD5", "12345678901234567890123456789012", gameFile.getMd5());
        Assert.assertNull("Merge", gameFile.getMerge());
        Assert.assertNull("Status", gameFile.getStatus());
        Assert.assertNull("Date", gameFile.getDate());
        Assert.assertNull("Region", gameFile.getRegion());
    }

    @Test
    public void testSample() {
        Game game = datafile.getGames().iterator().next();
        Iterator<GameFile> it = game.getFiles().iterator();
        GameFile gameFile = it.next();
        while (gameFile != null && !gameFile.getType().equals(GameFileTypeEnum.SAMPLE.name()) && it.hasNext()) {
            gameFile = it.next();
        }

        Assert.assertNotNull("Sample", gameFile);
        Assert.assertEquals("Name", "sample 01", gameFile.getName());
        Assert.assertNull("Size", gameFile.getSize());
        Assert.assertNull("CRC", gameFile.getCrc());
        Assert.assertNull("SHA1", gameFile.getSha1());
        Assert.assertNull("MD5", gameFile.getMd5());
        Assert.assertNull("Merge", gameFile.getMerge());
        Assert.assertNull("Status", gameFile.getStatus());
        Assert.assertNull("Date", gameFile.getDate());
        Assert.assertNull("Region", gameFile.getRegion());
    }
}
