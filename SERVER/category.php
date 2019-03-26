<?php

function getcurrentpath(){ 
			$curPageURL = "";
			if ($_SERVER["HTTPS"] != "on"){
				$curPageURL .= "http://";
			} else {
				$curPageURL .= "https://" ;
			}
			if ($_SERVER["SERVER_PORT"] == "80"){
				$curPageURL .= $_SERVER["SERVER_NAME"].$_SERVER["REQUEST_URI"];
			}else{
				$curPageURL .= $_SERVER["SERVER_NAME"].":".$_SERVER["SERVER_PORT"].$_SERVER["REQUEST_URI"];
			}
			$count = strlen(basename($curPageURL));			
			if($count > 3){
				$path = substr($curPageURL, 0, -$count);
			} else {
				$path = $curPageURL;
			}
			return $path ;
}	

// Orig. db name
//$db_mysqli = "sevozbz";

//error_reporting(-1);
error_reporting(E_ALL);

include "config.php";

$conn = mysqli_connect($db_addres, $db_user, $db_password);
mysqli_select_db($conn, $db_name);
mysqli_query($conn, 'SET NAMES utf8');

$ads = 0;

if (isset($_POST['level'])){
	$level = $_POST['level'];
} else {
	$level = 0;	
}

switch ( $level ){

case 0:
	$sql=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "cats ORDER BY catid ASC;");
	while($row=mysqli_fetch_assoc($sql)){		
		$sql2=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "subcats WHERE catid=".$row['catid'].";");
		while ( $row2 = mysqli_fetch_assoc($sql2) ){
			$sql3=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "ads WHERE subcatid=".$row2['subcatid'].";");
			$ads += mysqli_num_rows($sql3);
		}
		$row['total_ads'] = "Объявлений: ".$ads;
		$output[]=$row;
		$ads = 0;
	}
break;

case 1:
	$sql=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "subcats WHERE catid=".$_POST['parent']." ORDER BY subcatid ASC;");
	while($row=mysqli_fetch_assoc($sql)){		
		
		$sql3=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "ads WHERE subcatid=".$row['subcatid'].";");
		$ads += mysqli_num_rows($sql3);		
		$row['total_ads'] = "В субкатегориях: ".$ads;
		$output[]=$row;
		$ads = 0;
		
	}
break;

case 2:
	$sql=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "ads WHERE subcatid=".$_POST['parent']." ORDER BY adid ASC;");
	if ( mysqli_num_rows($sql)>0 ){
	while ( $row = mysqli_fetch_assoc($sql) ){
		$row['total_ads'] = 'Цена: $' . $row['price'];
		$output[]=$row;
	}	
	} else {
		$row['adid'] = 'null';
		$row['total_ads'] = ' - ';
		$output[]=$row;
	}
break;

case 3:
	$sql=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "ads WHERE adid=".$_POST['parent'].";");
	while ( $row = mysqli_fetch_assoc($sql) ){
		
		/*
		$sql2=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "adpics WHERE adid=".$row['adid']." LIMIT 1;");
		if ( mysqli_num_rows($sql2)>0 ){
			while ($row2=mysqli_fetch_assoc($sql2)){
				$row['img1']= $row2['picfile'];
			}
		} else { $row['img1']=''; }
		*/
		
		if ( stripos($row['picfile'], 'adpics') === FALSE ){
			
			//$row['picfile'] = getcurrentpath() . '/empty.png';
			
		} else {			
			$row['picfile'] = 'adpics' . explode('adpics', $row['picfile'])[1];
			$row['picfile'] = getcurrentpath() . '/' . $row['picfile'];
		}
		
		$output[]=$row;
	}
	
	//print('<pre>'); var_dump( $output ); print('</pre>');
	
break;

case 95:
	
	$sql=mysqli_query($conn, "SELECT catid, catname FROM " . $tb_prefix . "cats;");
	while($row=mysqli_fetch_assoc($sql)){		
		$output[]=$row;
	}
break;

case 96:
	
	$sql=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "subcats WHERE catid=".$_POST['parent'].";");
	while($row=mysqli_fetch_assoc($sql)){		
		$output[]=$row;
	}
break;

case 100:
	if ( $_POST['parent'] == $admin_password ){
		$row['login_msg'] = "Теперь вы - админ, и можете удалять категории и объявления";
		$row['login_result'] = 1;
	} else {
		$row['login_msg'] = "Извините, пароль не верный. \nПопробуйте снова.";
		$row['login_result'] = 0;
	}
	$output[]=$row;
