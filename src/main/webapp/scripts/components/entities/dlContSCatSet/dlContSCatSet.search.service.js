'use strict';

angular.module('stepApp')
    .factory('DlContSCatSetSearch', function ($resource) {
        return $resource('api/_search/dlContSCatSets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
