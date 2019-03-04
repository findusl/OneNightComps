<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");

ini_set('display_errors', 1);

include_once '../database.php';
include_once '../util.php';
include_once 'read.php';

try {
    $database = getConnection();

    $userId = checkLogin($database);
    if ($userId == -1) {
        http_response_code(401);
        return;
    }

    $content = json_decode(file_get_contents('php://input'), true);
    
    $roles = $content['roles'];
    $roleIds = [];
    foreach($roles as $role) {
        $roleIds[] = $role['id'];
    }
    
    $insertCompStmt = $database->prepare("INSERT INTO game_composition (name,description,difficulty_level,creator_user_id,last_modified) VALUES (?,?,?,?,?);");
    $insertCompStmt->bind_param("ssiii", $content['name'], $content['description'], $content['difficultyLevel'], $userId, time());
    $insertCompStmt->execute();
    
    $createdId = $insertCompStmt->insert_id;

    insertCompRoles($database, $createdId, $roleIds);
    
    echo encodeResult($createdId);
} catch(Exception $exception){
    //this should trigger in case of duplicate user
    echo encodeResult(-1, $exception);
}

function insertCompRoles($database, $compId, $roleIds) {
    $insertRoleReferenceStmt = $database->prepare("INSERT INTO game_composition_role (game_composition_id,game_role_id) VALUES (?,?);");
    $i = 0;
    $roleId = $roles[$i++];
    $insertRoleReferenceStmt->bind_param("ii",$compId,$roleId);
    $insertRoleReferenceStmt->execute();
    while($i < sizeof($roles)) {
        $roleId = $roles[$i++];
        $insertRoleReferenceStmt->execute();
    }
}
?>