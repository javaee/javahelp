<?xml version='1.0' encoding='ISO-8859-1' ?>
<!DOCTYPE helpset
  PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
         "http://java.sun.com/products/javahelp/helpset_2_0.dtd">

<helpset version="2.0">

  <!-- title -->
  <title>History of the Holidays</title>

  <!-- maps -->
  <maps>
     <homeID>hol_intro</homeID>
     <mapref location="Map.jhm"/>
  </maps>

  <!-- views -->
  <view mergetype="javax.help.UniteAppendMerge">
    <name>TOC</name>
    <label>Holidays</label>
    <type>javax.help.TOCView</type>
  </view>

  <view mergetype="javax.help.SortMerge">
    <name>Index</name>
    <label>Index</label>
    <type>javax.help.IndexView</type>
  </view>

  <view>
    <name>Search</name>
    <label>Search</label>
    <type>javax.help.SearchView</type>
  </view>

  <view>
    <name>Favorites</name>
    <label>Favorites</label>
    <type>javax.help.FavoritesView</type>
  </view>

  <!-- subhelpsets -->
  <subhelpset location="HolidayHistoryAmerican.hs"/>
  <subhelpset location="HolidayHistoryJewish.hs"/>
</helpset>
