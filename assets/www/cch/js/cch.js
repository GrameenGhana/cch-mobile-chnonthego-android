function CCH() {
    this.getQuote = function()  { 
        return Android.getQuote(); 
    },

    this.getTodayInHistory = function() { 
        return Android.getTodayInHistory(); 
    },

    this.refreshEvents = function() {
        Android.refreshEvents();
    },
    
    this.getNumEventsToday = function() {
    	return Android.getNumEventsToday();
    },
    
    this.getTodaysEventsSnippet = function() {
    	return Android.getTodaysEventsSnippet();
    },
    
    this.getEventsList = function(period) {
    	return Android.getEventsList(period);
    },

    this.getPreviousLocations = function() {
        var s = Android.getPreviousLocations();
        var data = JSON.parse(s);
        return data.myLocations;
    }
}

$(document).ready(function()  {

    var cch = new CCH();

    // Event planner
    if ($('#planner')) {
    	$('#plannerbadge').html(cch.getNumEventsToday());
    }
    
    $('.gotoevent').click(function(e) {
         var url = $(this).data('url');
         window.location = url; 
    });
    

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


