<?php
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, 
                Authorization, X-Requested-With");
    print_r($_SERVER);
    $headers =  apache_request_headers();
    $headerString = "";
    foreach($headers as $key=>$val){
      $headerString = $headerString. $key . ': ' . $val . ';';
    }
    echo "user:".$_SERVER["PHP_AUTH_USER"]." pw:".$_SERVER["PHP_AUTH_PW"]." query: ".$_SERVER["QUERY_STRING"]." headers: ".$headerString;
?>