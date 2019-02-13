<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

ini_set('display_errors', 1);

include_once '../database.php';
include_once '../util.php';

try {
    $database = getConnection();

    $userId = checkLogin($database);
    if ($userId == -1) {
        http_response_code(401);
        return;
    }

    $content = json_decode(file_get_contents('php://input'), true);
    
    $insertCompStmt = $database->prepare("INSERT INTO game_composition (name, description,difficulty_level,creator_user_id,last_modified) VALUES (?,?,?,?,?,?);");
    $insertCompStmt->bind_param("ssiiii", $content['name'], $content['description'], $content['difficultyLevel'], $userId, time());
    $insertCompStmt->execute();
    
    $createdId = $insertCompStmt->insert_id;
    
    $insertRoleReferenceStmt = $database->prepare("INSERT INTO game_composition_role (game_composition_id,game_role_id) VALUES (?,?);");
    $i = 0;
    $roles = $content['roles'];
    $roleId = $roles[$i++]['id'];
    $insertRoleReferenceStmt->bind_param("ii",$createdId,$roleId);
    $insertRoleReferenceStmt->execute();
    while($i < $content['roleCount']) {
        $roleId = $roles[$i++]['id'];
        $insertRoleReferenceStmt->execute();
    }
    
    echo encodeResult($createdId);
} catch(Exception $exception){
    //this should trigger in case of duplicate user
    echo encodeResult(-1, $exception);
}
?>