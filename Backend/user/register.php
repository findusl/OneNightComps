<?php
//Expects post request with these parameters: username, passwordHashBase64, eMail

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

ini_set('display_errors', 1);

include_once '../database.php';
include_once '../util.php';

try {
    $database = getConnection();

    $content = json_decode(file_get_contents('php://input'), true);
    
    $registerStmt = $database->prepare("INSERT INTO user (user_name, pw_hash_base64, e_mail) VALUES (?,?,?);");
    $pwHash = password_hash($content['passwordBase64'], PASSWORD_DEFAULT);
    $registerStmt->bind_param("sss", $content['username'], $pwHash, $content['eMail']);
    $registerStmt->execute();
    
    echo encodeResult(true);
} catch(Exception $exception){
    //this should trigger in case of duplicate user
    echo encodeResult(false, $exception);
}