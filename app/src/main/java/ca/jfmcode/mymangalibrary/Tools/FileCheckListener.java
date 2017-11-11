package ca.jfmcode.mymangalibrary.Tools;

/**
 * Created by ONi on 07/11/2017.
 */

public interface FileCheckListener {
    void sysFileFound();
    void allFilesFound();
    void noFiles();
    void missingMangaFile();
    void error(String message);
}
