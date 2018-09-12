<?php
    //Expects post request with these parameters: username, passwordHashBase64, eMail

    // required headers
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    //header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, 
                Authorization, X-Requested-With");

    include_once '../database.php';
    //ini_set('display_errors', 1);

    try {
        $database = getConnection();
    
        $input = file_get_contents('php://input');
        $content = json_decode($input, true);
    
        $input = array();
        $input['user_name'] = $database->real_escape_string(htmlspecialchars_decode($content['username']));
        $input['pw_hash_base64'] = $database->real_escape_string($content['passwordHashBase64']);
        $input['e_mail'] = $database->real_escape_string(html_entity_decode($content['eMail']));

        $registerQuery = 'INSERT INTO user (user_name, pw_hash_base64, e_mail) VALUES ("'.$input['user_name'].
        '","'.$input['pw_hash_base64'].'","'.$input['e_mail'].'");';
        $queryResult = $database->query($registerQuery);
        $result = true;
        $response = array("errorMessage"=>"query: ".$registerQuery, "result"=>$result);
        echo json_encode($response);
    } catch(Exception $exception){
        $response = array("errorMessage"=>$exception->getMessage(), "result"=>"");
        echo json_encode($response);
    } 
?>