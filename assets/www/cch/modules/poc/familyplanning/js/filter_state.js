/*
  Summary
*/
var FilterState = {
  // Returns an empty filter state object.
  create: function () {
    return {
      sets:      [],
      methodIds: [],

      /*
        Summary
          Accepts a database, database, and an entry set,
          set, and returns an array of the methods that
          are restricted by the entries contained within
          set.
      */
      getMethodsRestrictedBySet: function (database, set) {
        // alert ('[getMethodsRestrictedBySet] set: ' + JSON.stringify (set));
        var methodIds = [];
        for (var i = 0; i < set.entries.length; i ++) {
          methodIds = _.union (methodIds, this.getRestrictedMethods (database, set.entries [i]));
        }
        // alert ('[getMethodsRestrictedBySet] method ids: ' + JSON.stringify (methodIds));
        return methodIds;
      },

      /*
        Summary
          Accepts a database, database, and an array of
          entry sets, sets, and returns an array of ids
          of the methods that are restricted by the given
          sets.
      */
      getMethodsRestrictedBySets: function (database, sets) {
        // alert ('[getMethodsRestrictedBySets] sets: ' + JSON.stringify (sets));
        var methodIds = [];
        for (var i = 0; i < sets.length; i ++) {
          methodIds = _.union (methodIds, this.getMethodsRestrictedBySet (database, sets [i]));
        }
        // alert ('[getMethodsRestrictedBySets] methods: ' + JSON.stringify (methodIds));
        return methodIds;
      },

      /*
        Summary
          Accepts a database, database, and an entry,
          entry, and returns the ids of the methods that
          are restricted by the given entry and its
          subentries.
      */
      getRestrictedMethods: function (database, entry) {
        // alert ('[getRestrictedMethods] entry: ' + JSON.stringify (entry));
/*
        var methodIds = database.getMethodsRestrictedByEntry (entry.id);
        for (var i = 0; i < entry.sets.length; i ++) {
          methodIds = _.union (methodIds, this.getMethodsRestrictedBySet (database, entry.sets [i]));
        }
*/
        var methodIds = entry.methods;
        // alert ('[getRestrictedMethods] entry: ' + entry.id + ' methods: ' + JSON.stringify (methodIds));
        return methodIds;
      },

      /*
        Summary
          Accepts three arguments: database, a database;
          set, an entry set; and methodIds, an array of
          method ids; and returns the number of methods
          in methodIds, that the entries in set could
          possible restrict.
      */
      getSetWeight: function (database, set) {
        // alert ('[FilterState.getSetWeight] set: ' + JSON.stringify (set));
        return _.intersection (
          this.methodIds,
          this.getMethodsRestrictedBySet (database, set)
        ).length;
      },

      /*
        Summary
          Accepts a database object, database, and sorts
          and filters the entry sets by weight.
      */
      filterSets: function (database) {
        // alert ('[FilterState.filterSets] sets: ' + JSON.stringify (this.sets));
        var self = this;
        this.sets =
          _.chain  (this.sets)
           .map    (function (set) {
                      return {
                        set:    set,
                        weight: self.getSetWeight (database, set)
                      };
                   })
           .filter (function (x) { return x.weight > 0; })
           .sortBy (function (x) { return x.weight; })
           .map    (function (x) { return x.set; })
           .value  ();
        // alert ('[FilterState.filterSets] filtered sets.');
      },

      /*
        Summary
          Accepts a database object, database, and an
          array of entry sets, sets, and adds sets to
          this.sets.
      */
      addSets: function (database, sets) {
        // alert ('[FilterState.addSets]');
        this.sets = this.sets.concat (sets);
        this.filterSets (database);
        // alert ('[FilterState.addSets] added sets.');
      },

      /*
        Summary
          Accepts a database object, database, and an
          entry id; and adds the entry sets associated
          with the given entry to this.sets.
      */
      expandEntry: function (database, entry) {
        // alert ('[FilterState.expandEntry] entry: ' + JSON.stringify (entry));
        this.addSets (database, entry.sets);
        // alert ('[FilterState.expandEntry] expanded entry.');
      },

      /*
        Summary
          Accepts a database object, database, and an
          entry, entry, adds entry's entry sets to
          this.sets and removes any methods that are
          restricted by entry from this.methodIds.
      */
      applyEntry: function (database, entry) {
        // alert ('[FilterState.applyEntry]');
        this.expandEntry (database, entry);
        this.methodIds = _.difference (
                           this.methodIds,
                           database.getMethodsRestrictedByEntry (entry.id)
                         );
        this.filterSets (database);
        // alert ('[FilterState.applyEntry] applied entry.');
      },

      /*
        Summary
          Initializes the sets and methodIds arrays.
      */
      init: function (database) {
        // alert ('[FilterState.init]');
        this.sets      = database.getRootSets ();
        this.methodIds = database.getMethodIds ();
        // alert ('[FilterState.init] sets: ' + JSON.stringify (this.sets) + ' methods: ' + JSON.stringify (this.methodIds));
      },

      /*
        Summary
          Accepts one argument: name, a string that
          represents the state name and saves this state
          to local storage under the given name.
      */
      save: function (name) {
        window.localStorage.setItem (name,
           JSON.stringify ({
             sets:      this.sets,
             methodIds: this.methodIds
        }));
      }
    };
  },

  /*
    Summary
      Accepts three arguments: name, a string that
      represents the state name; database, a database
      interface; and continuation, a function that
      accepts a filter state; retrieves the state
      that has the given name from local storage and
      passes it to continuation.
    Note
      If none of the entries in local storage have
      the given name, this function will create a new
      filter state object that contains an entry for
      every method in the given database.
  */
  load: function (name, database) {
    // alert ('[FilterState.load]');
    var x = window.localStorage.getItem (name);
    var state = this.create ();

    if (x) {
      // alert ('[FilterState.load] using a cached state.');
      var y = JSON.parse (x);
      state.sets      = y.sets;
      state.methodIds = y.methodIds;
    } else {
      // alert ('[FilterState.load] creating a new state.');
      state.init (database);
    }
    // alert ('[FilterState.load] done');
    return state;
  }
};
