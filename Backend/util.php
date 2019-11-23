<?php
function encodeResult($result, $error = "") {
    if($error instanceof Exception) {
        $error = $error->getCode()." ".$error->getMessage();
    }
    if($error != null && $error !== '') {
        file_put_contents('./errors.log', $error, FILE_APPEND);
    }
    return json_encode(array("errorMessage"=>$error, "result"=>$result));
}

function checkLogin($database) {
    $loginStmt = $database->prepare('SELECT id, pw_hash_base64 FROM user WHERE user_name=?;');
    $loginStmt->bind_param("s", $_SERVER["PHP_AUTH_USER"]);
    $loginStmt->execute();

    $id = -1;
    $pwHash = "";

    $loginStmt->bind_result($id, $pwHash);
    $loginStmt->fetch();
    $loginStmt->close();

    if ($id != -1 and password_verify($_SERVER["PHP_AUTH_PW"], $pwHash)) {
        return $id;
    }
    else {
        return -1;
    }
}
?>