break;

case 101:
	$lvl = explode("_", $_POST['parent']);
	switch($lvl[1]){
		case 1:
			$dbtable = $tb_prefix . "cats";
			$dbidcol = "catid";
			
			$sql=mysqli_query($conn, "SELECT * FROM " . $tb_prefix . "subcats WHERE catid=".$lvl[0].";");
			while($row=mysqli_fetch_assoc($sql)){	
				mysqli_query($conn, "DELETE FROM " . $tb_prefix . "ads WHERE subcatid = ".$row['subcatid']);
			}
			mysqli_query($conn, "DELETE FROM " . $tb_prefix . "subcats WHERE catid = ".$lvl[0]);
			mysqli_query($conn, "DELETE FROM " . $dbtable . " WHERE ".$dbidcol." = ".$lvl[0]);
		break;
		case 2:
			$dbtable = $tb_prefix . "subcats";
			$dbidcol = "subcatid";
			
			mysqli_query($conn, "DELETE FROM " . $tb_prefix . "ads WHERE subcatid = ".$lvl[0]);
			mysqli_query($conn, "DELETE FROM " . $dbtable . " WHERE ".$dbidcol." = ".$lvl[0]);
		break;
		case 3:
			$dbtable = $tb_prefix . "ads";
			$dbidcol = "adid";
			
			mysqli_query($conn, "DELETE FROM " . $tb_prefix . "ads WHERE adid = ".$lvl[0]);
		break;
	}
	//$sql=mysqli_query($conn, );
	$row['del_result'] = "успешно уалено(а)!";
	//$row['del_result'] = "DELETE FROM " . $dbtable . " WHERE ".$dbidcol." = ".$lvl[0];
	$output[]=$row;
break;

case 102:
	$jArr =	json_decode($_POST['parent']);	
	//print('<pre>'); var_dump( $jArr ); print('</pre>');
	$publ = date("Y-m-d");
	
	$reqest = "INSERT INTO " . $tb_prefix . "ads (adtitle, email, expireson, price, addesc, subcatid, enabled, createdon) VALUES ('". $jArr->title ."', '". $jArr->contact ."', STR_TO_DATE('".$jArr->expire."', '%Y-%m-%d'), ". $jArr->price .", '". $jArr->descr ."', ". $jArr->subcatid . ", 1, STR_TO_DATE('".$publ."', '%Y-%m-%d'));";
	
	$sql=mysqli_query($conn, $reqest);
	
	if (!$sql){
		//$row['submit_result'] = 'MySQL ERR: ' . mysql_error();
		//$row['submit_result'] = $row['submit_result'] . "<br>" . $reqest;
		$row['submit_result'] = "Проблема с добавлением... Попробуйте снова!";
	} else {
		//$row['submit_result'] = $reqest;
		$row['submit_result'] = "Объявление '".$jArr->title."' успешно добавлено";
	}
	
	$output[]=$row;
break;

case 103:
	$jArr =	json_decode($_POST['parent']);	
	//print('<pre>'); var_dump( $jArr ); print('</pre>');
	
	if ( $jArr->core == "0" ){
		$reqest = "INSERT INTO " . $tb_prefix . "cats (catname, enabled ) VALUES ('". $jArr->title ."', 1);";
	} else {
		$reqest = "INSERT INTO " . $tb_prefix . "subcats (subcatname, catid, enabled ) VALUES ('". $jArr->title ."', ". $jArr->core .", 1);";
	}
		
	$sql=mysqli_query($conn, $reqest);
	
	if (!$sql){
		//$row['submit_result'] = 'MySQL ERR: ' . mysql_error() . "<br>" . $reqest;
		$row['submit_result'] = "Проблема с добавлением... Попробуйте снова!";
	} else {
		//$row['submit_result'] = $reqest;
		$row['submit_result'] = "Категория\Раздел '".$jArr->title."' успешно добавлен!";
	}
	
	$output[]=$row;
break;

default:
	$row['catid'] = 0;
	$row['enabled'] = 0;
	$row['catname'] = "Неизвестные данные";
	$row['total_ads'] = "Получено перменных: ".count($_POST);
	$output[]=$row;
break;

}

if ( !isset($output) ){ 
	$row['catid'] = 0;
	$row['enabled'] = 0;
	$row['catname'] = "Нет данных в БД по запросу";
	$row['total_ads'] = $sql;
	$output[]=$row;	
}

print(json_encode($output));

mysqli_close($conn);
unset($row); unset($output);
?>