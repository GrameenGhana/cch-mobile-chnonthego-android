function CCH() {
    this.getQuote = function()  { 
        return Android.getQuote(); 
    },

    this.getTodayInHistory = function() { 
        return Android.getTodayInHistory(); 
    },
    
    this.getNumEventsToday = function() {
    	return Android.getNumEventsToday();
    },
    
    this.getTodaysEventsSnippet = function() {
    	return Android.getTodaysEventsSnippet();
    }
}

$(document).ready(function()  {

    var cch = new CCH();

    // Event planner
    if ($('#planner')) {
    	$('#plannerbadge').html(cch.getNumEventsToday());
    }

    // Staying well
    if ($('#mainquote')) {
       $('#mainquote').html(cch.getQuote());
    }

    if ($('#todayinhistory')) {
	        $('#todayinhistory').html(cch.getTodayInHistory());
    }

    $('.featurette').click(function(e) {
         var url = $(this).data('url');
         window.location = url;
    });
});


