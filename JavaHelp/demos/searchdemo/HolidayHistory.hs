<?xml version='1.0' encoding='ISO-8859-1' ?>
<!DOCTYPE helpset
  PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN"
         "http://java.sun.com/products/javahelp/helpset_1_0.dtd">

<helpset version="1.0">
  <!-- title -->
  <title>History of the Holidays</title>

  <!-- maps -->
  <maps>
     <homeID>intro</homeID>
     <mapref location="Map.jhm"/>
  </maps>

  <!-- views -->
  <view>
    <name>TOC</name>
    <label>Holidays</label>
    <type>javax.help.TOCView</type>
    <data>HolidayTOC.xml</data>
  </view>

  <view>
    <name>TOC2</name>
    <label>Jewish Holidays</label>
    <type>javax.help.TOCView</type>
    <data>HolidayTOC2.xml</data>
  </view>

  <view>
    <name>Index</name>
    <label>Index</label>
    <type>javax.help.IndexView</type>
    <data>HolidayIndex.xml</data>
  </view>

  <view>
    <name>Search</name>
    <label>Alternate Search</label>
    <type>javax.help.SearchView</type>
    <data engine="sunw.demo.searchdemo.ClientSearchEngine">
      JavaHelpIndex
    </data>
  </view>
</helpset>
