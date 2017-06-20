<?php

$user = json_decode(file_get_contents('php://input'));

$conn = "host=localhost dbname=arai user=postgres password=NN12bt54_r";
try{
	$link = pg_connect($conn);
	if (!$link) {
  	  die('接続失敗です。'.pg_last_error());
	}

	// PostgreSQLに対する処理
	$result = pg_prepare("query",'SELECT id, pass FROM sysroid.user WHERE id = $1 and pass = $2');
	$result = pg_execute("query",array($user.id,$user.pass));
	if (!$result) {
    	die('クエリーが失敗しました。'.pg_last_error());
	}
	
	$obj;
	
	for ($i = 0 ; $i < pg_num_rows($result) ; $i++){
    	$rows = pg_fetch_array($result, NULL, PGSQL_ASSOC);
		$obj = array("id" => $rows[0], "pass" => $rows[1]);
	}
	$json = json_encode($obj,JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
	
	// ヘッダーを指定
	header( "Content-Type: application/json; charset=utf-8" );
	
	echo $json;
}catch(Exception $e){
	echo $e->getMessage();	
}finally{
	$close_flag = pg_close($link);
}

?>