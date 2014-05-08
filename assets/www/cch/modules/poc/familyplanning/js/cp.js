/*
  Summary
    The Continuation Passing library defines a set
    of combinators for functions that use
    continuation-passing style.
*/
var CP = {
  version: "1.0",

  /*
    Summary
      Accept four arguments: f, a function that
      accepts three arguments, an accumulator value,
      a list element, and a continuation function
      that accepts an accumulator value; init, the
      initial accumulator value; and xs, a list of
      objects; and continuation, a function that
      accepts an accumulation value; accumulates the
      values in xs and passes the result to
      continuation.
  */
  foldl: function (f, init, xs, continuation) {
    // alert ('[CP.foldl] init: ' + init + ' xs: ' + xs);
    if (xs.length === 0) return continuation (init);

    var self = this;
    var x = xs.shift ();
    f (init, x, function (result) {
        self.foldl (f, result, xs, continuation);
    });
  },

  /*
    Summary
      A helper function for CP.map. Accepts four
      arguments: f, a function that accepts two
      arguments: x, a value from xs; and
      continuation; xs, a list of values; ys, a list
      of values; and continuation, a function that
      accepts a list of results; applies f to every
      element in xs and pushes the results onto ys.
      Once xs is empty, passes ys to continuation.
  */
  _map: function (f, xs, ys, continuation) {
    // alert ('[CP._map] xs: ' + xs + ' ys: ' + ys);
    if (xs.length === 0) return continuation (ys);

    var self = this;
    var x = xs.shift ();
    f (x, function (y) {
      ys.push (y);
      self._map (f, xs, ys, continuation);
    });
  },

  /*
    Summary
      Accepts three arguments: f, a function that
      accepts two arguments: x, an element from xs;
      and a continuation; xs, a list of values; and
      continuation a function that accepts a list of
      values; applies f to every element in xs and
      passes the result to continuation.
  */
  map: function (f, xs, continuation) {
    // alert ('[CP.map]');
    this._map (f, xs, [], continuation);
  }
}
