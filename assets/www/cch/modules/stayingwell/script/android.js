function CCH() {
    this.showToast = function(stri) {
        Android.showToast(stri);
    },

    this.getGreeting = function() {
    	return Android.getGreeting();
    },

    this.getRoutineInfo = function(inclcharet) {
        return Android.getRoutineInfo(inclcharet);
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
