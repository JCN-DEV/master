'use strict';

angular.module('stepApp')
    .factory('DlContTypeSetSearch', function ($resource) {
        return $resource('api/_search/dlContTypeSets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
