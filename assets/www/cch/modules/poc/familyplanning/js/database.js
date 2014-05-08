/*
  Summary
    The XML database library defines the database
    object. The database object provides an
    interface for the XML database, which contains
    records for the medical conditions and
    contraceptive options presented by ACE2. 
*/
var database = {
  /*
    Summary
      Accepts a continuation; loads the database
      file; and calls the continuation function.
  */
  load: function (continuation) {
    // alert ('[database.load]');
    var self = this;
    $.get ("database.xml",
      function (xml) {
        self.xml = xml;
        continuation ();
    });
  },

  /*
    Summary
      Accepts a JQuery object that represents an
      entry XML element and returns an object that
      represents the same entry.
  */
  _getEntry: function (x) {
    // alert ('[database._getEntry]');

    var self = this;
    return {
      id:   x.attr ('id'),
      name: x.attr ('name'),
      type: x.attr ('type'),
      sets: $('> entry-set', x).map (
              function (i, y) {
                return self._getSet ($(y));
            }).get (),
      methods: x.attr ('methods').split (',')
    };
  },

  /*
    Summary
      Accepts an entry id and returns the entry that
      has the given id.
  */
  getEntry: function (entryId) {
    // alert ('[database.getEntry] entryId: ' + entryId);
    return this._getEntry ($('entry#' + entryId, this.xml).first ());
  },

  // Returns the id of every entry in the database.
  getEntries: function () {
    // alert ('[database.getEntryIds]');
    var self = this;
    return $('> entry', this.xml).map (
      function (i, x) {
        return self._getEntry ($(x));
    }).get ();
  },

  /*
    Summary
      Accepts a JQuery object that represents an XML
      entry-set element and returns an object that
      represents the same entry set.
  */
  _getSet: function (x) {
    // alert ('[database._getSet]');
    var self = this;
    return {
      entries: $('> entry', x).map (
                 function (i, y) {
                   return self._getEntry ($(y));
               }).get ()
    };
  },

  // Returns the set of root entries.
  getRootSets: function () {
    // alert ('[database.getRootEntries]');
    var self = this;
    return $('entries > entry-set', this.xml).map (
      function (i, x) {
        return self._getSet ($(x));
    }).get ();
  },

  /*
    Summary
      Accepts a JQuery object that represents a
      method element and returns a method object.
  */
  _getMethod: function (x) {
    // alert ('[database._getMethod]');
    return {
      id:                      x.attr ('id'),
      name:                    x.attr ('name'),
      summary:                 x.attr ('summary'),
      description:             x.attr ('description'),
      icon:                    x.attr ('icon'),
      effectiveness:           x.attr ('effectiveness'),
      effects:                 x.attr ('effects'),
      correctUseEffectiveness: x.attr ('correctUseEffectiveness'),
      commonUseEffectiveness:  x.attr ('commonUseEffectiveness'),
      benefits:                x.attr ('benefits'),
      risks:                   x.attr ('risks'),
      complications:           x.attr ('complications')
    };
  },

  // Accepts a method id and returns the method that has the given id.
  getMethod: function (methodId) {
    // alert ('[database.getMethodId] method id: ' + methodId);
    return this._getMethod ($('method#' + methodId, this.xml).first ());
  },

  // Returns the method id of every method in the database.
  getMethodIds: function () {
    // alert ('[database.getMethodIds]');
    var self = this;
    return $('method', this.xml).map (
      function (i, x) {
        return $(x).attr ('id');
    }).get ();
  },

  /*
    Summary
      Accepts an entry id, entryId, and returns the
      ids of the methods that are restricted by the
      given entry.
  */
  getMethodsRestrictedByEntry: function (entryId) {
    // alert ('[database.getMethodsRestrictedByEntry] entry id: ' + entryId);
    var methodIds = $('restriction[condition="' + entryId + '"][serious="true"]', this.xml).map (
      function (i, x) {
        return $(x).attr ('method');
    }).get ();
    // alert ('[database.getMethodsRestrictedByEntry] entry id: ' + entryId + ' methods: ' + JSON.stringify (methodIds));
    return methodIds;
  }
};
