<!DOCTYPE html>
<html>

<head>
  <title>Weather Search</title>
  <style>
  .search {
    padding: 1px 55px ;
    line-height: 0;
    border-radius: 10px;
    background-color: #2a8f28;
    color: #ffffff;
    margin: auto;
    margin-top: 35px;
    width: 600px;
    height: 250px;
    position: relative;
    top: 5%;
  }

  label {
    padding: 12px 12px 12px 0;
    display: inline-block;
  }

  .col-25 {
    float: left;
    width: 20%;
    margin-top: 6px;
  }

  input[type=text], select {
    width: 100%;
    border: 1px solid #ccc;
  }

  .col-75 {
    float: left;
    width: 20%;
    margin-top: 6px;
  }

  .row:after {
    content: "";
    display: table;
    clear: both;
  }

  .vl {
    border-left: 6px solid white;
    height: 125px;
    position: absolute;
    left: 55%;
    top: 20%;
  }

  .currloc{
    position: absolute;
    left: 70%;
    top: 23%;
  }

  input[type=submit], #clear {
    padding: 3px 6px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    position: absolute;
    left: 38%;
    bottom: 10%;
  }

  #clear {
    position: absolute;
    left: 46%;
  }

  input[type=text]:focus, select:focus {
    border: 2px solid red;
  }

  #errMsg{
    text-align: center;
    border: 3px solid Grey;
    max-width: 400px;
    margin: auto;
  }

  .card {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    width: 500px;
    border-radius: 20px;
    height: 325px;
    margin: auto;
    padding: 5px 20px;
    text-align: left;
    background-color: DeepSkyBlue;
    color: #ffffff;
  }

  #tz{
    margin: 0;
    line-height: 0.25;
  }

  #tmp{
    font-size: 100px;
    position:relative;
    margin:0px;
    line-height: 0.25;
  }

  #dg{
    margin-bottom: 60px;
    margin-top: 25px;
  }

  #csumm{
    line-height: 0.25;
    font-size: 45px;
    margin-bottom: 30px;
  }

  #tbl1, #tbl1 th, #tbl1 td {
    text-align: center;
  }

  .tooltip {
    position: relative;
    display: inline-block;
  }

  .tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: white;
    color: #000;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    top: 100%;
    left: 50%;
    margin-left: -60px;
    line-height: 1;
  }

  .tooltip:hover .tooltiptext {
    visibility: visible;
  }

  #tbl2 {
    border-collapse: collapse;
    background-color: SkyBlue;
    color: white;
  }

  #tbl2, #tbl2 th, #tbl2 td {
    border: 3px solid DodgerBlue;
    padding: 8px;
    text-align: center;
    font: bold 20px serif;
  }

  #tbl2 td img{
    padding: 0;
    margin: 0;
  }

  .dailyCard {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    width: 500px;
    border-radius: 20px;
    height: 425px;
    margin: auto;
    padding: 5px 20px;
    text-align: left;
    background-color: LightSkyBlue;
    color: #ffffff;
    line-height: 0.75;
  }

  #tmp1{
    font-size: 90px;
    position:relative;
    margin:0px;
    padding-top: 0;
    line-height:0;
  }

  #tbl3{
    line-height:1;
    padding-top: 15px;
    padding-left: 120px;
  }

  #key3 {
    font: bold 20px serif;
    text-align: right;
  }

  #value3 {
    font: bold 24px serif;
    text-align: left;
  }

  .collapsible {
    background-color: #fff;
    cursor: pointer;
    padding: 18px;
    width: 100%;
    border: none;
    text-align: center;
    outline: none;
    font-size: 15px;
  }

  .content {
    margin: 0 25%;
    display: none;
    overflow: hidden;
    text-align: center;
  }

  i {
    border: solid black;
    border-width: 0 3px 3px 0;
    display: inline-block;
    padding: 3px;
  }

  .down {
    transform: rotate(45deg);
    -webkit-transform: rotate(45deg);
  }

  .up {
    transform: rotate(-135deg);
    -webkit-transform: rotate(-135deg);
  }
  </style>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>

