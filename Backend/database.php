<?php
mysqli_report(MYSQLI_REPORT_STRICT);
$currentVersion = 1.0;//is there some way to make constant?

// specify your own database credentials
$host = "rdbms.strato.de";
$username = "U3462419";
$password = "";
$db_name = "DB3462419";

// get the database connection
function getConnection(){
    global $host, $username, $password, $db_name;
    try{
        //echo "Trying to establish connection<br>";
        $db = new mysqli($host, $username, $password, $db_name);

        if($db->connect_errno > 0){
            exit('Unable to connect to database [' . $db->connect_error . ']');
        }

        return $db;
    }catch(mysqli_sql_exception $exception){
        exit("Connection error: " . $exception->getMessage());
    }
}
?>