/* Staying well functions */

function CCH() {
    this.getQuote = function()  { 
        return Android.getQuote(); 
    },

    this.getTodayInHistory = function() { 
        return Android.getTodayInHistory(); 
    }
}
$(document).ready(function()  {

    var cch = new CCH();

    if ($('body#home')) {
       //cch.getPlannedEvents('#planner',3); 
    }

     // Event planner

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