<body>
  <div class="search">
    <span>
      <h1 style="text-align: center; font-style: italic;"> Weather Search </h1>
      <form id="summFormDetails" name="myForm" action="main.php" method="post" onsubmit="return validateForm()">
        <div class="row">
          <div class="col-25">
            <label for="str">Street</label>
          </div>
          <div class="col-75">
            <input type="text" id="str" name="street" value="<?php echo $_POST["street"];?>">
          </div>
        </div>
        <div class="row">
          <div class="col-25">
            <label for="cty">City</label>
          </div>
          <div class="col-75">
            <input type="text" id="cty" name="city" value="<?php echo $_POST["city"];?>">
          </div>
        </div>
        <div class="row">
          <div class="col-25">
            <label for="st">State</label>
          </div>
          <div class="col-75">
            <select id="st" name="state">
              <option value="">State</option>
              <option value="" disabled>------------------</option>
              <option value="al" <?php if (isset($_POST["state"]) && ($_POST["state"]=="al")) echo "selected";?>>Alabama</option>
              <option value="ak" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ak")) echo "selected";?>>Alaska</option>
              <option value="az" <?php if (isset($_POST["state"]) && ($_POST["state"]=="az")) echo "selected";?>>Arizona</option>
              <option value="ar" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ar")) echo "selected";?>>Arkansas</option>
              <option value="ca" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ca")) echo "selected";?>>California</option>
              <option value="co" <?php if (isset($_POST["state"]) && ($_POST["state"]=="co")) echo "selected";?>>Colorado</option>
              <option value="ct" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ct")) echo "selected";?>>Connecticut</option>
              <option value="de" <?php if (isset($_POST["state"]) && ($_POST["state"]=="de")) echo "selected";?>>Delaware</option>
              <option value="dc" <?php if (isset($_POST["state"]) && ($_POST["state"]=="dc")) echo "selected";?>>District of Columbia</option>
              <option value="fl" <?php if (isset($_POST["state"]) && ($_POST["state"]=="fl")) echo "selected";?>>Florida</option>
              <option value="ga" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ga")) echo "selected";?>>Georgia</option>
              <option value="hi" <?php if (isset($_POST["state"]) && ($_POST["state"]=="hi")) echo "selected";?>>Hawaii</option>
              <option value="id" <?php if (isset($_POST["state"]) && ($_POST["state"]=="id")) echo "selected";?>>Idaho</option>
              <option value="il" <?php if (isset($_POST["state"]) && ($_POST["state"]=="il")) echo "selected";?>>Illinois</option>
              <option value="in" <?php if (isset($_POST["state"]) && ($_POST["state"]=="in")) echo "selected";?>>Indiana</option>
              <option value="ia" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ia")) echo "selected";?>>Iowa</option>
              <option value="ks" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ks")) echo "selected";?>>Kansas</option>
              <option value="ky" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ky")) echo "selected";?>>Kentucky</option>
              <option value="la" <?php if (isset($_POST["state"]) && ($_POST["state"]=="la")) echo "selected";?>>Louisiana</option>
              <option value="me" <?php if (isset($_POST["state"]) && ($_POST["state"]=="me")) echo "selected";?>>Maine</option>
              <option value="md" <?php if (isset($_POST["state"]) && ($_POST["state"]=="md")) echo "selected";?>>Maryland</option>
              <option value="ma" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ma")) echo "selected";?>>Massuchusetts</option>
              <option value="mi" <?php if (isset($_POST["state"]) && ($_POST["state"]=="mi")) echo "selected";?>>Michigan</option>
              <option value="mn" <?php if (isset($_POST["state"]) && ($_POST["state"]=="mn")) echo "selected";?>>Minnesota</option>
              <option value="ms" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ms")) echo "selected";?>>Mississippi</option>
              <option value="mo" <?php if (isset($_POST["state"]) && ($_POST["state"]=="mo")) echo "selected";?>>Missouri</option>
              <option value="mt" <?php if (isset($_POST["state"]) && ($_POST["state"]=="mt")) echo "selected";?>>Montana</option>
              <option value="ne" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ne")) echo "selected";?>>Nebraska</option>
              <option value="nv" <?php if (isset($_POST["state"]) && ($_POST["state"]=="nv")) echo "selected";?>>Nevada</option>
              <option value="nh" <?php if (isset($_POST["state"]) && ($_POST["state"]=="nh")) echo "selected";?>>New Hampshire</option>
              <option value="nj" <?php if (isset($_POST["state"]) && ($_POST["state"]=="nj")) echo "selected";?>>New Jersey</option>
              <option value="nm" <?php if (isset($_POST["state"]) && ($_POST["state"]=="nm")) echo "selected";?>>New Mexico</option>
              <option value="ny" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ny")) echo "selected";?>>New York</option>
              <option value="nc" <?php if (isset($_POST["state"]) && ($_POST["state"]=="nc")) echo "selected";?>>North Carolina</option>
              <option value="nd" <?php if (isset($_POST["state"]) && ($_POST["state"]=="nd")) echo "selected";?>>North Dakota</option>
              <option value="oh" <?php if (isset($_POST["state"]) && ($_POST["state"]=="oh")) echo "selected";?>>Ohio</option>
              <option value="ok" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ok")) echo "selected";?>>Oklahoma</option>
              <option value="or" <?php if (isset($_POST["state"]) && ($_POST["state"]=="or")) echo "selected";?>>Oregon</option>
              <option value="pa" <?php if (isset($_POST["state"]) && ($_POST["state"]=="pa")) echo "selected";?>>Pennslyvania</option>
              <option value="ri" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ri")) echo "selected";?>>Rhode Island</option>
              <option value="sc" <?php if (isset($_POST["state"]) && ($_POST["state"]=="sc")) echo "selected";?>>South Carolina</option>
              <option value="sd" <?php if (isset($_POST["state"]) && ($_POST["state"]=="sd")) echo "selected";?>>South Dakota</option>
              <option value="tn" <?php if (isset($_POST["state"]) && ($_POST["state"]=="tn")) echo "selected";?>>Tennessee</option>
              <option value="tx" <?php if (isset($_POST["state"]) && ($_POST["state"]=="tx")) echo "selected";?>>Texas</option>
              <option value="ut" <?php if (isset($_POST["state"]) && ($_POST["state"]=="ut")) echo "selected";?>>Utah</option>
              <option value="vt" <?php if (isset($_POST["state"]) && ($_POST["state"]=="vt")) echo "selected";?>>Vermont</option>
              <option value="va" <?php if (isset($_POST["state"]) && ($_POST["state"]=="va")) echo "selected";?>>Virginia</option>
              <option value="wa" <?php if (isset($_POST["state"]) && ($_POST["state"]=="wa")) echo "selected";?>>Washington</option>
              <option value="wv" <?php if (isset($_POST["state"]) && ($_POST["state"]=="wv")) echo "selected";?>>West Virginia</option>
              <option value="wi" <?php if (isset($_POST["state"]) && ($_POST["state"]=="wi")) echo "selected";?>>Wisconsin</option>
              <option value="wy" <?php if (isset($_POST["state"]) && ($_POST["state"]=="wy")) echo "selected";?>>Wyoming</option>
            </select>
          </div>
        </div>
        <div class="vl"></div>
        <div class="currloc">
          <input type="checkbox" id="location" name="current" onclick="myLocation()"> Current Location
        </div>
        <div class="row" id="show">
          <input type="submit" value="Search">
          <button onclick="clearData(event)" id="clear">Clear</button>
        </div>
      </form>
    </span>
  </div>

  <div class="view" id="view">
    <br>
    <?php
      $street=$_POST["street"];
      $city=$_POST["city"];
      $state=$_POST["state"];
      $location=$_POST["current"];
      $lat = 0.0;
      $long = 0.0;

      $custLat = $_POST["latitude"];
      $custLong = $_POST["longitude"];
      $time = $_POST["time"];
      
      if($location==true) {
        $ip_url = "http://ip-api.com/json/{$_SERVER["REMOTE_ADDR"]}";
        $ip_resp = file_get_contents($ip_url);
        //echo $ip_resp;
        $json=json_decode($ip_resp);
        $lat=$json->lat;
        $long=$json->lon;
        $city=$json->city;
      }
      else {
        $addr=$street . ", " . $city . ", " . $state;
        $address=urlencode($addr);
        $api_key_1="AIzaSyDQpn2ptRbeZEHunKYQVfBzgjxbDWLvQuM";
        $query_string_1 = "address={$address}&key={$api_key_1}";
        $url_1 = "https://maps.googleapis.com/maps/api/geocode/xml?" . $query_string_1;
        $resp_xml = file_get_contents($url_1);
        //echo $resp_xml;
        $xml=simplexml_load_string($resp_xml) or die("Error: Cannot create object");
        //print_r($xml);
        $lat=$xml->result->geometry->location->lat;
        $long=$xml->result->geometry->location->lng;
      }
      //echo "Lat: " . $lat . "<br> Long: ". $long;

      $api_key_2="44041ac3ff8b4e9be35d309f2880c854";
      $query_string_2 = "{$api_key_2}/{$lat},{$long}";
      $url_2="https://api.forecast.io/forecast/" . $query_string_2 . "?exclude=minutely,hourly,alerts,flags";
      $resp_json = file_get_contents($url_2);
      //echo "<br><br><br>" . $resp_json;
      $json=json_decode($resp_json);
      //var_dump($json);
      //echo $json->code;

      if ($time) {
        $query_string_3 = "{$api_key_2}/{$custLat},{$custLong},{$time}";
        $url_3="https://api.forecast.io/forecast/" . $query_string_3 . "?exclude=minutely";
        $resp_json1 = file_get_contents($url_3);
        //echo "<br><br><br>" . $resp_json1;
        $json1=json_decode($resp_json1);
        //var_dump($json1);
      }

      if ($json1) {
    ?>
        <h2 style="text-align: center;"> Daily Weather Detail </h2>
        <div class="dailyCard" id="dailyCard">
          <?php
            if (($json1->currently->icon=="clear-day") || ($json1->currently->icon=="clear-night")){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
            }
            else if (($json1->currently->icon=="partly-cloudy-day") || ($json1->currently->icon=="partly-cloudy-night")){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png";
            }
            else if ($json1->currently->icon=="rain"){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png";
            }
            else if ($json1->currently->icon=="snow"){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png";
            }
            else if ($json1->currently->icon=="sleet"){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png";
            }
            else if ($json1->currently->icon=="wind"){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png";
            }
            else if ($json1->currently->icon=="fog"){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png";
            }
            else if ($json1->currently->icon=="cloudy"){
              $img_url1="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png";
            }
            else{
              $img_url1="#";
            }

            echo "<img src='" . $img_url1 . "' width=\"175\" height=\"175\" style=\"float:right; padding:15px 30px;\">"
          ?>

          <h1 style="padding-top: 80px; margin: 0;"><?php echo $json1->currently->summary; ?></h1>
          <h1 id="tmp1"><?php echo $json1->currently->temperature; ?><span style="font-size:70px;"><img src="https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png" alt="Degree"  width="12" height="12" id="dg">F</span></h1>

          <?php
            if ($json1->currently->precipIntensity<=0.001){
              $precip1="N/A";
            }
            else if (($json1->currently->precipIntensity>0.001) && ($json1->currently->precipIntensity<=0.015)){
              $precip1="Very Light";
            }
            else if (($json1->currently->precipIntensity>0.015) && ($json1->currently->precipIntensity<=0.05)){
              $precip1="Light";
            }
            else if (($json1->currently->precipIntensity>0.05) && ($json1->currently->precipIntensity<=0.1)){
              $precip1="Moderate";
            }
            else {
              $precip1="Heavy";
            }
          ?>

          <table align="center" id=tbl3>
            <tr>
              <td id="key3"> Precipitation: </td>
              <td id="value3"><?php echo $precip1; ?></td>
            </tr>
            <tr>
              <td id="key3"> Chance of Rain: </td>
              <td id="value3"><?php echo $json1->currently->precipProbability * 100; ?> % </td>
            </tr>
            <tr>
              <td id="key3"> Wind Speed: </td>
              <td id="value3"><?php echo $json1->currently->windSpeed; ?> mph </td>
            </tr>
            <tr>
              <td id="key3"> Humidity: </td>
              <td id="value3"><?php echo $json1->currently->humidity * 100; ?> % </td>
            </tr>
            <tr>
              <td id="key3"> Visibility: </td>
              <td id="value3"><?php echo $json1->currently->visibility; ?> mi </td>
            </tr>
            <tr>
              <?php date_default_timezone_set($json1->timezone); ?>
              <td id="key3"> Sunrise/Sunset: </td>
              <td id="value3"><?php echo date("g:i a", $json1->daily->data[0]->sunriseTime); ?> / <?php echo date("g:i a", $json1->daily->data[0]->sunsetTime); ?></td>
            </tr>
          </table>
        </div>
        <h2 style="text-align: center;"> Day's Hourly Weather </h2>
        <button type="button" class="collapsible"><i class="down"></i></button>
        <div class="content">
          <?php
            $r=0;
            $ar=array();
            foreach ($json1->hourly->data as $key) {
              $ar[$r++] = $key->temperature;
            }
            json_encode($ar);
            //echo json_encode($ar);
          ?>
          <div id="chart_div"></div>
        </div>

    <?php } elseif($json->code!=400) { ?>

        <div class="card" id="card">
          <h1 style="line-height: 0.25;"><b><?php echo $city; ?></b></h1>
          <p id="tz"><?php echo $json->timezone; ?></p>
          <h1 id="tmp"><?php echo $json->currently->temperature; ?><span style="font-size:50px;"><img src="https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png" alt="Degree"  width="15" height="15" id="dg">F</span></h1>
          <h2 id="csumm"><?php echo $json->currently->summary; ?></h2>
          <table style="width:100%" id="tbl1">
            <tr>
              <th><div class="tooltip" ><img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png" alt="Humidity"  width="25" height="25">
                <span class="tooltiptext">Humidity</span></div></th>
              <th><div class="tooltip" ><img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png" alt="Pressure"  width="25" height="25">
                <span class="tooltiptext">Pressure</span> </div></th>
              <th><div class="tooltip" ><img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png" alt="Wind Speed"  width="25" height="25">
                <span class="tooltiptext">Wind Speed</span> </div></th>
              <th><div class="tooltip" ><img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png" alt="Visibility"  width="25" height="25">
                <span class="tooltiptext">Visibility</span> </div></th>
              <th><div class="tooltip" ><img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png" alt="Cloud Cover"  width="25" height="25">
                <span class="tooltiptext">Cloud Cover</span> </div></th>
              <th><div class="tooltip" ><img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png" alt="Ozone"  width="25" height="25">
                <span class="tooltiptext">Ozone</span> </div></th>
            </tr>
            <tr>
              <td style="font: bold 24px serif;"><?php echo $json->currently->humidity; ?></td>
              <td style="font: bold 24px serif;"><?php echo $json->currently->pressure; ?></td>
              <td style="font: bold 24px serif;"><?php echo $json->currently->windSpeed; ?></td>
              <td style="font: bold 24px serif;"><?php echo $json->currently->visibility; ?></td>
              <td style="font: bold 24px serif;"><?php echo $json->currently->cloudCover; ?></td>
              <td style="font: bold 24px serif;"><?php echo $json->currently->ozone; ?></td>
            </tr>
          </table>
        </div>
        <br>
        <div class="jsonTable" id="jsonTable">
          <table style="width:65%"; align="center" id="tbl2">
            <tr>
              <th>Date</th>
              <th>Status</th>
              <th>Summary</th>
              <th>Temperature High</th>
              <th>Temperature Low</th>
              <th>Wind Speed</th>
            </tr>
            <?php
              foreach($json->daily->data as $mydata){
                date_default_timezone_set($json->timezone);
                echo "<tr><td>" . date("Y-m-d", $mydata->time) . "</td>";

                if (($mydata->icon=="clear-day") || ($mydata->icon=="clear-night")){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
                }
                else if (($mydata->icon=="partly-cloudy-day") || ($mydata->icon=="partly-cloudy-night")){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png";
                }
                else if ($mydata->icon=="rain"){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png";
                }
                else if ($mydata->icon=="snow"){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png";
                }
                else if ($mydata->icon=="sleet"){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png";
                }
                else if ($mydata->icon=="wind"){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png";
                }
                else if ($mydata->icon=="fog"){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png";
                }
                else if ($mydata->icon=="cloudy"){
                  $img_url="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png";
                }
                else{
                  $img_url="#";
                }

                echo "<td><img src='" . $img_url . "' width=\"25\" height=\"25\"></td>";
                echo "<td onclick=\"summForm('". $lat . "', '" . $long . "', '" . $mydata->time . "')\">" . $mydata->summary . "</td>";
                echo "<td>" . $mydata->temperatureHigh . "</td>";
                echo "<td>" . $mydata->temperatureLow . "</td>";
                echo "<td>" . $mydata->windSpeed . "</td></tr>";
              }
            ?>
          </table>
        </div>
    <?php } ?>
  </div>

