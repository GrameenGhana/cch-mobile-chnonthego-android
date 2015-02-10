////////////////////////////////////////////////////////////////////////////////
//
//  Copyright 2012-2015 App
//  All Rights Reserved.
//
////////////////////////////////////////////////////////////////////////////////

(function () {
    "use strict";

    //cache the current context
    //use it when in a function
    var self = this;


    //lets export the App object for require as a module
    var App;
    if (typeof exports !== 'undefined') {
        App = exports;
    } else {
        App = self.App = {};
    }

    //lets create global functions like constants
    //using capital case

    /**
     * application version number
     * @type {string}
     */
    App.VERSION = '0.0.1';


    /**
     * the top level element of the appliction
     * used by MVC frameworks for routing
     * @type {string}
     */
    App.EL = 'body';

    /**
     * the root of the application
     * used by MVC frameworks for navigation
     * @type {string}
     */
    App.ROOT = '/';

    /**
     * jquery ID initializer
     * @type {string}
     */
    App.HASH_TAG = '#';

    /**
     * the width of the current device
     * used to position elements absolutely on screen
     * also used to set elements width to match the current device width
     * @type {*}
     */
    App.WIDTH = $(window).width();

    /**
     * the height of the current device
     * used to position elements absolutely on screen
     * also used to set elements height to match the current device height
     * @type {*}
     */
    App.HEIGHT = $(window).height();

    //get the current event from the windows object, so we can enable touch events for android
    //and click events for desktop Defaults touchstart
    App.CURRENT_EVENT = 'ontouchstart' in window ? "click" : "click";

    // helper arrays for managing view animation in the main application
    App.VIEW_MANAGER = ['home'];
    App.REMOVED_VIEWS = [];

    //lets cache the top-section height for views using the carousel
    App.TS_HEIGHT = 0;

    // the view stack index
    App.Z_INDEX = 0;

    // start view time
    App.startTime = new Date();

    //override underscore template tags
    _.templateSettings = {
        evaluate: /\{\{(.+?)\}\}/g,
        interpolate: /\{\{=(.+?)\}\}/g,
        escape: /\{\{-(.+?)\}\}/g
    };

    App.init = function () {

        //this is a javascript plugin that removes the 300m delay on native web views.
        //this plugin can be found in script/vendor/fastclick.min.js
        FastClick.attach(document.body);

        // Show legal page?
        var r = 'www/cch/modules/stayingwell/';
        var startpage = (cch.showLegal()) ? r+'templates/legal.html' : r+'templates/home.html';
        var maintemplate = r+'templates.html';

        //load the home page
        //require(['script/text!'+template, 'script/text!'+startpage], function (template, homeTemp) {
       
            var template = cch.getFileTemplate(maintemplate);
            var homeTemp = cch.getFileTemplate(startpage);

            $(App.EL).append(template);
            $(App.EL).append(homeTemp);

            //after the view successfully loads
            //lets resize it to the current device size
            var home = "#home";
            App.setToDeviceSize(home);
            App.innerViews(home);
            App.carousel(home);

            //resize all view ports
            $(window).resize(function () {

                //set view port width and height to device width and height
                $('.canvas').width($(window).width()).height($(window).height());

            });

            // Ask for new plan each month
            if (cch.getProfileStatus()!="" && !cch.showLegal() && cch.changeMonthlyPlan()) {
                App.navigateTo('gliving/chooseplan.html')
            }

            // process some other stuff
            if ($('.greetinglabel')) {
                $('.greetinglabel').html(cch.getGreeting());
            }

            if ($('#routines_label')) {
                $('#routines_label').html(cch.getRoutineInfo('label'));
            }

            if ($('.routines_today')) {
                $('.routines_today').html(cch.getRoutineInfo('list'));
            }

            $('#legalno').click(function(e) {
                var url = "file:///android_asset/www/cch/modules/stayingwell/done";
                window.location = url;
            });

            $('#legalyes').click(function(e) {
                cch.setLegalStatus("agreed");
                window.location = 'file:///android_asset/www/cch/modules/stayingwell/index.html';
            });

            //lets call the navigation view initializer on application start
            App.navigationView();

            //lets call modal view event delegation on app start
            App.modalView();
            App.imageModal();

            //lets call the pop manager on application start
            App.back();

        //});
    };

    App.initPageRun = function (id) {

        if ($('.greetinglabel')) {
            $('.greetinglabel').html(cch.getGreeting());
        }
            
        if ($('.routines_label')) {
            $('.routines_label').html(cch.getRoutineInfo('label'));
        }

        if ($('.routines_today')) {
            $('.routines_today').html(cch.getRoutineInfo('list'));
            App.modalView();
        }


        if ($('.surveynext') || $('.plannext')) {
            var _dots = $(id).find('.slick-dots');
            $(_dots).css('display', 'none');
        } else {
            var _dots = $(id).find('.slick-dots');
            $(_dots).css('display', 'inline-block');
        }


        if ($('.surveynext')) {
            $('.surveynext').click(function(e) {
                var showError = false;
                if (this.id != "ne") {
                
                	var ids = this.id.split(',');
                	for(var i=0; i < ids.length; i++) {
                    	if (! ($('#'+ids[i]+'A').is(':checked') || 
                           		$('#'+ids[i]+'B').is(':checked') || 
                           		$('#'+ids[i]+'C').is(':checked'))  
                        	) { showError = true; }
                	}
                	if (showError) {
                    	jQuery.facebox({ div: '#fillerror' });
                	} else { 
                   		var _carousel = $(id).find('.carousel');
                    	$(_carousel).slickNext();
                	}
                }  else {
                		var _carousel = $(id).find('.carousel');
                    	$(_carousel).slickNext();
                }
            }); 
        }

        if ($('.surveyprev')) {
            $('.surveyprev').click(function(e) {
                var _carousel = $(id).find('.carousel');
                $(_carousel).slickPrev(); 
            }); 
        }

        $('#surveydone').click(function(e) {
                var responses = '';
                var expectedresponses = 10;
                for(var i=1; i<=10; i++)
                {
                    $('[name="question'+i+'"]').each(function() {
                        if($('#'+this.id).is(':checked'))
                        {
                            responses += $('#'+this.id).val()+",";
                            expectedresponses--;
                        }
                    });
                } 
                if (expectedresponses != 0)
                {
                    jQuery.facebox({ div: '#fillerror' });
                } else {
                    cch.setProfileStatus(responses);
                    App.navigateTo('gliving/chooseplan.html');
                }
        });

        if ($('.plannext')) {
            $('.plannext').click(function(e) {
                var showError = false;
                var ids = this.id.split(',');
                for(var i=0; i < ids.length; i++) {
                    if (! ($('#'+ids[i]+'A').is(':checked') || 
                           $('#'+ids[i]+'B').is(':checked') || 
                           $('#'+ids[i]+'C').is(':checked') ||
                           $('#'+ids[i]+'D').is(':checked') ||
                           $('#'+ids[i]+'E').is(':checked') ||
                           $('#'+ids[i]+'F').is(':checked') )  
                        ) { showError = true; }
                }
                if (showError) {
                    jQuery.facebox({ div: '#fillerror' });
                } else { 
                   var _carousel = $(id).find('.carousel');
                    $(_carousel).slickNext();
                } 
            }); 
        }
        
        $('#plandone').click(function(e) {
                var response = '';
                $('[name="plan"]').each(function() {
                     if($('#'+this.id).is(':checked'))
                     {
                        response = $('#'+this.id).val();
                     }
                });
                cch.setMonthlyPlan(response);
                cch.restart();
                //App.navigateTo('home.html');       
        });
    };

    //function for loading and animation navigation views
    App.navigationView = function () {

        //look for all class names with the name navigation-view
        //attach an event to them
        //extract the data object
        $(document).delegate('.navigation-view', App.CURRENT_EVENT, function (e) {

            e.preventDefault();
            var _view = $(this).data('view');
            var temp_id = "view" + App.createHash();
            var _id = App.HASH_TAG + temp_id;
            if (typeof _view === 'undefined') {
                alert("Error Loading Page");
            }

            if (~ _view.indexOf('http')) {
                window.location = _view;
            }

            if (~ _view.indexOf('gliving')) {
                var profile = cch.getProfileStatus(); 
                if (profile=="") {
                    _view = 'gliving/survey.html';
                } else {
                    var plan = cch.getMonthlyPlan();
                    if (plan=="") {
                        _view = "gliving/chooseplan.html";
                    } else {
                        if ((~ _view.indexOf('routines')) || (~ _view.indexOf('stressfree'))) {
                            if (! (_view == "gliving/content/routines/index.html")) {
                                _view = _view.replace("routines/","routines/"+profile+"/");
                            }
                            if (! (_view == "gliving/content/stressfree/index.html")) {
                                _view = _view.replace("stressfree/","stressfree/"+profile+"/");
                            }
                        }
                    }
                }
            }

            App.navigateTo(_view);
        });
    };

    App.logEntry = function (page) {
        //log entry
        var end = new Date();
        var data = "{'type':'url', 'value':'"+ page +"'}";
        cch.logger(data, App.startTime.getTime(), end.getTime());
        App.startTime = end;
    };

    App.navigateTo = function(_view) {
        var page = 'www/cch/modules/stayingwell/templates/'+_view;
        var template = cch.getFileTemplate(page);

        App.logEntry(page);

        var temp_id = "view" + App.createHash();
        var _id = App.HASH_TAG + temp_id;

        var _json = {
            template: template,
            id: temp_id,
            index: App.Z_INDEX++
        };
        var temp = _.template($("script.fullscreen-view").html(), _json);
        $(App.EL).append(temp);
        App.VIEW_MANAGER.push(_id);
        App.clearViews();
        App.setToDeviceOffsetX(_id);
        App.setToDeviceSize(_id);
        $(window).trigger('resize');
        App.screenxReverse(_id);
        App.innerViews(_id);
        App.carousel(_id);
        App.detectBackButton(_id);
        App.initPageRun(_id);
    };

    //lets check for pages with the class name page-with-back-button
    //and decide to make the button sticky depending on the height of
    //the content in page-with-back-button
    App.detectBackButton = function (id) {
        //lets find object using id params as the current page context
        var bb_page = $(id).find('.page-with-back-button');
        var back_button = $(id).find('.back-button');
        //check to see if it's a valid jQuery object
        if (bb_page.length >= 1) {
            //lets get the height of the back button page
            var bb_page_height = $(bb_page).height();
            //lets cache the device height
            var device_height = $(window).height();
            // lets calculate the percentage to determine
            //when to make the back button sticky
            var perc = (bb_page_height / device_height) * 100;
            perc = Math.floor(perc);

            //console.log('percentage ' + perc);
            //if perc is less than value make it sticky
            //ps: play around with percentage to get the right value
            if (perc <= 80) {
                $(back_button).addClass('back_btn_sticky');
            }
        }
    };

    //lets detect and initial a carousel if a view requests it
    App.carousel = function (id) {
        var _carousel = $(id).find('.carousel');
        if (_carousel.length >= 1) {
            if (typeof $.fn.slick !== 'undefined') {

                var slideOpt = {
                    'lazyLoad': 'progressive',
                    'touchMove': false,
                    infinite: false,
                    'dots': true,
                    'arrows': false,
                    'autoplay': false,
                    'swipe': false,
                    speed: 300
                };
                $(_carousel).slick(slideOpt);
                var _dots = $(id).find('.slick-dots');
                var _topSection = $(id).find('.top-section');
                $(_dots).css('bottom', 10).appendTo(_topSection);
            }
        }
    };

    //lets find the inner views within a specific view container
    //by providing a jquery id
    App.innerViews = function (id) {
        var top_section = $(id).find('.top-section');
        var bottom_section = $(id).find('.bottom-section');
        if (bottom_section.length >= 1 && top_section.length >= 1) {
            var _top = $(top_section);
            var _bottom = $(bottom_section);
            var window_height = $(window).height();
            var _half = window_height * 0.5;
            if (_bottom.height() >= _half) {
                _bottom.height(_half).css('overflow-y', 'scroll');
                _top.height(_half - 10);
            } else {
                var ts_height = (window_height - _bottom.height()) - 4;
                _top.height(ts_height - 6);
            }
            App.TS_HEIGHT = _top.height();
        }
    };

    //function to show and load content into a modal dialog
    App.modalView = function () {


        //look for all class names with the name navigation-view
        //attach an event to them
        //extract the data object
        $(document).delegate('.modal-view', App.CURRENT_EVENT, function (e) {

            e.preventDefault();
            $('.main-modal').remove();
            var _view = $(this).data('view');
            var _type = $(this).data('type');

            if (~ _view.indexOf('gliving')) {
                var profile = cch.getProfileStatus(); 
                if (profile=="") {
                    _view = 'gliving/survey.html';
                } else {
                    if ((~ _view.indexOf('routines')) || (~ _view.indexOf('routines'))) {
                        if (! (_view == "gliving/content/routines/index.html")) {
                            _view = _view.replace("routines/","routines/"+profile+"/");
                        }
                        if (! (_view == "gliving/content/stressfree/index.html")) {
                            _view = _view.replace("stressfree/","stressfree/"+profile+"/");
                        }
                    }
                }
            }

            var _template = ['script/text!templates/' + _view];
            if (typeof _view === 'undefined') {
                alert("Error Loading Modal");
            } else {
                if (typeof _type !== 'undefined') {
                    _template = ['script/text!' + _view];
                }
                //require(_template, function (template) {
                    var page = 'www/cch/modules/stayingwell/templates/'+_view;
                    var template = cch.getFileTemplate(page);
        
                    App.logEntry(page);

                    var _json = {
                        template: template
                    };
                    var temp = _.template($("script.modal-view").html(), _json);
                    $('body').append(temp);
                    var content = '.modal-content';
                    window.setTimeout(function () {
                        if ($(content).height() >= $(window).height()) {
                            $(content).css('margin-top', "25px").show();
                        } else {
                            $(content).css('margin-top', (($(window).height() - $(content).height())) / 2).show();
                        }
                    }, 160);
                    $('.modal-close').off().on(App.CURRENT_EVENT, function (e) {
                        $('.modal').fadeOut(function () {
                            $(this).remove();
                        });
                    });
                //});
            }
        });
    };

    App.imageModal = function () {

        //look for all class names with the name navigation-view
        //attach an event to them
        //extract the data object
        $(document).delegate('.image-view', App.CURRENT_EVENT, function (e) {
            e.preventDefault();
            //stop event from bubbling
            e.stopImmediatePropagation();
            $('.image-modal').remove();
            var _json = {
                template: $(this).html()
            };
            var temp = _.template($("script.image-view").html(), _json);
            $('body').append(temp);
            var content = '.image-modal-content';
            window.setTimeout(function () {
                if ($(content).height() >= $(window).height()) {
                    alert('yup');
                    $(content).css('margin-top', "25px").show();
                } else {
                    $(content).css('margin-top', (($(window).height() - $(content).height())) / 2).show();
                }
            }, 160);
            $('.image-modal-close').off().on(App.CURRENT_EVENT, function (e) {
                $('.image-modal').fadeOut(function () {
                    $(this).remove();
                });
            });
        });
    };



    //remove a view from the view stack
    App.back = function () {

        //this event removes the next object in the view stack
        //you can attach the .back-button class to remove the immediate view in the stack
        $(document).delegate('.back-button', App.CURRENT_EVENT, function () {
            // alert("event");
            var _lastView = App.VIEW_MANAGER[App.VIEW_MANAGER.length - 1];
            if (App.VIEW_MANAGER.length !== 0) {
                App.screenx(_lastView);
                App.REMOVED_VIEWS.push(_lastView);
                App.VIEW_MANAGER.pop();
            }
        });
    };

    //garbage collect all unused views to preserve memory on mobile devices
    App.clearViews = function () {
        if (App.REMOVED_VIEWS.length) {
            _.each(App.REMOVED_VIEWS, function (view) {
                $(view).remove();
            });
        }
    };

    /**
     * function for animating elements which are positioned below the screen
     * @param el
     * @constructor
     */
    App.screenxReverse = function (el) {

        // $(el).animate({ y: -(App.HEIGHT)}, 1000, 'ease');
        tram(el).add('transform 0.5s ease').start({x: -(App.WIDTH)});

    };

    App.screenx = function (el, callback) {

        // $(el).animate({ y: -(App.HEIGHT)}, 1000, 'ease');
        tram(el).add('transform 1.2s ease').start({x: (App.WIDTH)}, function () {
            if (typeof callback === 'function') {
                callback();
            }
        });
    };

    /**
     * remove DOM element using jquery
     * @param el
     */
    App.destroyUI = function (el) {

        $(el).remove();

    };

    /**
     * function for animating elements out of screen position
     * @param obj a
     * @constructor
     */
    App.offscreenUI = function (el) {

        // $(el).animate({ y: -(App.HEIGHT + App.HEIGHT)}, 1000, 'ease');
        tram(el).add('transform 2s ease').start({x: -(App.WIDTH + App.WIDTH)});

    };

    /**
     * set an element to device size
     * @param el a jquery|zepto|endo object
     */
    App.setToDeviceSize = function (el) {

        $(el).width(App.WIDTH).height(App.HEIGHT);

    };

    /**
     * position an element using half of the devices width
     * @param el a jquery|zepto|endo object
     */
    App.setToDeviceCenterX = function (el) {

        $(el).css({"left": (App.WIDTH * 0.5) + "px"});

    };

    /**
     * position an element using half of the devices height
     * @param el a jquery|zepto|endo object
     */
    App.setToDeviceCenterY = function (el) {

        $(el).css({"top": (App.HEIGHT * 0.5) + "px"});

    };

    /**
     * position an element off the screen using the devices full width
     * @param el a jquery|zepto|endo object
     */
    App.setToDeviceOffsetX = function (el) {

        $(el).css({"left": (App.WIDTH) + "px"});
    };

    /**
     * position an element off the screen using the devices full height
     * @param el a jquery|zepto|endo object
     */
    App.setToDeviceOffsetY = function (el) {

        $(el).css({"top": (App.HEIGHT) + "px"});
    };

    App.createName = function (name) {

        return name + Math.ceil(Math.random() * 14000);
    };

    App.hash = function () {

        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };

    App.createHash = function () {

        return (App.hash() + App.hash() + "-" + App.hash() + "-" + App.hash() + "-" + App.hash() + "-" + App.hash() + App.hash() + App.hash());

    };

    /**
     * expose the App object to the windows object;
     * this makes it public
     * doing this doesn't pollute the global scope or namespace
     * @type {*}
     */
    window.App = App;

}.call(this));

//initialise application
$(document).ready(App.init);
