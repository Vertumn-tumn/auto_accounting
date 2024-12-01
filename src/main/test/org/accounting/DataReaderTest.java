package org.accounting;

import junit.framework.TestCase;

public class DataReaderTest extends TestCase {
    /*public void testReadFileContentOrNull() throws IOException {
        String pathToMonthReport = "C:/Users/admin/Documents/monthReport/m.202401.txt";
        String expectedContent = "item_name,is_expensive,quantity,sum_of_one\r\n" +
                "шарики,true,5000,5\r\n" +
                "автоматы с мороженым,true,12,15000\r\n" +
                "продажа мороженного,false,1000,120";
        DataReader dataReader = new DataReader();
        String actual = dataReader.readFileContentOrNull(pathToMonthReport);
        assertEquals(expectedContent, actual);
    }*/

    /*public void testListFilesFromDirectory() {
        String pathToMonthlyReports = "C:/Users/admin/Documents/monthReport";
        String expectedName = "m.202402.txt";
        DataReader dataReader = new DataReader();
        Set<String> strings = dataReader.listFilesFromDirectory(pathToMonthlyReports);
        assertTrue(strings.contains(expectedName));
    }*/
}