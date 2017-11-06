package ca.jfmcode.mymangalibrary.System;

/**
 * Created by ONi on 05/11/2017.
 */

public class Manga {
    //region private variables
    private int id;
    private String title;
    private String english;
    private String synonyms;
    private int episodes;
    private String type;
    private String status;
    private String start_date;
    private String end_date;
    private String synopsis;
    private String image;
    //endregion
}

/* MYANIMELIST API INFO

Search Methods

    Anime/Manga Search

        Allows authenticating user to search anime/manga titles.

        URL:
        https://myanimelist.net /api/anime|manga/search.xml?q=full+metal

        Formats:
        xml

        HTTP Method(s):
        GET

        Requires Authentication:
        true

        Parameters:

            q. Required. URL encoded string of the anime/manga to search for.
                Example: https://myanimelist.net /api/anime/search.xml?q=bleach

        Response:
        Success: 200 status code, XML data for anime/manga.
        Failure: 204 status code (no content).

        Example Response:

        <?xml version="1.0" encoding="utf-8"?>
        <anime>
          <entry>
            <id>2889</id>
            <title>Bleach - The DiamondDust Rebellion</title>
            <english>Bleach: Diamond Dust Rebellion</english>
            <synonyms>Bleach: The Diamond Dust Rebellion - M&Aring;
            Bleach - The DiamondDust Rebellion - Mou Hitotsu no Hyourinmaru</synonyms>
            <episodes>1</episodes>
            <type>Movie</type>
            <status>Finished Airing</status>
            <start_date>2007-12-22</start_date>
            <end_date>2007-12-22</end_date>
            <synopsis>A valuable artifact known as &amp;quot;King's Seal&amp;quot; is stolen
            by a mysterious group of people during transport in Soul Society. Hitsugaya Toushiro,
            the 10th division captain of Gotei 13, who is assigned to transport the seal fights the
            leader of the group and shortly after goes missing. After the incident, Seireitei declares
            Hitsugaya a traitor and orders the capture and execution of Hitsugaya. Kurosaki Ichigo
            refuses to believe this, and along with Matsumoto Rangiku, Kuchiki Rukia and Abarai Renji
            swear to uncover the real mastermind of the stolen seal, find Hitsugaya and clear his name.
            Meanwhile, a rogue Hitsugaya searches for the perpetrators and uncovers a
            dark secret regarding a long dead shinigami. (from ANN)</synopsis>
            <image>https://myanimelist.cdn-dena.com/images/anime/6/4052.jpg</image>
          </entry>
        </anime>



        Usage Examples:

            CURL:
            curl -u user:password -d https://myanimelist.net/api/anime/search.xml?q=naruto

//*/