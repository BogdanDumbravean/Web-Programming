
function getQuiz(questionCount, callbackFunction) {
	$.getJSON(
		"QuizController",
		{ action: 'getQuiz', questionCount: questionCount },
		callbackFunction
	);
}

/*function getUserAssets(userid, callbackFunction) {
	$.getJSON(
		"AssetsController",
		{ action: 'getAll', userid: userid },
	 	callbackFunction
	);
}

function updateAsset(id, userid, description, value, callbackFunction) {
    $.get("AssetsController",
		{ action: "update",
			id: id,
			userid: userid,
			description: description,
			value: value
		},
		callbackFunction
	);
}
*/
