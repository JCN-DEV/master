'use strict';

angular.module('stepApp')
    .factory('DlContCatSetSearch', function ($resource) {
        return $resource('api/_search/dlContCatSets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
