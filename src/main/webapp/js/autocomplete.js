$(function() {
	$("#userName").autocomplete(
		{
			source : function(request, response) {
				$.ajax({
					url : "names",
					dataType : "json",
					data : {
						term : request.term
					},
					success : function(data) {
						response($.map(data.names, function(item) {
							return {
								label : item.label,
								value : item.label
							}
						}));
					}
				});
			},
			minLength : 1
		});
});