</body>

<script type="text/javascript">
  var coll = document.getElementsByClassName("collapsible");
  var i;
  for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    var content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
      coll[0].innerHTML = "<button type=\"button\" class=\"collapsible\"><i class=\"down\"></i></button>";
    } else {
      coll[0].innerHTML = "<button type=\"button\" class=\"collapsible\"><i class=\"up\"></i></button>";
      content.style.display = "block";
    }
  });
  }

  var ar = <?php echo json_encode($ar); ?>;
  var tp = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
  var t, out = [];
  for(t = 0 ; t < tp.length ; t++)
  	out.push([tp[t],ar[t]]);

  google.charts.load('current', {packages: ['corechart', 'line']});
  google.charts.setOnLoadCallback(drawCurveTypes);
  function drawCurveTypes() {
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'T');
    data.addRows(out);

    var options = {
      hAxis: {
        title: 'Time'
      },
      vAxis: {
        title: 'Temperature'
      },
      series: {
        1: {curveType: 'function'}
      },
      colors: ['skyblue'], width: 800, height: 300
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
  }

  function validateForm() {
    var x = document.forms["myForm"]["street"].value;
    var y = document.forms["myForm"]["city"].value;
    var z = document.getElementById("st").selectedIndex;
    var w = document.getElementById("location");
    if (w.checked == true){
      document.getElementById("view").innerHTML = "";
      return true;
    }
    if (x == "")  {
      document.getElementById("view").innerHTML = "<br><div id=\"errMsg\">Please check the input address.</div>";
      document.getElementById("str").focus();
      return false;
    }
    if (y == "")  {
      document.getElementById("view").innerHTML = "<br><div id=\"errMsg\">Please check the input address.</div>";
      document.getElementById("cty").focus();
      return false;
    }
    if (z < 1)  {
      document.getElementById("view").innerHTML = "<br><div id=\"errMsg\">Please check the input address.</div>";
      document.getElementById("st").focus();
      return false;
    }
    document.getElementById("view").innerHTML = "";
    return true;
  }

  function myLocation() {
    var w = document.getElementById("location");
    if (w.checked == true){
      document.getElementById("str").value = "";
      document.getElementById("str").disabled=true;
      document.getElementById("cty").value = "";
      document.getElementById("cty").disabled=true;
      document.getElementById("st").value = "";
      document.getElementById("st").disabled=true;
    }
    else {
      document.getElementById("str").disabled=false;
      document.getElementById("cty").disabled=false;
      document.getElementById("st").disabled=false;
    }
  }

  function clearData(e){
    e.preventDefault();
    document.getElementById("view").innerHTML = null;
    document.getElementById("str").value = "";
    document.getElementById("cty").value = "";
    document.getElementById("st").value = "";
    document.getElementById("location").checked = false;
  }

  function summForm(lat, long, time) {
    var summ = document.getElementById("summFormDetails");
    var inputTime = document.createElement("INPUT");
    var latitude = document.createElement("INPUT");
    var longitude = document.createElement("INPUT");
    inputTime.name = "time";
    latitude.name = "latitude";
    longitude.name = "longitude";
    inputTime.value = time;
    latitude.value = lat;
    longitude.value = long;
    inputTime.style.display = "none";
    latitude.style.display = "none";
    longitude.style.display = "none";
    summ.appendChild(inputTime);
    summ.appendChild(latitude);
    summ.appendChild(longitude);
    summ.submit();
  }

</script>

</html>
