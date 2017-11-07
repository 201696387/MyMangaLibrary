package ca.jfmcode.mymangalibrary.Tools;

/**
 * Created by ONi on 07/11/2017.
 */

public interface FileCheckListener {
    void filesFound();
    void noFiles();
    void error(String message);
}
