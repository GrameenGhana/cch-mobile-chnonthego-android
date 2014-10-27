/*
  Summary
    The Page stack represents a list of URLs that
    record how the user should be redirected back
    when they press the back button.
*/
var PageStack = {

  set: function (localStorage, stack) {
    localStorage.setItem ('page_stack', JSON.stringify (stack));
  },

  get: function (localStorage) {
    return JSON.parse (localStorage.getItem ('page_stack'));
  },

  init: function (localStorage) {
    this.set (localStorage, new Array ());
  },

  /*
    Summary
      Accepts a URL that references the current page
      and pushes this URL onto the page stack.
  */
  pushCurrentPage: function (localStorage, url) {
    var stack = this.get (localStorage);
    if (stack.length === 0) { stack = new Array (); }
    stack.push (url);
    this.set (localStorage, stack);
  },

  popCurrentPage: function (localStorage) {
    var stack = this.get (localStorage);
    stack.pop ();
    this.set (localStorage, stack);
  },

  getCurrentPage: function (localStorage) {
    var stack = this.get (localStorage);
    return stack.pop ();
  },

  /*
    Summary
      Returns the URL of the previous page from the
      page stack and removes the reference from the
      stack.
  */
  getPreviousPage: function (localStorage) {
    var stack = this.get (localStorage);
    stack.pop ();
    return stack.pop ();
  },

  getOnBackButton: function (localStorage) {
    return function (x) {
      return function () {
        var url = x.getPreviousPage (localStorage);
        x.popCurrentPage (localStorage);
        x.popCurrentPage (localStorage);
        location.href = url;
      };
    } (this);
  },

  getOnBackButtonT: function (localStorage) {
    return function (x) {
      return function () {
        var url = x.getCurrentPage (localStorage);
        x.popCurrentPage (localStorage);
        location.href = url;
      };
    } (this);
  }
};
