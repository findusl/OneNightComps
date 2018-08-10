<?php
mysqli_report(MYSQLI_REPORT_STRICT);
$currentVersion = 1.0;//is there some way to make constant?

// specify your own database credentials
private $host = "rdbms.strato.de";
private $username = "U2285767";
private $password = "W3oovyYr56LZt9wIOHUr";
private $db_name = "DB2285767";
public $conn;

// get the database connection
public function getConnection(){

    $this->conn = null;

    try{
        echo "Trying to establish connection<br>";
        $db = new mysqli($host, $username, $password, $db_name);

        if($db->connect_errno > 0){
            exit('Unable to connect to database [' . $db->connect_error . ']');
        }

        echo "Connected to database<br>"
    }catch(mysqli_sql_exception $exception){
        exit("Connection error: " . $exception->getMessage());
    }

    return $db;
}

public function getResultArray($result) {
    $resultArray = array();
    while($row = $result->fetch_array(MYSQL_ASSOC)) {
        $resultArray[] = $row;
    }
    return $resultArray;
}
?>