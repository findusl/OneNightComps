<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET");

ini_set('display_errors', 1);

include_once '../util.php';
include_once '../database.php';

try {
    $database = getConnection();

    echo encodeResult(checkLogin($database));
} catch(Exception $exception){
    echo encodeResult(-1, $exception);
}
?>