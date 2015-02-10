function CCH() {
    this.restart = function() {
        Android.restartApp();
    },

    this.showToast = function(stri) {
        Android.showToast(stri);
    },

    this.logger = function(data, start, end) {
        Android.saveToCCHLog(data, start, end);
    },

    this.getGreeting = function() {
    	return Android.getGreeting();
    },

    this.getRoutineInfo = function(inclcharet) {
        return Android.getRoutineInfo(inclcharet);
    },

    this.markActivityDone = function(cb, uuid) {
        //Android.showToast(uuid);
        Android.markRoutineDone(uuid);
        // disable check box
        $('.routines_today').html(cch.getRoutineInfo('list'));  
    },
    
    this.showLegal = function() {
        return (Android.getLegalStatus()=="") ? true : false;
    },

    this.setLegalStatus = function(status) {
        Android.setLegalStatus(status);
    },

    this.setProfileStatus = function(status) {
        Android.setProfileStatus(status);
    },
    
    this.getProfileStatus = function() {
        return Android.getProfileStatus();
    },

    this.setMonthlyPlan = function(plan) {
        Android.setMonthlyPlan(plan);
    },

    this.getMonthlyPlan = function() {
        return Android.getMonthlyPlan();
    },
    
    this.changeMonthlyPlan = function() {
        return (Android.changeMonthlyPlan()=="true") ? true:false;
    },

    this.getFileTemplate = function(file) {
        return Android.getFileTemplate(file);
    }
}
var cch = new CCH();
